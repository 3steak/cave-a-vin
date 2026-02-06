package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.cave.bo.vin.Couleur;

public interface CouleurRepository extends JpaRepository<Couleur, Integer> {
}
