package com.Radiant_wizard.GastroManagementApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HealthRestController {

    @GetMapping("/pong")
    public String getPong(){
        return "pong";
    }

}
