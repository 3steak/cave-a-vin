package fr.eni.cave.association;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import fr.eni.cave.bo.client.Adresse;
import fr.eni.cave.bo.client.Client;
import fr.eni.cave.dal.ClientRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestOneToOneUni {

    // Tester le repository Client car Adresse et Client
    // ont le mm cycle de vie (cascade)
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository repository;

    @Test
    void test_save_and_delete_client_with_adresse() {

        // creation de l adresse
        Adresse adresse = Adresse.builder()
                .rue("15 rue de Paris")
                .codePostal("35000")
                .ville("Rennes")
                .build();

        // creation du client + association
        Client client = Client.builder()
                .pseudo("bobepong@email.fr")
                .password("carré")
                .nom("Eponge")
                .prenom("Bob")
                .adresse(adresse)
                .build();

        // persist client pour test le delete apres
        entityManager.persist(client);

        // Forcer ecriture en DB
        entityManager.flush();

        // verification que l adresse est bien persist
        assertThat(adresse.getId()).isNotNull();
        assertThat(adresse.getId()).isGreaterThan(0);

        entityManager.clear();

        // Appel du comportement
        Client clientFromDb = repository.findById(client.getPseudo()).orElse(null);

        assertThat(clientFromDb).isNotNull();
        assertThat(clientFromDb.getAdresse()).isNotNull();

        log.info("Client : {}", clientFromDb);
        log.info("Adresse : {}", clientFromDb.getAdresse());

        // verif champs Adresse
        assertThat(clientFromDb.getAdresse().getRue()).isEqualTo("15 rue de Paris");
        assertThat(clientFromDb.getAdresse().getCodePostal()).isEqualTo("35000");
        assertThat(clientFromDb.getAdresse().getVille()).isEqualTo("Rennes");

        // Suppression du client
        repository.delete(clientFromDb);

        // Forcer ecriture en DB
        entityManager.flush();
        entityManager.clear();

        // Verif suppression
        Client deletedClient = repository.findById(client.getPseudo()).orElse(null);
        assertThat(deletedClient).isNull();

        Adresse deletedAdresse = entityManager.find(Adresse.class, adresse.getId());
        assertThat(deletedAdresse).isNull();

        log.info("Suppression Client + Adresse OK (cascade)");
    }
}
