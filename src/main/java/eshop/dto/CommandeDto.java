package eshop.dto;

import java.time.LocalDate;
import java.util.List;

import eshop.entity.Client;

public class CommandeDto {
	private Long numero;
	private LocalDate date;
	private Client client;
	private List<AchatDto> achat;
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<AchatDto> getAchat() {
		return achat;
	}
	public void setAchat(List<AchatDto> achat) {
		this.achat = achat;
	}
	
}
