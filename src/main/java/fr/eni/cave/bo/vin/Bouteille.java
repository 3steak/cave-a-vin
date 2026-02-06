package fr.eni.cave.bo.vin;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "CAV_BOTTLE")
public class Bouteille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOTTLE_ID")
    private Integer id;

    @Column(name = "NAME", length = 250, unique = true, nullable = false)
    private String nom;

    @Column(name = "SPARKLING")
    private boolean petillant;

    @Column(name = "VINTAGE", length = 100)
    private String millesime;

    @Column(name = "QUANTITY")
    private int quantite;

    @Column(name = "PRICE", precision = 2)
    private float prix;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "COLOR_ID")
    private Couleur couleur;
}
