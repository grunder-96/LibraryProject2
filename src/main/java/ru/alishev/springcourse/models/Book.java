package ru.alishev.springcourse.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 100, message = "Допускается длина от 2 до 100 символов")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 100, message = "Допускается длина от 2 до 100 символов")
	@Column(name = "author")
	private String author;

	@Column(name = "year_of_publication")
	private int yearOfPublication;
	
	@ManyToOne
	@JoinColumn(name = "human_id", referencedColumnName = "id")
	private Human human;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "taken_at")
	private Date takenAt;
	
	@Transient
	private boolean isExpired;

	public Book() {

	}

	public Book(int id, String name, String author, int yearOfPublication) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.yearOfPublication = yearOfPublication;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", yearOfPublication=" + yearOfPublication
				+ "]";
	}

	public Date getTakenAt() {
		return takenAt;
	}

	public void setTakenAt(Date takenAt) {
		this.takenAt = takenAt;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
}
