package eshop.repository;

import eshop.entity.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    List<Fournisseur> findByProduitsIsTrue();

    List<Fournisseur> findByNom(String nom);

    Page<Fournisseur> findByNomContaining(String nom, Pageable pageable);

    @Query("select f from Fournisseur f left join fetch f.produits where f.id=:id")
    Optional<Fournisseur> findByIdFetchProduits(@Param("id") Long id);

    @Query("select f from Fournisseur f where f.contact like ?1")
    List<Fournisseur> findByContactContaining(String contact);
    
    @Query("select f from Fournisseur f where f.nom like ?1")
    List<Fournisseur> findByNomContaining(String nom);
    
    @Query("select f from Fournisseur f where f.email like ?1")
    List<Fournisseur> findByEmailContaining(String email);
}
