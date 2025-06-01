package com.oneonetrade.fx.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneonetrade.fx.dto.Cnd;
import com.oneonetrade.fx.dto.FxRate;
import com.oneonetrade.fx.dto.currency.*;
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

    public List<?> searchFxRate(Cnd apiRequest) throws JsonProcessingException {

        List<CollectionEntity> result = collectionRepository.findByDateRange(
                apiRequest.getStartDate().replace("/",""),
                apiRequest.getEndDate().replace("/","")
        );

        ObjectMapper mapper = new ObjectMapper();

        FxRate fxRate;

        switch (apiRequest.getCurrency().replace("/", "").toLowerCase()) {

            case "usdntd":

                List<UsdNtd> usdNtdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    usdNtdList.add(new UsdNtd(fxRate.getDate(), fxRate.getUsdNtd()));
                }
                return usdNtdList;

            case "rmbntd":

                List<RmbNtd> rmbNtdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    rmbNtdList.add(new RmbNtd(fxRate.getDate(), fxRate.getRmbNtd()));
                }
                return rmbNtdList;

            case "eurusd":

                List<EurUsd> eurUsdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    eurUsdList.add(new EurUsd(fxRate.getDate(), fxRate.getEurUsd()));
                }
                return eurUsdList;

            case "usdjpy":

                List<UsdJpy> usdJpyList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    usdJpyList.add(new UsdJpy(fxRate.getDate(), fxRate.getUsdJpy()));
                }
                return usdJpyList;

            case "gbpusd":

                List<GbpUsd> gbpUsdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    gbpUsdList.add(new GbpUsd(fxRate.getDate(), fxRate.getGbpUsd()));
                }
                return gbpUsdList;

            case "audusd":

                List<AudUsd> audUsdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    audUsdList.add(new AudUsd(fxRate.getDate(), fxRate.getAudUsd()));
                }
                return audUsdList;

            case "usdhkd":

                List<UsdHkd> usdHkdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    usdHkdList.add(new UsdHkd(fxRate.getDate(), fxRate.getUsdHkd()));
                }
                return usdHkdList;

            case "usdrmb":

                List<UsdRmb> usdRmbList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    usdRmbList.add(new UsdRmb(fxRate.getDate(), fxRate.getUsdRmb()));
                }
                return usdRmbList;

            case "usdzar":

                List<UsdZar> usdZarList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    usdZarList.add(new UsdZar(fxRate.getDate(), fxRate.getUsdZar()));
                }
                return usdZarList;

            case "nzdusd":

                List<NzdUsd> nzdUsdList = new ArrayList<>();
                for (CollectionEntity collectionEntity : result) {
                    fxRate = mapper.readValue(collectionEntity.getFxrate(), FxRate.class);
                    nzdUsdList.add(new NzdUsd(fxRate.getDate(), fxRate.getNzdUsd()));
                }
                return nzdUsdList;

            default:

                List<FxRate> fxRateList = new ArrayList<>();
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
