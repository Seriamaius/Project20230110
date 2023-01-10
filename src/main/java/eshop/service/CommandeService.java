package eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Client;
import eshop.entity.Commande;
import eshop.exception.CommandeException;
import eshop.exception.IdException;
import eshop.repository.CommandeRepository;

@Service
public class CommandeService {
	@Autowired
	private CommandeRepository commandeRepo;
	//@Autowired
	//private ClientRepository clientRepo;

	public void create(Commande commande) {
		checkCommandeIsNotNull(commande);
		/*if (commande.getAchats()==null || commande.getAchats().isEmpty()) {
			throw new CommandeException("pas de liste d'achat !");
		}*/
		commandeRepo.save(commande);
	}
	private void checkCommandeIsNotNull(Commande commande) {
		if (commande == null) {
			throw new CommandeException("commande null");
		}
	}

	public Commande getCommandeClient(Commande commande) {
		checkCommandeIsNotNull(commande);
		return commandeRepo.findByNumberFetchClient(commande.getNumero()).orElseThrow(() -> {
			throw new CommandeException("Aucun Client pour la commande ");
		});
	}

	public Commande getByNumero(Long numero) {
		if (numero == null) {
			throw new IdException();
		}
		return commandeRepo.findByNumero(numero).orElseThrow(() -> {
			throw new CommandeException("Commande unknow ");
		});
	}

	public void delete(Commande commande) {
		checkCommandeIsNotNull(commande);
		deleteByNumero(commande.getNumero());
	}

	public void delete(Long numero) {
		deleteByNumero(numero);
	}

	private void deleteByNumero(long numero) {
		Commande commande = getByNumero(numero);
		// clientRepo.updateByCommandeSetCommandeToNull(commande);
		commandeRepo.delete(commande);
	}

	public List<Commande> getAll() {
		return commandeRepo.findAll();
	}

	public Page<Commande> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new CommandeException();
		}
		return commandeRepo.findAll(pageable);
	}

	public Page<Commande> getNextPage(Page<Commande> page) {
		if (page == null) {
			throw new CommandeException();
		}
		return commandeRepo.findAll(page.nextOrLastPageable());
	}

	public Page<Commande> getPrevious(Page<Commande> page) {
		if (page == null) {
			throw new CommandeException();
		}
		return commandeRepo.findAll(page.previousOrFirstPageable());
	}

	public Commande update(Commande commande) {
		Commande commandeEnBase = getByNumero(commande.getNumero());
		commandeEnBase.setNumero(null);
		if (commande.getClient() != null) {
			commandeEnBase.setClient(new Client(
					commande.getClient().getCivilite(),
					commande.getClient().getNom(),
					commande.getClient().getPrenom(),
					commande.getClient().getEmail(),
					commande.getClient().getAdresse(),
					commande.getClient().getDateInscription()));
		} else {
			commandeEnBase.setClient(null);
		}
		return commandeRepo.save(commandeEnBase);
	}
}
