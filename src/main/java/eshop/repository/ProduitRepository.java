package eshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Fournisseur;
import eshop.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Modifying
    @Transactional
    @Query("update Produit f set f.fournisseur=null where f.fournisseur=:fournisseur")
    void updateByfournisseurSetfournisseurToNull(@Param("fournisseur") Fournisseur fournisseur);

    @Modifying
    @Transactional
    @Query("delete Produit f where f.fournisseur=:fournisseur")
    void deleteByFournisseur(@Param("fournisseur") Fournisseur fournisseur);

    List<Produit> findByLibelle(String libelle);

    List<Produit> findByLibelleContaining(String libelle);

    Page<Produit> findByLibelleContaining(String libelle, Pageable pageable);

    List<Produit> findByFournisseur(Fournisseur fournisseur);
    
    @Modifying
	@Transactional
	@Query("update Produit p set p.onSale=false where p.fournisseur=:fournisseur")
    void updateByFournisseurSetOnSaleToFalse(@Param("fournisseur") Fournisseur fournisseur);
}
