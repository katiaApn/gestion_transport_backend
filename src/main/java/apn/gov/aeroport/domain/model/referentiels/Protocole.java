package apn.gov.aeroport.domain.model.referentiels;


import apn.gov.aeroport.domain.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "protocole")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Protocole extends User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // pour protocole
    @Column(name = "fonction")
    private String fonction;

    @Column(name = "departement")
    private String departement;


}
