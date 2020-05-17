package net.gfeng.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import net.gfeng.model.Person;

@Service
public class PersonService {
	private static final Map<Integer, Person> repo = new ConcurrentHashMap<>();
	static {
		repo.put(0, new Person(0, "ABC-0", 23));
		repo.put(1, new Person(1, "ABC-1", 34));
		repo.put(2, new Person(2, "ABC-2", 64));
		repo.put(3, new Person(6, "ABC-3", 21));
		repo.put(4, new Person(4, "ABC-4", 23));
		repo.put(5, new Person(5, "ABC-5", 27));
		repo.put(6, new Person(6, "ABC-6", 76));
	}
	
	public Person getPerson(int id) {
		return repo.get(id);
	}
	
	public Collection<Person> getAllPerson(){
		return repo.values();
	}
	
	public Person deletePerson(int id) {
		Person p = repo.get(id);
		if (p != null)
			repo.remove(id);
		return p;
	}
	
	public Person addPerson(Person person) {
		if (repo.containsKey(person.id)) {
			return null;
		} else {
			repo.put(person.id, person);
			
			return person;
		}
	}
	
	public List<Person> addPerson(List<Person> personList) {
		List<Person> failAdded = new ArrayList<>();
		for (Person p : personList) {
			if (repo.containsKey(p.id)) {
				failAdded.add(p);
			} else {
				repo.put(p.id, p);
			}
		}
		return failAdded;
	}
	
	public Person updatePerson(Person person) {
		if (repo.containsKey(person.id)) {
			repo.put(person.id, person);			
			return person;
		} else {
			return null;
		}
	}
	
	public Map<Integer, Person> checkReposity(){
		Map<Integer, Person> unConsistant = new HashMap<>();
		
		for (Entry<Integer, Person>e : repo.entrySet()) {
			if (e.getKey() != e.getValue().id) {
				unConsistant.put(e.getKey(), e.getValue());
			}
		}
		
		return unConsistant;
	}
}
