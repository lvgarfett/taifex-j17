package com.oneonetrade.fx.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneonetrade.fx.bean.FxRate;
import com.oneonetrade.fx.entity.CollectionEntity;
import com.oneonetrade.fx.repository.CollectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FxService {

    @Autowired
    private CollectionRepository collectionRepository;

    public List<FxRate> getAll() throws JsonProcessingException {

        List<CollectionEntity> result = collectionRepository.findAll();

        List<FxRate> fxRateList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        FxRate fxRate;

        for (CollectionEntity collectionEntity : result) {
            fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
            fxRateList.add(new FxRate(
                    fxRate.getDate(),
                    fxRate.getUsdNtd(),
                    fxRate.getRmbNtd(),
                    fxRate.getEurUsd(),
                    fxRate.getUsdJpy(),
                    fxRate.getGbpUsd(),
                    fxRate.getAudUsd(),
                    fxRate.getUsdHkd(),
                    fxRate.getUsdRmb(),
                    fxRate.getUsdZar(),
                    fxRate.getNzdUsd()
            ));

        }

        return fxRateList;
    }

}
