package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import com.oneonetrade.fx.repository.CollectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FxWriterTest {

    @Mock // Mock the repository dependency
    private CollectionRepository collectionRepository;

    @InjectMocks // Inject mocks into FxWriter instance
    private FxWriter fxWriter;

    private ListAppender<ILoggingEvent> listAppender; // To capture logs

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);

        // Setup Logback ListAppender to capture logs
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(FxWriter.class); // Get the logger for FxWriter
        listAppender = new ListAppender<>();
        listAppender.start();
        ((ch.qos.logback.classic.Logger) logger).addAppender(listAppender);
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO); // Set logging level for the test
    }

    @Test
    void testWrite_successWithItems() throws Exception {
        // Prepare mock data
        CollectionEntity item1 = new CollectionEntity();
        item1.setId(1L);
        item1.setFxrate("{\"date\":\"20250601\",\"usdHkd\":7.78}");
        item1.setDate("2025-06-01 10:00:00");

        CollectionEntity item2 = new CollectionEntity();
        item2.setId(2L);
        item2.setFxrate("{\"date\":\"20250601\",\"eurUsd\":1.08}");
        item2.setDate("2025-06-01 10:00:00");

        List<CollectionEntity> items = List.of(item1, item2);
        Chunk<CollectionEntity> chunk = new Chunk<>(items); // Create a Chunk from the list

        // When collectionRepository.saveAll is called, do nothing (default mock behavior)
        // or you can configure it to return the saved items if your logic depends on it.
        // when(collectionRepository.saveAll(anyList())).thenReturn(items); // Example if you need a return

        // Call the write method
        fxWriter.write(chunk);

        // 1. Verify that saveAll was called exactly once on the mock repository
        verify(collectionRepository, times(1)).saveAll(anyList());

        // 2. Capture the argument passed to saveAll to assert its content
        ArgumentCaptor<List<CollectionEntity>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(collectionRepository).saveAll(argumentCaptor.capture());
        List<CollectionEntity> capturedItems = argumentCaptor.getValue();

        assertNotNull(capturedItems);
        assertEquals(2, capturedItems.size());
        assertTrue(capturedItems.contains(item1));
        assertTrue(capturedItems.contains(item2));

        // 3. Verify logging
        List<ILoggingEvent> logsList = listAppender.list;
        assertFalse(logsList.isEmpty(), "Log should not be empty");
        assertEquals(1, logsList.size(), "There should be exactly one log entry");
        assertEquals(Level.INFO, logsList.get(0).getLevel(), "Log level should be INFO");
    }

    @Test
    void testWrite_repositoryThrowsException() {
        // Prepare mock data
        CollectionEntity item1 = new CollectionEntity();
        item1.setId(1L);
        List<CollectionEntity> items = List.of(item1);
        Chunk<CollectionEntity> chunk = new Chunk<>(items);

        // Configure the mock repository to throw an exception when saveAll is called
        doThrow(new RuntimeException("Database connection error")).when(collectionRepository).saveAll(anyList());

        // Verify that the exception is rethrown by the writer
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> fxWriter.write(chunk));
        assertTrue(thrown.getMessage().contains("Database connection error"), "Exception message should match");

        // Verify that saveAll was still attempted
        verify(collectionRepository, times(1)).saveAll(anyList());

        // Verify logging
        List<ILoggingEvent> logsList = listAppender.list;
        assertFalse(logsList.isEmpty(), "Log should not be empty");
        assertEquals(1, logsList.size(), "There should be exactly one log entry before exception");
        assertEquals(Level.INFO, logsList.get(0).getLevel(), "Log level should be INFO");
    }
}