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
    void test_save_client_with_adresse() {

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

        //  sauvegarde du client (cascade vers adresse)
        Client clientDB = repository.save(client);

//        Forcer ecriture en DB
        entityManager.flush();
        entityManager.clear();

        // recup du client
        Client clientFromDb = repository.findById(clientDB.getPseudo()).orElse(null);

        assertThat(clientFromDb).isNotNull();
        assertThat(clientFromDb.getAdresse()).isNotNull();

        log.info("Client récupéré : {}", clientFromDb);
        log.info("Adresse associée : {}", clientFromDb.getAdresse());

        // verif champs Adresse
        assertThat(clientFromDb.getAdresse().getRue()).isEqualTo("15 rue de Paris");
        assertThat(clientFromDb.getAdresse().getCodePostal()).isEqualTo("35000");
        assertThat(clientFromDb.getAdresse().getVille()).isEqualTo("Rennes");
    }
}
