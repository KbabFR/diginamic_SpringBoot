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

import fr.iocean.species.model.Species;
import fr.iocean.species.repository.SpeciesRepository;

@Controller
@RequestMapping("/species")
public class SpeciesController {

	@Autowired
	private SpeciesRepository speciesRepo;

	@GetMapping
	public String listSpecies(Model model) {
		List<Species> listSpecies = this.speciesRepo.findAll();
		model.addAttribute("listSpecies", listSpecies);
		return "list_species";
	}

	@GetMapping("/{id}")
	public String getSpeciesById(@PathVariable("id") Integer id, Model model) {
		Optional<Species> speciesOpt = this.speciesRepo.findById(id);
		if (speciesOpt.isPresent()) {
			model.addAttribute(speciesOpt.get());
			return "update_species";
		}
		return "error";
	}

	@GetMapping("/create")
	public String getSpeciesCreate(Model model) {
		model.addAttribute(new Species());
		return "create_species";
	}

	@PostMapping
	public String CreateOrUpdate(Species speciesItem) {
		speciesRepo.save(speciesItem);
		return "redirect:/species";
	}

	@GetMapping("/species/delete/{id}")
	public String delete(@PathVariable("id") Integer speciesId) {
		Optional<Species> speciesToDelete = speciesRepo.findById(speciesId);
		speciesToDelete.ifPresent(species -> speciesRepo.delete(species));
		return "redirect:/species";
	}
}
