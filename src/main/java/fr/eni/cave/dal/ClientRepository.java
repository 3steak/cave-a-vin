package fr.eni.cave.dal;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.cave.bo.client.Client;

public interface ClientRepository extends JpaRepository <Client, String> {

}
