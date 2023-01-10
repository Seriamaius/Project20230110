package eshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	List<Client> findByIdIsNotNull();

	List<Client> findByPrenom(String prenom);

	List<Client> findByPrenomContaining(String prenom);

	Page<Client> findByPrenomContaining(String prenom, Pageable pageable);

	Optional<Client> findByCommandes(String commandes);


	List<Client> findClientByEmailContaining(String email);

	List<Client> findByNomContaining(String nom);

	@Query("select c from Client c left join fetch c.commandes where c.id=:id")
	Optional<Client> findByIdFetchCommandes(@Param("id") Long id);
}
