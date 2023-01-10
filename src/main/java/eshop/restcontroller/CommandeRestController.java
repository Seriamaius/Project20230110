package eshop.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Commande;
import eshop.jsonview.Views;
import eshop.service.CommandeService;
import eshop.util.Check;

@RestController
@RequestMapping("/api/commandes")
public class CommandeRestController {
	@Autowired
	private CommandeService commandeService;
	
	//GET 
	
	@GetMapping("")
	@JsonView(Views.Common.class)
	public List<Commande> getAll(){
		return commandeService.getAll();
	}
	//par numero de commande
	@GetMapping("/{numero}")
	@JsonView(Views.Common.class)
	public Commande getByNumero(@PathVariable long numero) {
		return commandeService.getByNumero(numero);
	}
	//par date
	
	//par client
	
	//POST
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	@JsonView(Views.Common.class)
	public void create(@Valid @RequestBody Commande commande, BindingResult br) {
		Check.checkBindingResulHasError(br);
		commandeService.create(commande);
	}
	
	//PUT
	
	@PostMapping("/{numero}")
	@JsonView(Views.Common.class)
	public void update(@Valid @RequestBody Commande commande, BindingResult br,@PathVariable Long numero) {
		Check.checkBindingResulHasError(br);
		commande.setNumero(numero);
		commandeService.update(commande);
	}
	
}
