package com.rentabook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/book")
    public String listBook(){
        return "bookAdmin";
    }
    @GetMapping("/book/add")
    public String addBook(){
        return "add-book";
    }
    @GetMapping("/book/edit")
    public String editBook(){
        return "add-book";
    }
    @GetMapping("/book/view")
    public String viewBook(){
        return "add-book";
    }
}
