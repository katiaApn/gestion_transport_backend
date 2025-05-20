package apn.gov.aeroport.domain.model.referentiels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "periode_legislatif")
public class PeriodeLegislatif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libeller_fr")
    private String libeller_fr;

    @Column(name = "libeller_ar")
    private String libeller_ar;

    @Column(name = "libeller_en")
    private String libeller_en;

    @Column(name = "numero")
    private String numero ;

    // periode !!!!!!!!

    @Column(name = "valid")
    private boolean valid ;
}
