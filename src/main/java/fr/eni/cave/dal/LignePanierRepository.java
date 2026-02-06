package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.cave.bo.client.LignePanier;

public interface LignePanierRepository extends JpaRepository<LignePanier, Integer>{

}
