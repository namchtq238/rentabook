package com.rentabook.controller;

import com.rentabook.domain.Book;
import com.rentabook.service.BookSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private static final int ITEMS_PER_PAGE = 5;
    @Autowired
    private BookSerivce bookSerivce;

    @GetMapping("/books")
    public String getBooks(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Book> bookPage = bookSerivce.getByPage(page, ITEMS_PER_PAGE);
        int totalPages = bookPage.getTotalPages();
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "bookList";
    }
    @GetMapping("/books/detail")
    public String getDetaliBook(Model model) {
        return "productDetail";
    }
}
