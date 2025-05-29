package com.oneonetrade.fx.batch;

import com.oneonetrade.fx.entity.CollectionEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class FxReader implements ItemReader<CollectionEntity> {
    @Autowired
    RestTemplate restTemplate;

    private int nextCollection;
    private List<CollectionEntity> collectionEntityList = new ArrayList<>();

    public CollectionEntity read() {

        LocalDateTime today = LocalDateTime.now();

        if (collectionEntityList.isEmpty()) {
            for (JSONObject obj : fetchData()) {
                CollectionEntity collectionEntity = new CollectionEntity();
                collectionEntity.setFxrate(obj.toString());
                collectionEntity.setDate(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                collectionEntityList.add(collectionEntity);
            }
        }

        CollectionEntity collectionEntity = null;

        if (nextCollection < collectionEntityList.size()) {
            collectionEntity = collectionEntityList.get(nextCollection);
            nextCollection++;
        }

        else {
            nextCollection = 0;
            collectionEntityList.clear();
        }

        return collectionEntity;
    }

    private ArrayList<JSONObject> fetchData() {

        LocalDate today = LocalDate.now();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates",
                HttpMethod.GET,
                entity,
                byte[].class);

        ArrayList<JSONObject> arrayList = new ArrayList<>();

        if (response.getStatusCode().is2xxSuccessful()) {

            byte[] data = response.getBody();
            log.info("Data received with size: " + data.length);

            String dailyForeignExchangeRates = new String(data, StandardCharsets.UTF_8);
            log.info(dailyForeignExchangeRates);

            JSONArray jsonArray = new JSONArray(dailyForeignExchangeRates);

            // Convert JSONArray to ArrayList
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(jsonArray.getJSONObject(i));
            }

            Iterator<JSONObject> iterator = arrayList.iterator();

            while (iterator.hasNext()) {
                if (iterator.next().getString("Date")
                        .equals(today.format(DateTimeFormatter.ofPattern("yyyyMMdd")))) {
                    iterator.remove();
                }
            }

        }

        else {
            log.info("Error fetching data: " + response.getStatusCode());
        }

        return arrayList;
    }

}
