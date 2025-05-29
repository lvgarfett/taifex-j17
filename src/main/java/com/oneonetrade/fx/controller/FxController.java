package com.oneonetrade.fx.controller;

import com.oneonetrade.fx.entity.CollectionEntity;
import com.oneonetrade.fx.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fx")
public class FxController {

    @Autowired
    private CollectionRepository collectionRepository;

    @GetMapping
    public List<CollectionEntity> getAll() {
        return collectionRepository.findAll();
    }

}
