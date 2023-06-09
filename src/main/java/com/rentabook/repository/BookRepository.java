package com.rentabook.repository;

import com.rentabook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsBookByAuthorAndName(String author, String name);
}
