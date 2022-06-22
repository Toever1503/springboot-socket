package com.web;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @MessageMapping("test")
    @SendTo("hello")
    public String index() {
//        MessageReposi
        return "index";
    }
}
