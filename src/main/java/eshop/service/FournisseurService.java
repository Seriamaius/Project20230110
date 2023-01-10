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

<<<<<<< HEAD
	public void create(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		if (fournisseur.getNom() == null || fournisseur.getNom().isEmpty()) {
			throw new FournisseurException("nom is null");
		}
		if (fournisseur.getEmail() == null || fournisseur.getEmail().isEmpty()) {
			throw new FournisseurException("email is null");
		}
		fournisseurRepository.save(fournisseur);
	}
=======
    public Fournisseur create(Fournisseur fournisseur) {
        checkFournisseurIsNotNull(fournisseur);
        return fournisseurRepository.save(fournisseur);
    }
>>>>>>> main

	private void checkFournisseurIsNotNull(Fournisseur fournisseur) {
		if (fournisseur == null) {
			throw new FournisseurException("fournisseur null");
		}
	}

	public Fournisseur getFournisseurProduits(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		return fournisseurRepository.findByIdFetchProduits(fournisseur.getId()).orElseThrow(() -> {
			throw new FournisseurException("Aucun produi" + "ts pour ce fournisseur");
		});
	}

<<<<<<< HEAD
	public Fournisseur getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return fournisseurRepository.findById(id).orElseThrow(() -> {
			throw new FournisseurException("Fournisseur unknown");
		});
	}

	public Fournisseur getByContact(String contact) {
		if (contact == null || contact.isEmpty()) {
			throw new FournisseurException("Contact vide");
		}
		return fournisseurRepository.findByContact(contact).orElseThrow(() -> {
			throw new FournisseurException("Contact inconnu");
		});
	}
=======
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
    
    
>>>>>>> main

	public void delete(Fournisseur fournisseur) {
		checkFournisseurIsNotNull(fournisseur);
		deleteById(fournisseur.getId());
	}

	public void delete(Long id) {
		deleteById(id);
	}

	private void deleteById(Long id) {
		Fournisseur fournisseur = getById(id);
		produitRepository.updateByfournisseurSetfournisseurToNull(fournisseur);
		fournisseurRepository.delete(fournisseur);
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

	public Page<Fournisseur> getPrevious(Page<Fournisseur> page) {
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
