package ru.alishev.springcourse.controllers;

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
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.services.PeopleService;
import ru.alishev.springcourse.util.HumanValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
	private final PeopleService peopleService;
	private final HumanValidator humanValidator;
		
	public PeopleController(PeopleService peopleService, HumanValidator humanValidator) {
		this.peopleService = peopleService;
		this.humanValidator = humanValidator;
	}
	
	@GetMapping()
	public String showAll(Model model) {
		model.addAttribute("people", peopleService.findAll());
		return "people/showAll";
	}
	
	@GetMapping("/new")
	public String showFormNewHuman(@ModelAttribute("newHuman") Human human) {
		return "people/new";
	}
	
	@PostMapping()
	public String createNewHuman(@ModelAttribute("newHuman") @Valid Human human, BindingResult bindingResult) {
		humanValidator.validate(human, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/people/new";
		}
		peopleService.save(human);
		return "redirect:/people";
	}
	
	@GetMapping("/{id}")
	public String showHuman(@PathVariable("id") int id, Model model) {
		model.addAttribute("human", peopleService.findById(id));
		model.addAttribute("books", peopleService.getBooksByHumanId(id));
		return "people/showHuman";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditHumanForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("human", peopleService.findById(id));
		return "people/edit";
	}
	
	@PatchMapping("/{id}")
	public String saveModifiedHuman(@PathVariable("id") int id, @ModelAttribute("human") @Valid Human human, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/edit";
		}
		peopleService.update(id, human);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}")
	public String deleteHuman(@PathVariable("id") int id) {
		peopleService.deleteHuman(id);
		return "redirect:/people";
	}
}
