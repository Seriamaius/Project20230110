package eshop.repository;

import eshop.entity.Achat;
import eshop.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AchatRepository extends JpaRepository<Achat, Long> {

    @Modifying
    @Transactional
    @Query("update Achat a set a.id.commande=null where a.id.commande=:commande")
    void updateByAchatKeySetAchatKeyToNull(@Param("commande") Commande commande);

    @Modifying
    @Transactional
    @Query("delete Achat a where a.id.commande=:commande")
    void deleteByAchatKey(@Param("commande") Commande commande);

}
