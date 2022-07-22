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

import fr.iocean.species.model.Animal;
import fr.iocean.species.repository.AnimalRepository;

@Controller
@RequestMapping("/animal")
public class AnimalController {

	@Autowired
	private AnimalRepository animalRepo;

	@GetMapping
	public String listAnimal(Model model) {
		List<Animal> listAnimal = this.animalRepo.findAll();
		model.addAttribute("listanimal", listAnimal);
		return "list_animal";
	}

	@GetMapping("/{id}")
	public String getanimalById(@PathVariable("id") Integer id, Model model) {
		Optional<Animal> animalOpt = this.animalRepo.findById(id);
		if (animalOpt.isPresent()) {
			model.addAttribute(animalOpt.get());
			return "update_animal";
		}
		return "error";
	}

	@GetMapping("/create")
	public String getanimalCreate(Model model) {
		model.addAttribute(new Animal());
		return "create_animal";
	}

	@PostMapping
	public String CreateOrUpdate(Animal animalItem) {
		animalRepo.save(animalItem);
		return "redirect:/animal";
	}

	@GetMapping("/anial/delete/{id}")
	public String delete(@PathVariable("id") Integer animalId) {
		Optional<Animal> animalToDelete = animalRepo.findById(animalId);
		animalToDelete.ifPresent(animal -> animalRepo.delete(animal));
		return "redirect:/animal";
	}

}
