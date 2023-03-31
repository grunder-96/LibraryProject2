package ru.alishev.springcourse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.alishev.springcourse.models.Human;

public interface PeopleRepository extends JpaRepository<Human, Integer>{
	
	public Optional<Human> findByFullName(String name);
}
