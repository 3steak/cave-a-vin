package fr.eni.cave.bo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fr.eni.cave.bo.client.Client;
import fr.eni.cave.dal.ClientRepository;
import fr.eni.cave.dal.ProprioRepository;
import fr.eni.cave.dal.UtilisateurRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestHeritage {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    ProprioRepository proprioRepository;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    void initDB() {

        // Utilisateur simple
        entityManager.persist(
            Utilisateur.builder()
                .pseudo("harrisonford@email.fr")
                .password("pwd")
                .nom("Ford")
                .prenom("Harrison")
                .build()
        );

        // Propriétaire
        entityManager.persist(
            Proprio.builder()
                .pseudo("georgelucas@email.fr")
                .password("pwd")
                .nom("Lucas")
                .prenom("George")
                .siret("12345678901234")
                .build()
        );

        // Client
        entityManager.persist(
            Client.builder()
                .pseudo("natalieportman@email.fr")
                .password("pwd")
                .nom("Portman")
                .prenom("Natalie")
                .build()
        );

        entityManager.flush();
    }

    @Test
    public void test_findAll_Utilisateur() {

        final List<Utilisateur> utilisateurs =
            utilisateurRepository.findAll();

        // Vérifications
        assertThat(utilisateurs).isNotNull();
        assertThat(utilisateurs).isNotEmpty();
        assertThat(utilisateurs.size()).isEqualTo(3);

        log.info(utilisateurs.toString());
    }

    @Test
    public void test_findAll_Proprio() {

        final List<Proprio> proprios =
            proprioRepository.findAll();

        // Vérifications
        assertThat(proprios).isNotNull();
        assertThat(proprios).isNotEmpty();
        assertThat(proprios.size()).isEqualTo(1);

        log.info(proprios.toString());
    }

    @Test
    public void test_findAll_Client() {

        final List<Client> clients =
            clientRepository.findAll();

        // Vérifications
        assertThat(clients).isNotNull();
        assertThat(clients).isNotEmpty();
        assertThat(clients.size()).isEqualTo(1);

        log.info(clients.toString());
    }
}
