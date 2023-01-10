package eshop.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.jsonview.Views;

@Entity
@Table(name = "supplier")
@AttributeOverride(name = "adresse.numero", column = @Column(name = "supplier_number", length = 50))
@AttributeOverride(name = "adresse.rue", column = @Column(name = "supplier_street", length = 255))
@AttributeOverride(name = "adresse.codePostal", column = @Column(name = "supplier_zip_code", length = 50))
@AttributeOverride(name = "adresse.ville", column = @Column(name = "supplier_city", length = 255))
@AttributeOverride(name = "id", column = @Column(name = "supplier_id"))
@AttributeOverride(name = "nom", column = @Column(name = "supplier_name", length = 200))
@AttributeOverride(name = "email", column = @Column(name = "supplier_email", length = 255))
@SequenceGenerator(name = "seqCompte", sequenceName = "supplier_id_seq", initialValue = 100, allocationSize = 1)
public class Fournisseur extends Compte {
	@JsonView(Views.Common.class)
	@Column(name = "contact")
	@NotEmpty(message = "contact manquant")
	private String contact;
	@JsonView(Views.FournisseurWithListProduits.class)
	@OneToMany(mappedBy = "fournisseur", fetch = FetchType.LAZY) // LAZY par defaut sur les collections
	private List<Produit> produits;

	public Fournisseur() {

	}

	public Fournisseur(String nom, String email, Adresse adresse, String contact) {
		super(nom, email, adresse);
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public boolean isEnActivite() {
		return enActivite;
	}

	public void setEnActivite(boolean enActivite) {
		this.enActivite = enActivite;
	}
}
