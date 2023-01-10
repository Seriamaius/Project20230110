package eshop.service;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import eshop.repository.AchatRepository;
import eshop.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Client;
import eshop.exception.ClientException;
import eshop.exception.IdException;
import eshop.repository.ClientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private CommandeRepository commandeRepository;

	@Autowired
	private AchatRepository achatRepository;


	public Client create(Client client) {
		checkClientIsNotNull(client);
		clientRepo.save(client);
		return client;
	}

	public void checkClientIsNotNull(Client client) {
		if (client == null) {
			throw new ClientException("Client = null");
		}
	}

	public Client getById(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findById(id).orElseThrow(() -> {
			throw new ClientException("Client inconnu");
		});
	}

	public List<Client> getByNom(String nom) {
		if (nom == null || nom.isEmpty()) {
			throw new ClientException("Client empty");
		}
		return clientRepo.findByNomContaining(nom);
	}

	public List<Client> getByEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new ClientException("email vide");
		}
		return clientRepo.findClientByEmailContaining(email);
	}

	public Page<Client> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(pageable);
	}

	public List<Client> getAll() {
		return clientRepo.findAll();
	}

	public Page<Client> getNextPage(Page<Client> page) {
		if (page == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(page.nextOrLastPageable());
	}

	public Page<Client> getPreviousPage(Page<Client> page) {
		if (page == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(page.previousOrFirstPageable());
	}

	public Client update(Client client) {
		Client clientEnBase = getById(client.getId());
		clientEnBase.setCivilite(client.getCivilite() != null ? client.getCivilite() : clientEnBase.getCivilite());
		clientEnBase.setNom(client.getNom() != null ? client.getNom() : clientEnBase.getNom());
		clientEnBase.setPrenom(client.getPrenom() != null ? client.getPrenom() : clientEnBase.getPrenom());
		clientEnBase.setEmail(client.getEmail() != null ? client.getEmail() : clientEnBase.getEmail());
		if (client.getAdresse() != null) {
			clientEnBase.setAdresse(
					new Adresse(
							client.getAdresse().getNumero(),
							client.getAdresse().getRue(),
							client.getAdresse().getCodePostal(),
							client.getAdresse().getVille()));
		} else {
			clientEnBase.setAdresse(null);
		}
		clientEnBase.setDateInscription(
				client.getDateInscription() != null ? client.getDateInscription() : clientEnBase.getDateInscription());
		return clientRepo.save(clientEnBase);
	}

	public Client updatePassword(Client client, String newPassword) {
		Client clientEnBase = getById(client.getId());
		if (newPassword == null || newPassword.isEmpty()) {
			throw new ClientException("Nouveau mot de passe manquant");
		}
		if (newPassword.equals(clientEnBase.getPassword())) {
			throw new ClientException("Le nouveau mot de passe doit etre different de l'ancien mot de passe");
		}
		clientEnBase.setPassword(newPassword);
		return clientRepo.save(clientEnBase);
	}


	public Client getClientCommandes(Client client) {
		checkClientIsNotNull(client);
		return clientRepo.findByIdFetchCommandes(client.getId()).orElseThrow(() -> {
			throw new ClientException("Aucune commandes pour ce client");
		});
	}

	public Client getClientCommandesId(Long id) {
		if (id == null) {
			throw new IdException();
		}
		return clientRepo.findByIdFetchCommandes(id).orElseThrow(() -> {
			throw new ClientException("Client inconnu");
		});
	}

	public void delete(Long id) {
		clientRepo.deleteById(id);
	}

	public void delete(Client client) {
		checkClientIsNotNull(client);
		clientRepo.deleteById(client.getId());
	}

}
