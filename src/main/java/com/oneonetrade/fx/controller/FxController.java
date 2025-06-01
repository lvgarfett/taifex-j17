package com.oneonetrade.fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oneonetrade.fx.dto.api.ApiError;
import com.oneonetrade.fx.dto.Cnd;
import com.oneonetrade.fx.dto.api.ApiResponseFailed;
import com.oneonetrade.fx.dto.api.ApiResponseSuccess;
import com.oneonetrade.fx.dto.FxRate;
import com.oneonetrade.fx.service.FxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fx")
public class FxController {

    @Autowired
    private FxService fxService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @PostMapping("/p/cnd")
    public ResponseEntity<?> searchFxRateByBody(@RequestBody Cnd apiRequest) throws JsonProcessingException {

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        try {

            LocalDate startDate = LocalDate.parse(
                    apiRequest.getStartDate().replace("/",""),
                    FORMATTER
            );

            LocalDate endDate = LocalDate.parse(
                    apiRequest.getEndDate().replace("/",""),
                    FORMATTER
            );

            if (startDate.isBefore(oneYearAgo) || endDate.isAfter(yesterday)) {
                ApiError error = new ApiError("E001", "日期區間不符");
                return ResponseEntity.ok(new ApiResponseFailed(error));
            }

        }

        catch (DateTimeParseException e) {

            ApiError error = new ApiError("E002", "日期格式不符");
            return ResponseEntity.ok(new ApiResponseFailed(error));
        }

        List<?> result = fxService.searchFxRate(apiRequest);

        if (result.size() > 0) {
            ApiError error = new ApiError("0000", "成功");
            return ResponseEntity.ok(new ApiResponseSuccess<>(error, result));
        }

        else {
            ApiError error = new ApiError("E000", "查無資料");
            return ResponseEntity.ok(new ApiResponseFailed(error));
        }
    }

    @GetMapping("/g/cnd")
    public ResponseEntity<?> searchFxRateByParam(@ModelAttribute Cnd apiRequest) throws JsonProcessingException {

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        try {

            LocalDate startDate = LocalDate.parse(
                    apiRequest.getStartDate().replace("/",""),
                    FORMATTER
            );

            LocalDate endDate = LocalDate.parse(
                    apiRequest.getEndDate().replace("/",""),
                    FORMATTER
            );

            if (startDate.isBefore(oneYearAgo) || endDate.isAfter(yesterday)) {
                ApiError error = new ApiError("E001", "日期區間不符");
                return ResponseEntity.ok(new ApiResponseFailed(error));
            }

        }

        catch (DateTimeParseException e) {

            ApiError error = new ApiError("E002", "日期格式不符");
            return ResponseEntity.ok(new ApiResponseFailed(error));
        }

        List<?> result = fxService.searchFxRate(apiRequest);

        if (result.size() > 0) {
            ApiError error = new ApiError("0000", "成功");
            return ResponseEntity.ok(new ApiResponseSuccess<>(error, result));
        }

        else {
            ApiError error = new ApiError("E000", "查無資料");
            return ResponseEntity.ok(new ApiResponseFailed(error));
        }
    }
    @GetMapping
    public ResponseEntity<?> getAll() throws JsonProcessingException {

        List<FxRate> result = fxService.getAll();

        if (result.size() > 0) {
            ApiError error = new ApiError("0000", "成功");
            return ResponseEntity.ok(new ApiResponseSuccess<>(error, result));
        }

        else {
            ApiError error = new ApiError("E000", "查無資料");
            return ResponseEntity.ok(new ApiResponseFailed(error));
        }
    }

}
