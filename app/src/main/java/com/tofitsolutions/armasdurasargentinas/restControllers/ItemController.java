package com.tofitsolutions.armasdurasargentinas.restControllers;

import com.tofitsolutions.armasdurasargentinas.Item;
import com.tofitsolutions.armasdurasargentinas.util.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ItemController {

    public Item getItem(){
// The connection URL
        String url = "http://"+ Util.getHost() + "/items/159";

// Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

// Add the String message converter
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

// Make the HTTP GET request, marshaling the response to a String


        ResponseEntity<Item> result = restTemplate.getForEntity(url, Item.class, "159");
        return result.getBody();
    }
}
