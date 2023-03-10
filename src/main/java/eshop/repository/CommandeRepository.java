package eshop.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eshop.entity.Client;
import eshop.entity.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long>{
	
	Optional<Commande> findByNumero(@Param("numero")Long numero);	
	List<Commande> findByClient (Client client);
	List<Commande> findByDate(LocalDate date);
	@Query("select c from Commande c left join fetch c.client where c.numero=:numero")
	Optional<Commande> findByNumberFetchClient(@Param("numero") Long numero);
}
