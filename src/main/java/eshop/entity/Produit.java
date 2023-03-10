package eshop.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import eshop.jsonview.Views;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "seqProduit", sequenceName = "product_id_seq", initialValue = 100, allocationSize = 1)
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProduit")
	@JsonView(Views.Common.class)
	@Column(name = "product_id")
	private Long id;

	@JsonView(Views.Common.class)
	@Column(name = "product_name", nullable = false)
	@NotBlank(message = "Le libellé est vide !")
	private String libelle;

	@JsonView(Views.Common.class)
	@Column(name = "product_description")
	@NotBlank(message = "La description est vide !")
	private String description;

	@JsonView(Views.Common.class)
	@Column(name = "product_price")
	@NotNull(message = "Le prix est null !")
	private double prix;

	@JsonView(Views.Common.class)
	@ManyToOne(fetch = FetchType.EAGER) // charger tout le temps EAGER par defaut pour @XXXToOne
	@JoinColumn(name = "product_supplier_id", foreignKey = @ForeignKey(name = "fk_product_product_supplier_id"))
	@NotNull(message = "Le fournisseur est null !")
	private Fournisseur fournisseur;

	@OneToMany(mappedBy = "id.produit")
	private List<Achat> achats;

	@Version
	private int version;

	@JsonView(Views.Common.class)
	@Column(name = "product_on_sale")
	private boolean onSale;

	public List<Achat> getAchats() {
		return achats;
	}

	public void setAchats(List<Achat> achats) {
		this.achats = achats;
	}

	public Produit() {

	}

	public Produit(String libelle, String description, double prix) {
		this(libelle, description, prix, null);
	}

	public Produit(String libelle, String description, double prix, Fournisseur fournisseur) {
		super();
		this.libelle = libelle;
		this.description = description;
		this.prix = prix;
		this.fournisseur = fournisseur;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean getOnSale() {
		return this.onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
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
		Produit other = (Produit) obj;
		return Objects.equals(id, other.id);
	}

}
