package fr.eni.cave.bo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fr.eni.cave.bo.client.Client;
import fr.eni.cave.dal.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestClient {

    private static final Logger log = LoggerFactory.getLogger(TestClient.class);

    @Autowired
    private ClientRepository repository;

    @Test
    void test_save_client() {
        Client client = Client
        		.builder()
                .pseudo("bobepong@email.fr")
                .password("carré")
                .nom("Eponge")
                .prenom("Bob")
                .build();

//        appel du comportement
       final Client clientDB = repository.save(client);

       // Vérification du toString (le mot de passe ne doit PAS apparaitre)
       log.info(clientDB.toString());
       assertThat(clientDB.toString()).doesNotContain("carré");
       
       assertThat(clientDB).isNotNull();
       assertThat(clientDB).isEqualTo(client);


    }

    @Test
    void test_delete_client() {
       final Client client = Client
        		.builder()
                .pseudo("patrick@email.fr")
                .password("secret")
                .nom("Etoile")
                .prenom("Patrick")
                .build();

        Client clientDB = repository.save(client);
        assertThat(repository.findById(clientDB.getPseudo())).isPresent();

        repository.delete(clientDB);

        assertThat(repository.findById(clientDB.getPseudo())).isEmpty();
    }
}
