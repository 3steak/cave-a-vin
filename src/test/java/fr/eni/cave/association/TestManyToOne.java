package fr.eni.cave.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import fr.eni.cave.dal.BouteilleRepository;
import fr.eni.cave.dal.CouleurRepository;
import fr.eni.cave.dal.RegionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
class TestManyToOne {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BouteilleRepository bouteilleRepository;

    @Autowired
    private CouleurRepository couleurRepository;

    @Autowired
    private RegionRepository regionRepository;

    private Couleur rouge;
    private Couleur blanc;
    private Couleur rose;

    private Region grandEst;
    private Region paysDeLaLoire;
    private Region nouvelleAquitaine;

    @BeforeEach
    void initData() {
        rouge = couleurRepository.save(Couleur.builder().nom("Rouge").build());
        blanc = couleurRepository.save(Couleur.builder().nom("Blanc").build());
        rose  = couleurRepository.save(Couleur.builder().nom("Rosé").build());

        grandEst = regionRepository.save(Region.builder().nom("Grand Est").build());
        paysDeLaLoire = regionRepository.save(Region.builder().nom("Pays de la Loire").build());
        nouvelleAquitaine = regionRepository.save(Region.builder().nom("Nouvelle Aquitaine").build());
    }

    @Test
    void test_save() {
        Bouteille bouteille = Bouteille.builder()
                .nom("Blanc du DOMAINE ENI Ecole")
                .millesime("2022")
                .prix(23.95f)
                .quantite(1298)
                .build();

        bouteille.setCouleur(blanc);
        bouteille.setRegion(paysDeLaLoire);

        Bouteille bouteilleDB = bouteilleRepository.save(bouteille);

        assertThat(bouteilleDB.getId()).isNotNull();
        assertThat(bouteilleDB.getCouleur()).isEqualTo(blanc);
        assertThat(bouteilleDB.getRegion()).isEqualTo(paysDeLaLoire);

        log.info(bouteilleDB.toString());
    }

    @Test
    void test_save_bouteilles_regions_couleurs() {
        List<Bouteille> bouteilles = List.of(
                Bouteille.builder()
                        .nom("Rouge ENI")
                        .millesime("2018")
                        .prix(11.45f)
                        .quantite(987)
                        .couleur(rouge)
                        .region(paysDeLaLoire)
                        .build(),
                Bouteille.builder()
                        .nom("Blanc ENI Service")
                        .millesime("2022")
                        .prix(34.00f)
                        .quantite(111)
                        .couleur(blanc)
                        .region(grandEst)
                        .build()
        );

        bouteilles.forEach(b -> {
            bouteilleRepository.save(b);
            assertThat(b.getId()).isNotNull();
            assertThat(b.getCouleur()).isNotNull();
            assertThat(b.getRegion()).isNotNull();
        });
    }

    @Test
    void test_delete() {
        Bouteille bouteille = Bouteille.builder()
                .nom("Rosé ENI")
                .millesime("2020")
                .prix(33.00f)
                .quantite(1987)
                .couleur(rose)
                .region(nouvelleAquitaine)
                .build();

        Bouteille bouteilleDB = entityManager.persist(bouteille);
        entityManager.flush();

        Integer id = bouteilleDB.getId();

        bouteilleRepository.delete(bouteilleDB);

        Bouteille bouteilleSupprimee = entityManager.find(Bouteille.class, id);
        assertNull(bouteilleSupprimee);

        assertThat(couleurRepository.findAll()).hasSize(3);
        assertThat(regionRepository.findAll()).hasSize(3);
    }
}
