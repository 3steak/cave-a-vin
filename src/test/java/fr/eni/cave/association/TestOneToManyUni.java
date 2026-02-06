package fr.eni.cave.association;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import fr.eni.cave.dal.PanierRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestOneToManyUni {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PanierRepository panierRepository;

    /**
     * crée un panier avec une ligne déjà persistée
     */
    private Panier panierEnDB() {
        Panier panier = new Panier();

        LignePanier lp = LignePanier.builder()
                .quantite(3)
                .prix(3 * 11.45f)
                .build();

        panier.getLignesPanier().add(lp);
        panier.setPrixTotal(lp.getPrix());

        entityManager.persist(panier);
        entityManager.flush();

        return panier;
    }


    @Test
    void test_save_nouvelleLigne_nouveauPanier() {

        LignePanier lp = LignePanier.builder()
                .quantite(2)
                .prix(2 * 9.99f)
                .build();

        Panier panier = Panier.builder()
                .numCommande("CMD001")
                .prixTotal(lp.getPrix())
                .paye(false)
                .build();

        panier.getLignesPanier().add(lp);

        Panier panierDB = panierRepository.save(panier);
        entityManager.flush();
        entityManager.clear();

        Panier panierFromDb = panierRepository.findById(panierDB.getId()).orElse(null);

        assertThat(panierFromDb).isNotNull();
        assertThat(panierFromDb.getLignesPanier()).hasSize(1);

        log.info("Panier créé avec ligne : {}", panierFromDb);
    }


    @Test
    void test_save_nouvelleLigne_Panier() {

        Panier panier = panierEnDB();

        LignePanier nouvelleLigne = LignePanier.builder()
                .quantite(1)
                .prix(12.50f)
                .build();

        panier.getLignesPanier().add(nouvelleLigne);
        panier.setPrixTotal(panier.getPrixTotal() + nouvelleLigne.getPrix());

        panierRepository.save(panier);
        entityManager.flush();
        entityManager.clear();

        Panier panierFromDb = panierRepository.findById(panier.getId()).orElse(null);

        assertThat(panierFromDb.getLignesPanier()).hasSize(2);

        log.info("Panier après ajout ligne : {}", panierFromDb);
    }

 
    @Test
    void test_delete_panier() {

        Panier panier = panierEnDB();
        Integer panierId = panier.getId();
        Integer ligneId = panier.getLignesPanier().get(0).getId();

        panierRepository.delete(panier);
        entityManager.flush();
        entityManager.clear();

        Panier panierFromDb = panierRepository.findById(panierId).orElse(null);
        LignePanier ligneFromDb = entityManager.find(LignePanier.class, ligneId);

        assertThat(panierFromDb).isNull();
        assertThat(ligneFromDb).isNull();

        log.info("Suppression panier + lignes OK");
    }


    @Test
    void test_orphanRemoval() {

        Panier panier = panierEnDB();

        List<LignePanier> lignes = panier.getLignesPanier();
        LignePanier ligneSupprimee = lignes.get(0);
        Integer ligneId = ligneSupprimee.getId();

        lignes.remove(ligneSupprimee);

        panierRepository.save(panier);
        entityManager.flush();
        entityManager.clear();

        LignePanier ligneFromDb = entityManager.find(LignePanier.class, ligneId);

        assertThat(ligneFromDb).isNull();

        log.info("Orphan removal OK");
    }
}
