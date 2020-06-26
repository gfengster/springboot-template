package net.gfeng.control;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.gfeng.model.Person;
import net.gfeng.service.PersonService;

@RestController
@RequestMapping(path="/demo")
public class RestControl {
	
	@Autowired
	PersonService personService;
	
	@GetMapping("/echo")
    @ResponseBody
	public String echo() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		return "Hello " + System.getProperty("user.name") 
					+ " <br> " + LocalDateTime.now()
					+ " <br> " + ip.getHostName()
					+ " <br> " + ip.getHostAddress();
	}
	
	@RequestMapping(path="person/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getPerson(@PathVariable("id") int id) {
		Person p = personService.getPerson(id);
		if (p == null) 
			return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@RequestMapping(path="person", method=RequestMethod.GET)
	public ResponseEntity<Collection<Person>> getPerson() {
		return new ResponseEntity<>(personService.getAllPerson(), HttpStatus.OK);
	}
	
	@RequestMapping(path="person/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deletePerson(@PathVariable int id) {
		Person p = personService.deletePerson(id);
		if (p == null)
			return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
		 else
			return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@RequestMapping(path="person", method=RequestMethod.PUT)
	public ResponseEntity<Object> addPerson(@RequestBody final Person person) {
		Person p = personService.addPerson(person);
		
		if (p == null)
			return new ResponseEntity<>(person, HttpStatus.CONFLICT);
		 else
			return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@RequestMapping(path="person", method=RequestMethod.OPTIONS)
	public ResponseEntity<Object> addPerson(@RequestBody final List<Person> personList) {
		List<Person> ps = personService.addPerson(personList);
		
		if (ps.isEmpty())
			return new ResponseEntity<>("All added", HttpStatus.OK);
		 else
			return new ResponseEntity<>(ps, HttpStatus.PARTIAL_CONTENT);
	}
	
	@RequestMapping(path="person", method=RequestMethod.POST)
	public ResponseEntity<Object> updatePerson(@RequestBody final Person person) {
		Person p = personService.updatePerson(person);
		
		if (p == null)
			return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
		 else
			return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@RequestMapping(path="person", method=RequestMethod.PATCH) 
	public ResponseEntity<Object> checkReposity() {
		Map<Integer, Person> unConsistant = personService.checkReposity();
		
		if (unConsistant.isEmpty()) {
			return new ResponseEntity<>("All records are good", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(unConsistant, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping(value="/full", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseEntity<Map<String, String>> fullQuery(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestBody final Person person,
            @RequestHeader(required = false, value = "header1") final String header1,
            @RequestHeader(required = false, value = "header2") final String header2,
            @RequestHeader(required = false, value = "authorization") final String authorization,
            @RequestParam(required = false, value = "query") final String query,
            @RequestParam(required = false, value = "userid") final String userid,
            @CookieValue(value = "username", defaultValue = "Atta") String username){
		
		Map<String, String> map = new TreeMap<>();
		
		map.put("body", person.toString());
		
		map.put("header1", header1);
		map.put("header2", header2);
		map.put("headauthorization", authorization);
		map.put("query", query);
		map.put("userId", userid);
		
		Cookie cookie = new Cookie("username", System.getProperty("user.name"));
		cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
		//cookie.setSecure(true);
	    response.addCookie(cookie);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("newheader", "I am sp header");
		ResponseEntity<Map<String, String>> entity = new ResponseEntity<>(map, headers, HttpStatus.OK);
		
		return entity;
	}
}
