package eshop.repository;

import eshop.entity.Fournisseur;
import eshop.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Modifying
    @Transactional
    @Query("update Produit f set f.fournisseur=null where f.fournisseur=:fournisseur")
    void updateByfournisseurSetfournisseurToNull(@Param("fournisseur")Fournisseur fournisseur);

    @Modifying
    @Transactional
    @Query("delete Produit f where f.fournisseur=:fournisseur")
    void deleteByFournisseur(@Param("fournisseur")Fournisseur fournisseur);

}
