package eshop.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Fournisseur;
import eshop.jsonview.Views;
import eshop.service.FournisseurService;

@RestController
@RequestMapping("/api/fournisseur")
public class FournisseursRestController {

	@Autowired
	private FournisseurService fournisseurServ;
	
	// Creation POST
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	@JsonView(Views.Common.class)
	public Fournisseur create(@Valid @RequestBody Fournisseur fournisseur, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return fournisseurServ.create(fournisseur);
	}
	
	// Modification PUT
	@PutMapping("/{id}")
	@JsonView(Views.Common.class)
	public Fournisseur update(@Valid @RequestBody Fournisseur fournisseur, BindingResult br, @PathVariable Long id) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		fournisseur.setId(id);
		return fournisseurServ.update(fournisseur);
	}
	
	// Suppresion Delete
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		// TODO: fournisseurServ.delete(id);
	}
	
	// Recherche GET
	@GetMapping("/id/{id}")
	@JsonView(Views.Common.class)
	public Fournisseur getById(@PathVariable Long id) {
		return fournisseurServ.getById(id);
	}
	
	@GetMapping("/nom/{nom}")
	@JsonView(Views.Common.class)
	public Fournisseur getByNom(@PathVariable String nom) {
		return fournisseurServ.getByNom(nom);
	}
	
	@GetMapping("/contact/{contact}")
	@JsonView(Views.Common.class)
	public Fournisseur getByContact(@PathVariable String contact) {
		return fournisseurServ.getByContact(contact);
	}
	
	@GetMapping("/email/{email}")
	@JsonView(Views.Common.class)
	public Fournisseur getByEmail(@PathVariable String email) {
		return fournisseurServ.getByEmail(email);
	}

	// Recherche page fournisseurs GET
	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Fournisseur> getAllFournisseur() {
		return fournisseurServ.getAll();
	}

	// Recherche avec detail produits GET
	@GetMapping("/produits/{id}")
	@JsonView(Views.FournisseurWithListProduits.class)
	public Fournisseur getByIdFetchProduits(@PathVariable Long id) {
		return fournisseurServ.getFournisseurProduits(fournisseurServ.getById(id));
	}
}
