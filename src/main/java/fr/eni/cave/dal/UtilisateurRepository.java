package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.cave.bo.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
}
