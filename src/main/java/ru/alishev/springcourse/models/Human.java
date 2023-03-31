package ru.alishev.springcourse.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "human")
public class Human {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 200, message = "Допускается длина от 5 до 200 символов")
	@Column(name = "full_name")
	private String fullName;
	
	@Min(value = 1900, message = "Значение не может быть меньше 1900")
	@Max(value = 2023, message = "Значение не может быть больше 2023")
	@Column(name = "year_of_birth")
	private int yearOfBirth;
	
	@OneToMany(mappedBy = "human")
	private List<Book> books;

	public Human() {

	}
	
	public Human(int id, String fullName, int yearOfBirth) {
		this.id = id;
		this.fullName = fullName;
		this.yearOfBirth = yearOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Human [id=" + id + ", fullName=" + fullName + ", yearOfBirth=" + yearOfBirth + "]";
	}
}
