package com.rentabook.service;

import com.rentabook.domain.Book;
import com.rentabook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSerivce {
    private final BookRepository bookRepository;

    public List<Book> getListBook() {
        return bookRepository.findAll();
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).get();
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<Book> getByPage(int page, int items) {
        return bookRepository.findAll(PageRequest.of(page - 1, items));
    }

    public boolean checkExistedBook(Book book) {
        return (bookRepository.existsBookByAuthorAndName(book.getAuthor(), book.getName()));
    }
}
