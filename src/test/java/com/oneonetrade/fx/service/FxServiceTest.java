package com.oneonetrade.fx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oneonetrade.fx.dto.Cnd;
import com.oneonetrade.fx.dto.FxRate;
import com.oneonetrade.fx.entity.CollectionEntity;
import com.oneonetrade.fx.repository.CollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FxServiceTest {

    @Mock // Mock the CollectionRepository dependency
    private CollectionRepository collectionRepository;

    @InjectMocks // Inject mocks into FxService instance
    private FxService fxService;

    // ObjectMapper is instantiated directly in FxService, so we'll let it do its real work
    // and ensure our CollectionEntity.getFxrate() returns valid JSON strings.
    // If ObjectMapper itself were a dependency, you'd mock it.

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
        // Reset any internal state of FxService if necessary (though it appears stateless)
    }

    // --- Helper Methods for Test Data ---
    private CollectionEntity createCollectionEntity(String fxrateJson) {
        CollectionEntity entity = new CollectionEntity();
        entity.setFxrate(fxrateJson);
        entity.setDate("2025-06-01 10:00:00"); // Example date
        return entity;
    }

    private Cnd createCnd(String startDate, String endDate, String currency) {
        Cnd cnd = new Cnd("", "", "");
        cnd.setStartDate(startDate);
        cnd.setEndDate(endDate);
        cnd.setCurrency(currency);
        return cnd;
    }

    // --- Tests for searchFxRate method ---

    @Test
    void testSearchFxRate_jsonProcessingException() {
        // Prepare mock data with malformed JSON
        List<CollectionEntity> mockEntities = Arrays.asList(
                createCollectionEntity("this is not valid json")
        );
        when(collectionRepository.findByDateRange(anyString(), anyString())).thenReturn(mockEntities);

        Cnd apiRequest = createCnd("20250601", "20250602", "usdntd");

        // Assert that JsonProcessingException is thrown
        assertThrows(JsonProcessingException.class, () -> fxService.searchFxRate(apiRequest));
    }

    @Test
    void testSearchFxRate_emptyResultFromRepository() throws JsonProcessingException {
        // Mock repository to return an empty list
        when(collectionRepository.findByDateRange(anyString(), anyString())).thenReturn(new ArrayList<>());

        Cnd apiRequest = createCnd("20250601", "20250602", "usdntd");

        List<?> result = fxService.searchFxRate(apiRequest);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Result list should be empty
    }

    // --- Tests for getAll method ---

    @Test
    void testGetAll_jsonProcessingException() {
        // Prepare mock data with malformed JSON
        List<CollectionEntity> mockEntities = Arrays.asList(
                createCollectionEntity("this is not valid json for getAll")
        );
        when(collectionRepository.findAll()).thenReturn(mockEntities);

        // Assert that JsonProcessingException is thrown
        assertThrows(JsonProcessingException.class, () -> fxService.getAll());
    }

    @Test
    void testGetAll_emptyResultFromRepository() throws JsonProcessingException {
        // Mock repository to return an empty list
        when(collectionRepository.findAll()).thenReturn(new ArrayList<>());

        List<FxRate> result = fxService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Result list should be empty
    }
}