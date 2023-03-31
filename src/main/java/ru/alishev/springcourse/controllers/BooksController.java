package ru.alishev.springcourse.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.services.BooksService;
import ru.alishev.springcourse.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {

	private BooksService booksService;
	private PeopleService peopleService;
	
	public BooksController(BooksService booksService, PeopleService peopleService) {
		this.booksService = booksService; 
		this.peopleService = peopleService; 
	}
	
	@GetMapping()
	public String showAll(Model model, @RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "books_per_page") Optional<Integer> booksPerPage,
			@RequestParam(name = "sort_by_year") Optional<Boolean> sortByYear) {
		model.addAttribute("books", booksService.findAll(page, booksPerPage, sortByYear));
		return "books/showAll";
	}
		
	@GetMapping("/new")
	public String showFormNewBook(@ModelAttribute("newBook") Book book) {
		return "books/new";
	}
	
	@PostMapping()
	public String createNewBook(@ModelAttribute("newBook") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/new";
		}
		booksService.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}")
	public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("human") Human human) {
		if (booksService.getBookOwner(id) != null) {
			model.addAttribute("owner", booksService.getBookOwner(id));
		} else {
			model.addAttribute("people", peopleService.findAll());
		}
		model.addAttribute("book", booksService.findById(id));
		return "books/showBook";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditBookForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", booksService.findById(id));
		return "books/edit";
	}
	
	@PatchMapping("/{id}")
	public String saveModifiedBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "books/edit";
		}
		booksService.update(id, book);
		return "redirect:/books";
	}
	
	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		booksService.deleteById(id);
		return "redirect:/books";
	}
	
	@PatchMapping("/{id}/set_owner")
	public String setOwner(@PathVariable("id") int bookId, @ModelAttribute("human") Human human) {
		booksService.setOwner(bookId, human);
		return "redirect:/books/" + bookId;
	}
	
	@PatchMapping("/{id}/reset_owner")
	public String resetOwner(@PathVariable("id") int bookId) {
		booksService.resetOwner(bookId);
		return "redirect:/books/" + bookId;
	}
	
	@GetMapping("/search")
	public String showSearchPage(@ModelAttribute Book book) {
		return "books/search";
	}
	
	@PostMapping("/search")
	public String showResultSearch(@RequestParam("substrName") String substr, Model model) {
		model.addAttribute("books", booksService.findByNameIgnoreCaseStartingWith(substr));
		return "books/search";
	}
}
