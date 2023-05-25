package com.rentabook.service;

import com.rentabook.constant.Role;
import com.rentabook.domain.Book;
import com.rentabook.domain.Order;
import com.rentabook.domain.User;
import com.rentabook.repository.BookRepository;
import com.rentabook.repository.OrderRepository;
import com.rentabook.repository.UserRepository;
import com.rentabook.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;


    public void registerUser(User user) throws Exception {
        if (checkExist(user.getUsername())) throw new Exception("Đã Tồn Tại User");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepo.save(user);
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

//    public void updateUser(Long id, User userRequest) {
//        //set trong db
//        User user = userRepo.getById(id);
//
//        updateUserSecurity(user);
//        userRepo.save(user);
//    }

    private static void updateUserSecurity(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getType()));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public boolean isUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails;
    }

    public boolean checkExist(String username) {
        return userRepo.existsUserByUsername(username);
    }

    public void placeOrder(Long bookId, String quantityInString) {
        Book book = bookRepository.getReferenceById(bookId);
        Long quantity = Long.valueOf(quantityInString);
        book.setSold(book.getSold() + quantity);
        Order order = new Order();
        order.setUser(this.getCurrentUser());
        order.setBook(book);
        order.setQuantity(quantity);
        orderRepository.save(order);

        bookRepository.save(book);
    }

    public List<Order> getListOrderByCurrentUser() {
        return orderRepository.findOrderByUserAndDeleted(this.getCurrentUser(), false);
    }

    public void cancelOrder(Long id) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setDeleted(true);
            Book b = order.getBook();
            b.setSold(b.getSold() - order.getQuantity());
            bookRepository.save(b);
        } else {
            throw new Exception("ngu");
        }
    }
}
