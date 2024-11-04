package br.com.VomHive.VomHive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Nome do arquivo HTML do formulário de login (login.html)
    }
}
