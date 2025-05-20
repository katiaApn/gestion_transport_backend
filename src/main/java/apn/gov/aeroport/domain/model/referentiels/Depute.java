package apn.gov.aeroport.domain.model.referentiels;


import apn.gov.aeroport.domain.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Table(name = "depute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Depute extends User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //pour député
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_depute_id" , referencedColumnName = "id")
    private TypeDepute type_depute;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parti_politique_id" , referencedColumnName = "id")
    private PartiPolitique parti_politique;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "depute_periode_legislatif",
            joinColumns = @JoinColumn(name = "depute_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "periode_legislatif_id", referencedColumnName = "id")
    )
    private Collection<PeriodeLegislatif> periode_legislatif;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wilaya_id" , referencedColumnName = "id")
    private Wilaya wilaya;

}
