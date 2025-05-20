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
@Table(name = "type_depute")
public class TypeDepute {

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

    @Column(name = "valid")
    private boolean valid; // 0 : invalide ; 1:valide

}
