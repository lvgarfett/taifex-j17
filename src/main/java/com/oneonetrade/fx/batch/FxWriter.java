package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import com.oneonetrade.fx.repository.CollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FxWriter implements ItemWriter<CollectionEntity> {

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public void write(Chunk<? extends CollectionEntity> chunk) throws Exception {
        log.info("Writing {}", chunk.getItems().size());
        collectionRepository.saveAll(chunk.getItems());
    }
}
