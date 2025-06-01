package com.oneonetrade.fx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneonetrade.fx.dto.Cnd;
import com.oneonetrade.fx.dto.FxRate;
import com.oneonetrade.fx.service.FxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Current date is June 2, 2025.
// LocalDate.now().minusYears(1) = 2024-06-02
// LocalDate.now().minusDays(1) = 2025-06-01

@WebMvcTest(FxController.class) // Tests only the FxController and its MVC components
class FxControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to perform HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // Spring Boot provides this for JSON serialization/deserialization

    @MockitoBean // Creates a Mockito mock bean for FxService and adds it to the Spring context
    private FxService fxService;

    // Helper to format dates for Cnd DTO (e.g., "20250601")
    private static final DateTimeFormatter REQUEST_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


    // --- Tests for POST /fx/p/cnd ---

    @Test
    void searchFxRateByBody_success() throws Exception {
        // Mock service response
        FxRate mockFxRate = new FxRate(); // Assuming FxRate has a constructor/setters
        mockFxRate.setDate("20250530");
        List<FxRate> mockServiceResult = Collections.singletonList(mockFxRate);

        // Prepare request body
        Cnd requestCnd = new Cnd("", "","");
        requestCnd.setStartDate(LocalDate.now().minusDays(2).format(REQUEST_DATE_FORMATTER)); // 20250531
        requestCnd.setEndDate(LocalDate.now().minusDays(1).format(REQUEST_DATE_FORMATTER)); // 20250601
        requestCnd.setCurrency("usdntd"); // Example currency

        // Perform POST request
        mockMvc.perform(post("/fx/p/cnd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCnd))) // Convert Cnd to JSON string
                .andExpect(status().isOk()); // Expect HTTP 200 OK
    }

    @Test
    void searchFxRateByBody_invalidDateFormat() throws Exception {
        // Prepare request body with invalid date format
        Cnd requestCnd = new Cnd("", "", "");
        requestCnd.setStartDate("2025-05-31"); // Invalid format
        requestCnd.setEndDate("20250601");
        requestCnd.setCurrency("usdntd");

        // Perform POST request
        mockMvc.perform(post("/fx/p/cnd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCnd)))
                .andExpect(status().isOk()) // Controller returns ResponseEntity.ok(ApiResponseFailed)
                .andExpect(jsonPath("$.error.code").value("E002")) // Check for date format error code
                .andExpect(jsonPath("$.error.message").value("日期格式不符")); // Check for date format error message
    }

    @Test
    void searchFxRateByBody_dateRangeExceedsOneYearAgo() throws Exception {
        // Prepare request body with startDate before one year ago
        Cnd requestCnd = new Cnd("", "", "");
        requestCnd.setStartDate(LocalDate.now().minusYears(1).minusDays(1).format(REQUEST_DATE_FORMATTER)); // 20240601, which is 1 year and 1 day ago
        requestCnd.setEndDate(LocalDate.now().minusDays(1).format(REQUEST_DATE_FORMATTER)); // 20250601
        requestCnd.setCurrency("usdntd");

        // Perform POST request
        mockMvc.perform(post("/fx/p/cnd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCnd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("E001")) // Check for date range error code
                .andExpect(jsonPath("$.error.message").value("日期區間不符"));
    }

    @Test
    void searchFxRateByBody_dateRangeExceedsYesterday() throws Exception {
        // Prepare request body with endDate after yesterday (i.e., today or future)
        Cnd requestCnd = new Cnd("", "", "");
        requestCnd.setStartDate(LocalDate.now().minusDays(2).format(REQUEST_DATE_FORMATTER)); // 20250531
        requestCnd.setEndDate(LocalDate.now().format(REQUEST_DATE_FORMATTER)); // Today: 20250602
        requestCnd.setCurrency("usdntd");

        // Perform POST request
        mockMvc.perform(post("/fx/p/cnd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCnd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("E001")) // Check for date range error code
                .andExpect(jsonPath("$.error.message").value("日期區間不符"));
    }

    @Test
    void searchFxRateByBody_noDataFound() throws Exception {
        // Mock service to return an empty list
        when(fxService.searchFxRate(any(Cnd.class))).thenReturn(Collections.emptyList());

        // Prepare request body
        Cnd requestCnd = new Cnd("", "", "");
        requestCnd.setStartDate(LocalDate.now().minusDays(2).format(REQUEST_DATE_FORMATTER)); // 20250531
        requestCnd.setEndDate(LocalDate.now().minusDays(1).format(REQUEST_DATE_FORMATTER)); // 20250601
        requestCnd.setCurrency("usdntd");

        // Perform POST request
        mockMvc.perform(post("/fx/p/cnd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCnd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("E000")) // Check for no data found error code
                .andExpect(jsonPath("$.error.message").value("查無資料")); // Check for no data found message
    }

    // --- Tests for GET /fx/g/cnd (similar to POST, just change request builder) ---

    @Test
    void searchFxRateByParam_success() throws Exception {
        // Mock service response
        FxRate mockFxRate = new FxRate();
        mockFxRate.setDate("20250530");
        List<FxRate> mockServiceResult = Collections.singletonList(mockFxRate);

        // Prepare request parameters for GET
        String startDateParam = LocalDate.now().minusDays(2).format(REQUEST_DATE_FORMATTER); // 20250531
        String endDateParam = LocalDate.now().minusDays(1).format(REQUEST_DATE_FORMATTER); // 20250601
        String currencyParam = "usdntd";

        // Perform GET request
        mockMvc.perform(get("/fx/g/cnd")
                        .param("startDate", startDateParam)
                        .param("endDate", endDateParam)
                        .param("currency", currencyParam)
                        .accept(MediaType.APPLICATION_JSON)) // Important for response content type
                .andExpect(status().isOk());
    }

    @Test
    void searchFxRateByParam_invalidDateFormat() throws Exception {
        String startDateParam = "2025-05-31"; // Invalid format
        String endDateParam = "20250601";
        String currencyParam = "usdntd";

        mockMvc.perform(get("/fx/g/cnd")
                        .param("startDate", startDateParam)
                        .param("endDate", endDateParam)
                        .param("currency", currencyParam)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("E002"))
                .andExpect(jsonPath("$.error.message").value("日期格式不符"));
    }

    // Add similar tests for date range exceedance and no data found for GET /fx/g/cnd
    // as they would be structured very similarly to the POST tests,
    // just using .param() instead of .content() for the request.

    // --- Tests for GET /fx ---

    @Test
    void getAll_success() throws Exception {
        // Mock service response
        FxRate mockFxRate1 = new FxRate();
        mockFxRate1.setDate("20250530");
        FxRate mockFxRate2 = new FxRate();
        mockFxRate2.setDate("20250529");
        List<FxRate> mockServiceResult = List.of(mockFxRate1, mockFxRate2);
        when(fxService.getAll()).thenReturn(mockServiceResult);

        // Perform GET request
        mockMvc.perform(get("/fx")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("0000"))
                .andExpect(jsonPath("$.error.message").value("成功"));
    }

    @Test
    void getAll_noDataFound() throws Exception {
        // Mock service to return an empty list
        when(fxService.getAll()).thenReturn(Collections.emptyList());

        // Perform GET request
        mockMvc.perform(get("/fx")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value("E000"))
                .andExpect(jsonPath("$.error.message").value("查無資料"));
    }

}