package net.gfeng.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Person {
	@Getter
	@Setter
	public int id;
	
	@Getter
	@Setter
	public String name;
	
	@Getter
	@Setter
	public int age;
	
	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}
