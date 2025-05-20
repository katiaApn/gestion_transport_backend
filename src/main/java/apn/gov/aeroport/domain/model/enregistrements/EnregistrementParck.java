package apn.gov.aeroport.domain.model.enregistrements;

import apn.gov.aeroport.domain.model.referentiels.Wilaya;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "enregistrement_parck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EnregistrementParck extends EnregistrementTransport{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "adresse_destination")
    private String adresse_destination ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wilaya_id" , referencedColumnName = "id")
    private Wilaya wilaya;

}
