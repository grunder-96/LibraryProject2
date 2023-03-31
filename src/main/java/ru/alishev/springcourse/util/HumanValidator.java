package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.alishev.springcourse.models.Human;
import ru.alishev.springcourse.services.PeopleService;

@Component
public class HumanValidator implements Validator{
	
	private final PeopleService peopleService;
	
	@Autowired
	public HumanValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Human.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Human human = (Human) target;
		if (peopleService.findByFullName(human.getFullName()).isPresent()) {
			errors.rejectValue("fullName", "", "Такой человек уже есть");
		}
	}
}

//@Override
//public void validate(Object target, Errors errors) {
//	Person person = (Person) target;
//	if (personDAO.show(person.getEmail()).isPresent()) {
//		errors.rejectValue("email", "", "Этот email уже успользуется");
//	}