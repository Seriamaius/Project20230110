package eshop.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eshop.entity.Adresse;
import eshop.entity.Client;
import eshop.exception.ClientException;
import eshop.exception.IdException;
import eshop.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepo;
	
	
	
	public void create(Client client) {
		checkClientIsNotNull(client);
		if (client.getCivilite() == null) {
			throw new ClientException("Civilite manquante");
		}
		if (client.getNom() == null || client.getNom().isEmpty()) {
			throw new ClientException("Nom manquant");
		}
		if (client.getPrenom() == null || client.getPrenom().isEmpty()) {
			throw new ClientException("Prenom manquant");
		}
		if (client.getEmail() == null || client.getEmail().isEmpty()) {
			throw new ClientException("Email manquant");
		}
		if (client.getAdresse() == null) {
			throw new ClientException("Adresse manquante");
		}
		if (client.getPassword() == null || client.getPassword().isEmpty()) {
			throw new ClientException("Mot de passe manquant");
		}
		if (client.getDateInscription() == null) {
			client.setDateInscription(LocalDate.now());
		}
		clientRepo.save(client);
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
							client.getAdresse().getVille()
					));
		} else {
			clientEnBase.setAdresse(null);
		}
		clientEnBase.setDateInscription(client.getDateInscription() != null ? client.getDateInscription() : clientEnBase.getDateInscription());
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
	
	public Page<Client> getAll(Pageable pageable) {
		if (pageable == null) {
			throw new ClientException();
		}
		return clientRepo.findAll(pageable);
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
	
	public Client getClientCommandes(Client client) {
		checkClientIsNotNull(client);
		return clientRepo.findByIdFetchCommandes(client.getId()).orElseThrow(() -> {
			throw new ClientException("Aucune commandes pour ce client");
		});		
	}
	
	
	public Client getById(Long id) {
		if(id == null) {
			throw new IdException();
		}
		return clientRepo.findById(id).orElseThrow(()->{
			throw new ClientException("Client inconnu");
		});	
	}

	public void checkClientIsNotNull(Client client) {
		if (client == null) {
			throw new ClientException("Client is null");
		}
	}
}
