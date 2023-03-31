package ru.alishev.springcourse.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.mapping.Backref;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	
	private final PeopleRepository peopleRepository;

	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}
	
	public List<Human> findAll() {
		return peopleRepository.findAll();
	}
	
	public Human findById(int id) {
		return peopleRepository.findById(id).orElse(null);
	}
		
	public List<Book> getBooksByHumanId(int id) {
		Optional<Human> humanOptional = peopleRepository.findById(id);
		if (humanOptional.isPresent()) {
			Hibernate.initialize(humanOptional.get().getBooks());
			humanOptional.get().getBooks().forEach(b -> {
				if (new Date().getTime() - b.getTakenAt().getTime() > 86_400_000) {
					b.setExpired(true);
				}
			});
			return humanOptional.get().getBooks();
		} else {
			return Collections.emptyList();
		}
	}
	
	@Transactional
	public void save(Human human) {
		peopleRepository.save(human);
	}
	
	@Transactional
	public void deleteHuman(int id) {
		peopleRepository.deleteById(id);
	}
	
	@Transactional
	public void update(int id, Human human) {
		human.setId(id);
		peopleRepository.save(human);
	}
	
	public Optional<Human> findByFullName(String name) {
		return peopleRepository.findByFullName(name);
	}
}
