package com.rentabook.controller;

import com.rentabook.domain.Book;
import com.rentabook.service.BookSerivce;
import com.rentabook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.rentabook.constant.AppConstaint.ITEMS_PER_PAGE;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final BookSerivce bookSerivce;
    private final UserService userService;

    @GetMapping("/book")
    public String getBooks(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Book> bookPage = bookSerivce.getByPage(page, ITEMS_PER_PAGE);
        int totalPages = bookPage.getTotalPages();
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "book-list";
    }

    @GetMapping("/book/detail/{id}")
    public String getDetaliBook(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookSerivce.getBook(id));
        return "book-detail";
    }

    @GetMapping("/user/order-history")
    public String getViewOrderHistory(Model model) {
        model.addAttribute("listOrder", userService.getListOrderByCurrentUser());
        return "order-history";
    }

    @PostMapping("/order/{id}")
    public String handleOrder(@RequestParam("quantity") String quantitystring,
                              @PathVariable("id") Long bookId,
                              RedirectAttributes redirect) {
        userService.placeOrder(bookId, quantitystring);
        redirect.addFlashAttribute("msg", "Place order success");
        return "redirect:/book/detail/" + bookId;
    }

    @GetMapping("/order/cancel/{orderId}")
    public String handleCancel(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            userService.cancelOrder(orderId);
            redirectAttributes.addFlashAttribute("msg", "Cancel order success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Cannot cancel order");
        }
        return "redirect:/user/order-history";
    }

}
