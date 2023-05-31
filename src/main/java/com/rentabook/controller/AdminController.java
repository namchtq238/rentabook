package com.rentabook.controller;

import com.rentabook.constant.Type;
import com.rentabook.domain.Book;
import com.rentabook.service.BookSerivce;
import com.rentabook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@Slf4j
public class AdminController {
    private static final String currentDirectory = System.getProperty("user.dir");
    private static final Path path = Paths.get(currentDirectory + Paths.get("/target/classes/static/image"));

    private final BookSerivce bookSerivce;
    private final UserService userService;

    @GetMapping("/book")
    public String listBook(Model model) {
        model.addAttribute("bookList", bookSerivce.getListBook());
        return "book-admin";
    }

    @GetMapping("/book/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("type", Type.ADD);
        return "add-book";
    }

    @GetMapping("/book/edit/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookSerivce.getBook(id));
        model.addAttribute("type", Type.EDIT);
        return "add-book";
    }

    @GetMapping("/book/view/{id}")
    public String viewBook(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookSerivce.getBook(id));
        model.addAttribute("type", Type.VIEW);
        return "add-book";
    }

    @PostMapping("book")
    public String handleAddBook(@ModelAttribute Book book,
                                BindingResult bindingResult,
                                @RequestParam MultipartFile bookCover,
                                RedirectAttributes redirectAttributes) {
        if (bookCover.isEmpty() && book.getId() == null) {
            bindingResult.rejectValue("cover", "not found", "cover is empty");
        }
        if (bookSerivce.checkExistedBook(book)) {
            bindingResult.rejectValue("name","Đã tồn tại sách");
        }
        if (bindingResult.hasErrors()){
            return "redirect:/book/view/" + book.getId();
        }
        try {
            if (!bookCover.isEmpty() && !bookCover.getOriginalFilename().isEmpty()) {
                InputStream inputStream = bookCover.getInputStream();
                Files.copy(inputStream, path.resolve(Objects.requireNonNull(bookCover.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
                book.setCover(bookCover.getOriginalFilename());
            }
            bookSerivce.createBook(book);
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("msg", "Thêm sách thất bại");
        }
        return"redirect:/admin/book";
}

    @GetMapping("/book/delete/{id}")
    public String handleDeleteProductAdmin(@PathVariable("id") long productId,
                                           RedirectAttributes redirectAttributes) {
        try {
            bookSerivce.delete(productId);
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("msg", "delete book failed");
        }
        return "redirect:/admin/book";
    }

}
