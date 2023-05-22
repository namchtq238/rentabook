package com.rentabook.controller;

import com.rentabook.service.BookSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private BookSerivce bookSerivce;

    @GetMapping("/user/book")
    public String getListBook(Model model){
        return "index";
    }
}
