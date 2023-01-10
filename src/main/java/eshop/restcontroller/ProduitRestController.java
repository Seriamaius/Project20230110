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

import com.fasterxml.jackson.annotation.JsonView;

import eshop.entity.Produit;
import eshop.jsonview.Views;
import eshop.service.FournisseurService;
import eshop.service.ProduitService;
import eshop.util.Check;

@RestController
@RequestMapping("/api/produit")
public class ProduitRestController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private FournisseurService fournisseurService;

    // GET

    @GetMapping("")
    @JsonView(Views.Common.class)
    public List<Produit> getAll() {
        return produitService.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Common.class)
    public Produit getById(@PathVariable Long id) {
        return produitService.getById(id);
    }

    @GetMapping("libelle/{libelle}")
    @JsonView(Views.Common.class)
    public List<Produit> getByLibelle(@PathVariable String libelle) {
        return produitService.getByLibelle(libelle);
    }

    @GetMapping("fournisseur/{fournisseurId}")
    @JsonView(Views.Common.class)
    public List<Produit> getByFournisseur(@PathVariable Long fournisseurId) {
        return produitService.getByFournisseur(fournisseurService.getById(fournisseurId));
    }

    // POST

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @JsonView(Views.Common.class)
    public Produit create(@Valid @RequestBody Produit produit, BindingResult br) {
        Check.checkBindingResulHasError(br);
        produit.setOnSale(true);
        return produitService.create(produit);
    }

    // PUT

    @PutMapping("/{id}")
    @JsonView(Views.Common.class)
    public Produit update(@Valid @RequestBody Produit produit, BindingResult br, @PathVariable Long id) {
        Check.checkBindingResulHasError(br);
        produit.setId(id);
        return produitService.update(produit);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        produitService.delete(id);
    }
}
