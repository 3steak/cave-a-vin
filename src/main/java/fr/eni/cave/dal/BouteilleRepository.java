package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.cave.bo.vin.Bouteille;

public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {
}
