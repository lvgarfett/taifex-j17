package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class FxReaderTest {

    @Mock // Mock the RestTemplate dependency
    private RestTemplate restTemplate;

    @InjectMocks // Inject mocks into FxReader instance
    private FxReader fxReader;

    private String todayFormattedApiDate; // Date format for API filtering (yyyyMMdd)
    private String todayFormattedCollectionDate; // Date format for CollectionEntity (yyyy-MM-dd HH:mm:ss)

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);

        // Determine "today" for consistent testing of the date filtering logic
        // This is crucial because fetchData() uses LocalDate.now()
        LocalDate today = LocalDate.now();
        todayFormattedApiDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        todayFormattedCollectionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Reset internal state of fxReader for each test
        // This is important because FxReader is stateful (collectionEntityList, nextCollection)
        // If FxReader was implemented as ItemStream, its open/close methods would handle this.
        try {
            // Use reflection or a test-specific method to clear internal state
            java.lang.reflect.Field listField = FxReader.class.getDeclaredField("collectionEntityList");
            listField.setAccessible(true);
            ((List) listField.get(fxReader)).clear();

            java.lang.reflect.Field indexField = FxReader.class.getDeclaredField("nextCollection");
            indexField.setAccessible(true);
            indexField.setInt(fxReader, 0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to reset FxReader internal state: " + e.getMessage());
        }
    }

    // Helper method to create a mock API response byte array
    private byte[] createMockApiResponse(List<JSONObject> data) {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Test
    void testRead_fetchesAndReturnsAllItemsExcludingToday() throws Exception {
        // Mock data: 2 items from yesterday, 1 item from today
        JSONObject yesterdayItem1 = new JSONObject();
        yesterdayItem1.put("Date", "20250531"); // Assuming yesterday
        yesterdayItem1.put("Currency", "USD");
        yesterdayItem1.put("Rate", "1.0");

        JSONObject yesterdayItem2 = new JSONObject();
        yesterdayItem2.put("Date", "20250531"); // Assuming yesterday
        yesterdayItem2.put("Currency", "EUR");
        yesterdayItem2.put("Rate", "0.92");

        JSONObject todayItem = new JSONObject();
        todayItem.put("Date", todayFormattedApiDate); // Matches today's date
        todayItem.put("Currency", "JPY");
        todayItem.put("Rate", "156.0");

        List<JSONObject> mockApiData = List.of(yesterdayItem1, yesterdayItem2, todayItem);
        byte[] mockResponseBody = createMockApiResponse(mockApiData);

        // Configure mock RestTemplate to return the mock data
        when(restTemplate.exchange(
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        // First read: should trigger fetchData() and return the first item from yesterday
        CollectionEntity item1 = fxReader.read();
        assertNotNull(item1);
        assertEquals(yesterdayItem1.toString(), item1.getFxrate());
        // Verify the date set in CollectionEntity matches the LocalDateTime.now() format
        assertTrue(item1.getDate().startsWith(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));


        // Second read: should return the second item from yesterday
        CollectionEntity item2 = fxReader.read();
        assertNotNull(item2);
        assertEquals(yesterdayItem2.toString(), item2.getFxrate());

        // Third read: no more items expected after filtering out today's item
        CollectionEntity item3 = fxReader.read();
        assertNull(item3); // Expect null as today's item should be filtered out

        // Verify that fetchData() was called once
        // (You'd need to mock the private method directly or use a spy if you want to verify it specifically.
        // For @Autowired components, verifying the mock of the dependency is usually sufficient).
    }

    @Test
    void testRead_returnsNullWhenNoDataFetched() throws Exception {
        // Configure mock RestTemplate to return an empty JSON array
        byte[] mockResponseBody = createMockApiResponse(Collections.emptyList());

        when(restTemplate.exchange(
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        // First read: should trigger fetchData() and return null as no data
        CollectionEntity item = fxReader.read();
        assertNull(item);

        // Subsequent read: should still return null
        item = fxReader.read();
        assertNull(item);
    }

    @Test
    void testRead_handlesApiError() throws Exception {
        // Configure mock RestTemplate to return a non-2xx status code
        when(restTemplate.exchange(
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // The read method will likely throw an exception if fetchData does
        // Or, if fetchData logs and returns empty, read() will return null
        // Based on your current fetchData, it logs and returns empty arrayList, then read() returns null.
        CollectionEntity item = fxReader.read();
        assertNull(item); // Expect null because fetchData returns empty list on error
    }

    @Test
    void testRead_handlesMalformedJsonResponse() throws Exception {
        // Configure mock RestTemplate to return malformed JSON
        byte[] malformedJson = "this is not valid json".getBytes(StandardCharsets.UTF_8);

        when(restTemplate.exchange(
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(malformedJson, HttpStatus.OK));

        // Expect the JSONException to propagate, or be caught and logged by fetchData,
        // leading to an empty list and thus null from read().
        // Based on your current code, JSONArray(dailyForeignExchangeRates) will throw an exception
        // which your current fetchData() doesn't explicitly catch and rethrow, so it will bubble up
        // as a RuntimeException in your `read()` method.
        // For a more robust test, you'd add a try-catch for the exception in fetchData,
        // or test the exception directly.
        assertThrows(org.json.JSONException.class, () -> fxReader.read());
    }


    @Test
    void testRead_resetsAfterAllItemsRead() throws Exception {
        // Mock data: Only one item
        JSONObject item = new JSONObject();
        item.put("Date", "20250531"); // Yesterday
        item.put("Currency", "USD");
        item.put("Rate", "1.0");

        byte[] mockResponseBody = createMockApiResponse(List.of(item));

        when(restTemplate.exchange(
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        // Read first item
        CollectionEntity firstRead = fxReader.read();
        assertNotNull(firstRead);
        assertEquals(item.toString(), firstRead.getFxrate());

        // Read again, should return null (list exhausted)
        CollectionEntity secondRead = fxReader.read();
        assertNull(secondRead);

        // Read a third time. Due to your `else { nextCollection = 0; collectionEntityList.clear(); }`
        // the `fetchData()` will be called again.
        // Re-mock RestTemplate for the second fetch if you want different data,
        // otherwise, it will return the same data again.
        when(restTemplate.exchange( // Mock again for the second call to fetchData
                eq("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)
        )).thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK)); // Same data for simplicity

        CollectionEntity thirdRead = fxReader.read();
        assertNotNull(thirdRead); // It should re-fetch and return the item again
        assertEquals(item.toString(), thirdRead.getFxrate());
    }
}