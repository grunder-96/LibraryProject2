package ru.alishev.springcourse.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.repositories.BooksRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	
	
	private final BooksRepository booksRepository;
	
	public BooksService(BooksRepository booksRepository) {
		this.booksRepository = booksRepository;
	}
	
	public List<Book> findAll(Optional<Integer> page, Optional<Integer> booksPerPage, Optional<Boolean> sortByYear) {
		if (page.isPresent() && booksPerPage.isPresent()) {
			if (sortByYear.orElse(false)) {
				return booksRepository.findAll(PageRequest.of(page.get(), booksPerPage.get(), Sort.by("yearOfPublication"))).getContent();
			}
			return booksRepository.findAll(PageRequest.of(page.get(), booksPerPage.get())).getContent();
		}
		
		if (sortByYear.orElse(false)) {
			return booksRepository.findAll(Sort.by("yearOfPublication"));
		}
		
		return booksRepository.findAll();
	}
	
	public Book findById(int id) {
		return booksRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void save(Book book) {
		booksRepository.save(book);
	}
	
	public Human getBookOwner(int id) {
		return booksRepository.findById(id).map(book -> book.getHuman()).orElse(null);
	}
	
	@Transactional
	public void update(int id, Book book) {
		Book originBook = booksRepository.findById(id).get();
		book.setId(originBook.getId());
		book.setHuman(originBook.getHuman()); 
		booksRepository.save(book);
	}
	
	@Transactional
	public void deleteById(int id) {
		booksRepository.deleteById(id);
	}
	
	@Transactional
	public void resetOwner(int bookId) {
		Book book = findById(bookId);
		book.setHuman(null);
		book.setTakenAt(null);
	}
	
	@Transactional
	public void setOwner(int bookId, Human human) {
		Book book = findById(bookId);
		book.setHuman(human);
		book.setTakenAt(new Date());
	}
	
	public List<Book> findByNameIgnoreCaseStartingWith(String substr) {
		return booksRepository.findByNameIgnoreCaseStartingWith(substr);
	}
}
