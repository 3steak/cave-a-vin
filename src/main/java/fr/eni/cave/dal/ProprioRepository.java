package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.eni.cave.bo.Proprio;

public interface ProprioRepository extends JpaRepository<Proprio, String> {
}
