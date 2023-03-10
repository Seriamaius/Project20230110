package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Fournisseur;
import eshop.exception.FournisseurException;
import eshop.exception.IdException;
import eshop.repository.FournisseurRepository;
import eshop.repository.ProduitRepository;

@Service
public class FournisseurService {

	@Autowired
	private FournisseurRepository fournisseurRepository;

	@Autowired
	private ProduitRepository produitRepository;

	public Fournisseur create(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		return fournisseurRepository.save(fournisseur);
	}

	private void checkFournisseurIsNotNull(Fournisseur fournisseur) {
		if (fournisseur == null) {
			throw new FournisseurException("fournisseur null");
		}
	}

	public Fournisseur getFournisseurProduits(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		return fournisseurRepository.findByIdFetchProduits(fournisseur.getId()).orElseThrow(() -> {
			throw new FournisseurException("Aucun produits pour ce fournisseur");
		});
	}

	public Fournisseur getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return fournisseurRepository.findById(id).orElseThrow(() -> {
			throw new FournisseurException("Fournisseur unknown");
		});
	}

	public List<Fournisseur> getByNomContaining(String nom) {
		if (nom == null || nom.isEmpty()) {
			throw new FournisseurException("Nom vide");
		}
		return fournisseurRepository.findByNomContaining(nom);
	}

	public List<Fournisseur> getByContactContaining(String contact) {
		if (contact == null || contact.isEmpty()) {
			throw new FournisseurException("Contact vide");
		}
		return fournisseurRepository.findByContactContaining(contact);
	}

	public List<Fournisseur> getByEmailContaining(String email) {
		if (email == null || email.isEmpty()) {
			throw new FournisseurException("Email vide");
		}
		return fournisseurRepository.findByEmailContaining(email);
	}


	public void delete(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		deleteById(fournisseur.getId());
	}

    private void deleteById(Long id) {
        Fournisseur fournisseur = getById(id);
        produitRepository.updateByFournisseurSetOnSaleToFalse(fournisseur);
        produitRepository.updateByfournisseurSetfournisseurToNull(fournisseur);
        fournisseurRepository.delete(fournisseur);
    }

	public void delete(Long id) {
		deleteById(id);
	}


	public List<Fournisseur> getAll() {
		return fournisseurRepository.findAll();
	}

	public Page<Fournisseur> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new FournisseurException();
		}
		return fournisseurRepository.findAll(pageable);
	}

	public Page<Fournisseur> getNextPage(Page<Fournisseur> page) {
		if (page == null) {
			throw new FournisseurException();
		}
		return fournisseurRepository.findAll(page.nextOrLastPageable());
	}

	public Page<Fournisseur> getPreviousPage(Page<Fournisseur> page) {
		if (page == null) {
			throw new FournisseurException();
		}
		return fournisseurRepository.findAll(page.previousOrFirstPageable());
	}

	public Fournisseur update(Fournisseur fournisseur) {
		Fournisseur fournisseurEnBase = getById(fournisseur.getId());
		fournisseurEnBase.setNom(fournisseur.getNom() != null ? fournisseur.getNom() : fournisseurEnBase.getNom());
		if (fournisseur.getAdresse() != null) {
			fournisseurEnBase
					.setAdresse(new Adresse(fournisseur.getAdresse().getNumero(), fournisseur.getAdresse().getRue(),
							fournisseur.getAdresse().getCodePostal(), fournisseur.getAdresse().getVille()));
		} else {
			fournisseurEnBase.setAdresse(null);
		}
		return fournisseurRepository.save(fournisseurEnBase);
	}
}