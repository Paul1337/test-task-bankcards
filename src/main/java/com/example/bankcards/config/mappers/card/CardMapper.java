package com.example.bankcards.config.mappers.card;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardMapper {
    @Autowired
    public void configureModelMapper(ModelMapper modelMapper) {
//        modelMapper.typeMap(Card.class, GetCard.FullResponse.class).addMappings(mapper -> {
//
//        });
    }
}
