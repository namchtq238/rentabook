package com.rentabook.controller;

import com.rentabook.constant.Type;
import com.rentabook.domain.Book;
import com.rentabook.service.BookSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory + Paths.get("/target/classes/static/image"));

    @Autowired
    private BookSerivce bookSerivce;
    @GetMapping("/book")
    public String listBook(Model model){
        model.addAttribute("bookList", bookSerivce.getListBook());
        return "bookAdmin";
    }
    @GetMapping("/book/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("type", Type.ADD);
        return "add-book";
    }
    @GetMapping("/book/edit/{id}")
    public String editBook(Model model,@PathVariable Long id){
        model.addAttribute("book", bookSerivce.getBook(id));
        model.addAttribute("type", Type.EDIT);
        return "add-book";
    }
    @GetMapping("/book/view/{id}")
    public String viewBook(Model model,@PathVariable Long id){
        model.addAttribute("book", bookSerivce.getBook(id));
        model.addAttribute("type", Type.VIEW);
        return "add-book";
    }
    @PostMapping("book")
    public String handleAddBook(@ModelAttribute Book book, @RequestParam MultipartFile bookCover){
        if (!bookCover.isEmpty()) {
            try {
                InputStream inputStream = bookCover.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(bookCover.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
                book.setCover(bookCover.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bookSerivce.createBook(book);
        return "redirect:/admin/book";
    }
    @GetMapping("book//delete/{id}")
    public String handleDeleteProductAdmin(@PathVariable("id") long productId) {
        bookSerivce.delete(productId);
        return "redirect:/admin/book";
    }

}
