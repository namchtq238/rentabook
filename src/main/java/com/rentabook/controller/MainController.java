package com.rentabook.controller;

import com.rentabook.domain.User;
import com.rentabook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        if (userService.isUserLogin()) return "redirect:/";
        return "login";
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/createAccount")
    public String getViewCreateAccount(Model model) {
        model.addAttribute("user", new User());
        return "create-account";
    }

    @PostMapping("/processRegister")
    public String handleCreateAccount(@ModelAttribute User user,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws Exception {
        if (userService.checkExist(user.getUsername()))
            bindingResult.rejectValue("username", "invalid", "Tài khoản khách hàng đã có trong hệ thống");
        if (bindingResult.hasErrors()) return "create-account";
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("msg", "Tạo tài khoản thành công");
        return "redirect:/login";
    }

}
