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
@Table(name = "compagnie")
public class Compagnie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "designation_fr")
    private String designation_fr;

    @Column(name = "designation_ar")
    private String designation_ar;

    @Column(name = "designation_en")
    private String designation_en;

    @Column(name = "valid")
    private boolean valid; // 0 : invalide ; 1:valide



}