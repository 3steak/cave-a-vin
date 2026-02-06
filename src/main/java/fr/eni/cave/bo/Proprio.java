package fr.eni.cave.bo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// Pour afficher les champs du parents
@ToString(callSuper = true) 

//Pour gérer les champs du parent
@SuperBuilder
@Entity
@Table(name = "CAV_OWNER")
public class Proprio extends Utilisateur {

    @Column(name = "CLIENT_NUMBER", length = 14)
    private String siret;
}
