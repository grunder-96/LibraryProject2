package ru.alishev.springcourse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.alishev.springcourse.models.Book;

public interface BooksRepository extends JpaRepository<Book, Integer>{
	
	public List<Book> findByNameIgnoreCaseStartingWith(String substr);
}
