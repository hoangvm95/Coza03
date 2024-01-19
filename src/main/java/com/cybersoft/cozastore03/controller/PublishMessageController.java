package com.cybersoft.cozastore03.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("")
    public ResponseEntity<?> publishMessage(String message){

        rabbitTemplate.convertAndSend("exjava03","/r03",message);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
