package com.example.bankcards.config;

import com.example.bankcards.dto.cards.GetCard;
import com.example.bankcards.entity.Card;
import org.apache.commons.collections4.Get;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.typeMap(Card.class, GetCard.ShallowResponse.class).addMappings(mapper -> {
            mapper.map(Card::getMaskedNumber, GetCard.ShallowResponse::setNumber);
        });

        return modelMapper;
    }
}
