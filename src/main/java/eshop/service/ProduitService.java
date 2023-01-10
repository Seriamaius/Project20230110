package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eshop.entity.Fournisseur;
import eshop.entity.Produit;
import eshop.exception.IdException;
import eshop.exception.ProduitException;
import eshop.repository.ProduitRepository;

@Service
public class ProduitService {

    @Autowired
    ProduitRepository produitRepository;

    public Produit create(Produit produit) {
        if (produit == null) {
            throw new ProduitException();
        }
        return produitRepository.save(produit);
    }

    public Produit getById(Long id) {
        if (id == null) {
            throw new IdException();
        }
        return produitRepository.findById(id).orElseThrow(() -> {
            throw new ProduitException("produit inconnu");
        });
    }

    public List<Produit> getByLibelle(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new IdException();
        }
        return produitRepository.findByLibelleContaining(libelle);
    }

    public List<Produit> getByFournisseur(Fournisseur fournisseur) {
        if (fournisseur == null) {
            throw new IdException();
        }
        return produitRepository.findByFournisseur(fournisseur);
    }

    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    public Produit update(Produit produit) {
        Produit produitEnBase = getById(produit.getId());
        produitEnBase
                .setLibelle(produit.getLibelle() == null || produit.getLibelle().isBlank() ? produitEnBase.getLibelle()
                        : produit.getLibelle());
        produitEnBase.setDescription(
                produit.getDescription() == null || produit.getDescription().isBlank() ? produitEnBase.getDescription()
                        : produit.getDescription());
        produitEnBase.setFournisseur(produit.getFournisseur());
        produitEnBase.setPrix(produit.getPrix());
        produitEnBase.setOnSale(produit.getOnSale());
        return produitRepository.save(produitEnBase);
    }
}
