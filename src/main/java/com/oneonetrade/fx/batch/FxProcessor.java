package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class FxProcessor implements ItemProcessor<CollectionEntity, CollectionEntity> {
    @Override
    public CollectionEntity process(CollectionEntity item) throws Exception {
        log.info("Process forex data: {}", item);
        return item;
    }
}
