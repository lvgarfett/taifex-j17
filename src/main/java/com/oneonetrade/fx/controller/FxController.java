package com.oneonetrade.fx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oneonetrade.fx.api.ApiError;
import com.oneonetrade.fx.api.ApiResponseFailed;
import com.oneonetrade.fx.api.ApiResponseSuccess;
import com.oneonetrade.fx.bean.FxRate;
import com.oneonetrade.fx.bean.UsdNtd;
import com.oneonetrade.fx.service.FxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fx")
public class FxController {

    @Autowired
    private FxService fxService;

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
