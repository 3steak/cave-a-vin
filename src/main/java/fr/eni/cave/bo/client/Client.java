package fr.eni.cave.bo.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CAV_CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "pseudo")
@ToString(exclude = "password")
public class Client {

    @Id
    @Column(name = "LOGIN", nullable = false, length = 255)
    private String pseudo;

    @Column(name = "PASSWORD", nullable = false, length = 68)
    private String password;

    @Column(name = "LAST_NAME", nullable = false, length = 90)
    private String nom;

    @Column(name = "FIRST_NAME", nullable = false, length = 150)
    private String prenom;
}
