package eshop.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.jsonview.Views;

@MappedSuperclass
public abstract class Compte {
	@JsonView(Views.Common.class)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCompte")
	private Long id;
	@JsonView(Views.Common.class)
	@Column(name = "name")
	@NotEmpty(message = "nom manquant")
	private String nom;
	@JsonView(Views.Common.class)
	@Column(name = "email")
	@NotEmpty(message = "email manquant")
	private String email;
	@JsonView(Views.Common.class)
	@Embedded
	private Adresse adresse;

	public Compte() {

	}

	public Compte(String nom, String email, Adresse adresse) {
		super();
		this.nom = nom;
		this.email = email;
		this.adresse = adresse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compte other = (Compte) obj;
		return Objects.equals(id, other.id);
	}

}
