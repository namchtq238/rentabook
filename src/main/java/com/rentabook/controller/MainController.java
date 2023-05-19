package com.rentabook.controller;

import com.rentabook.domain.User;
import com.rentabook.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public String login(){
        return "login";
    }
    @GetMapping("/createAccount")
    public String getViewCreateAccount(Model model) {
        model.addAttribute("user", new User());
        return "createAccount";
    }

    @PostMapping("/processRegister")
    public String handleCreateAccount(@ModelAttribute  User user,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws Exception {
        if (userService.checkExist(user.getUsername()))
            bindingResult.rejectValue("email", "invalid", "Tài khoản khách hàng đã có trong hệ thống");
        if (bindingResult.hasErrors()) return "/createAccount";
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("msg", "Tạo tài khoản thành công");
        return "redirect:/login";
    }

}
