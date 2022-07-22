package fr.iocean.species.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.iocean.species.model.Person;
import fr.iocean.species.repository.PersonRepository;

@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonRepository personRepo;

	@GetMapping
	public String listPerson(Model model) {
		List<Person> listPerson = this.personRepo.findAll();
		model.addAttribute("listPerson", listPerson);
		return "list_person";
	}

	@GetMapping("/{id}")
	public String getPersonById(@PathVariable("id") Integer id, Model model) {
		Optional<Person> personOpt = this.personRepo.findById(id);
		if (personOpt.isPresent()) {
			model.addAttribute(personOpt.get());
			return "update_person";
		}
		return "error";
	}

	@GetMapping("/create")
	public String getPersonCreate(Model model) {
		model.addAttribute(new Person());
		return "create_person";
	}

	@PostMapping
	public String CreateOrUpdate(Person personItem) {
		personRepo.save(personItem);
		return "redirect:/person";
	}

	@GetMapping("/person/delete/{id}")
	public String delete(@PathVariable("id") Integer personId) {
		Optional<Person> personToDelete = personRepo.findById(personId);
		personToDelete.ifPresent(person -> personRepo.delete(person));
		return "redirect:/person";
	}
}
