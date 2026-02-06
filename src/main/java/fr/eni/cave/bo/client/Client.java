package fr.eni.cave.bo.client;

import fr.eni.cave.bo.Utilisateur;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// Pour gérer les champs du parent
@SuperBuilder
@Entity
@Table(name = "CAV_CLIENT")
public class Client extends Utilisateur {

    // fetch EAGER : si on charge un Client, on charge son Adresse
    @OneToOne(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "ADDRESS_ID")
    private Adresse adresse;
}
