package fr.eni.cave.bo.client;


import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "CAV_SHOPPING_CART")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPPING_CART_ID")
    private Integer id;

    @Column(name = "ORDER_NUMBER", length = 200)
    private String numCommande;

    @Column(name = "TOTAL_PRICE", precision = 2)
    private float prixTotal;

    @Column(name = "PAID")
    private boolean paye;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "SHOPPING_CART_ID")
    private @Builder.Default List<LignePanier> lignesPanier = new ArrayList<>();
}
