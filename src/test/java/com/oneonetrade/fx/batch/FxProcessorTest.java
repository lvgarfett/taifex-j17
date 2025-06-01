package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FxProcessorTest {

    private FxProcessor fxProcessor;
    private ListAppender<ILoggingEvent> listAppender; // To capture logs

    @BeforeEach
    void setUp() {
        fxProcessor = new FxProcessor();

        // Setup Logback ListAppender to capture logs
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(FxProcessor.class); // Get the logger for FxProcessor
        listAppender = new ListAppender<>();
        listAppender.start();
        ((ch.qos.logback.classic.Logger) logger).addAppender(listAppender);
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO); // Set logging level for the test
    }

    @Test
    void testProcess_returnsSameItem() throws Exception {
        CollectionEntity inputItem = new CollectionEntity();
        inputItem.setId(1L);
        inputItem.setFxrate("{\"date\":\"20250601\",\"usdHkd\":7.78}");
        inputItem.setDate("2025-06-01 10:00:00");

        CollectionEntity processedItem = fxProcessor.process(inputItem);

        // 1. Assert that the same item instance is returned
        assertSame(inputItem, processedItem, "Processor should return the same instance");

        // 2. Assert that the content of the item remains unchanged (since it's a pass-through processor)
        assertEquals(1L, processedItem.getId());
        assertEquals("{\"date\":\"20250601\",\"usdHkd\":7.78}", processedItem.getFxrate());
        assertEquals("2025-06-01 10:00:00", processedItem.getDate());
        // Add more assertions for other fields if CollectionEntity has them

        // 3. Verify logging
        List<ILoggingEvent> logsList = listAppender.list;
        assertFalse(logsList.isEmpty(), "Log should not be empty");
        assertEquals(1, logsList.size(), "There should be exactly one log entry");
        assertEquals(Level.INFO, logsList.get(0).getLevel(), "Log level should be INFO");
        assertTrue(logsList.get(0).getMessage().contains("Process forex data:"), "Log message should contain expected text");
    }

    @Test
    void testProcess_withNullItem() throws Exception {
        // Test how the processor handles a null input item
        // Current implementation will just pass null through
        CollectionEntity processedItem = fxProcessor.process(null);
        assertNull(processedItem, "Processor should return null for null input");

        // Verify logging for null (if your FxProcessor handles null differently, adjust)
        List<ILoggingEvent> logsList = listAppender.list;
        assertFalse(logsList.isEmpty(), "Log should not be empty");
        assertEquals(1, logsList.size(), "There should be exactly one log entry");
    }

    // If FxProcessor were to perform transformations or filtering, you'd add more tests:
    // @Test
    // void testProcess_transformationLogic() { ... }
    // @Test
    // void testProcess_filteringLogic_returnsNull() { ... }
    // @Test
    // void testProcess_invalidData_throwsException() { ... }
}