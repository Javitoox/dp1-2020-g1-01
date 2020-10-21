package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
		  
		  List<Person> persons = new ArrayList<Person>();
		  Person person = new Person();
		  person.setFirstName("Javier ");
		  person.setLastName("Martínez Fernández");
		  
		  Person person1 = new Person();
		  person1.setFirstName("Evelyn ");
		  person1.setLastName("Yugsi Yugsi");
		  
		  Person person2 = new Person();
		  person2.setFirstName("Maria Isabel ");
		  person2.setLastName("Ramos Blanco");
		  
		  Person person3 = new Person();
		  person3.setFirstName("Fernando ");
		  person3.setLastName("Alonso");
		  
		  Person person4 = new Person();
		  person4.setFirstName("Gonzalo ");
		  person4.setLastName("Álvarez García");
		  
		  Person person5 = new Person();
		  person5.setFirstName("Javier ");
		  person5.setLastName("Vilariño Mayo");
		  
		  
		  persons.add(person);
		  persons.add(person1);
		  persons.add(person2);
		  persons.add(person3);
		  persons.add(person4);
		  persons.add(person5);

		  model.put("persons", persons);
		  model.put("title", "G1-01");
		  model.put("group", "Alumnos");
		  
	    return "welcome";
	  }
}
