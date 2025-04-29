package com.Backend.Sprint_2.Controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/connect")
    @SendTo("/topic/status")
    public String handleConnection(String message){
        System.out.println("Received message: " + message);
        return "Connected to WebSocket server";
    }

}
