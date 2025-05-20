package apn.gov.aeroport.domain.model.enregistrements;

import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.domain.enums.TypeMotif;
import apn.gov.aeroport.domain.model.AbstractEntity;
import apn.gov.aeroport.domain.model.referentiels.Depute;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "enregistrement_transport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // ou SINGLE_TABLE
public class EnregistrementTransport extends AbstractEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private MoyenTransport moyen_transport;

//    @Column(name = "type_voyage")
//    @Enumerated(value = EnumType.STRING)
//    private TypeVoyage type_voyage;

    @Column(name = "motif_voyage")
    private String motif_voyage;

    @Column(name = "type_motif")
    @Enumerated(value = EnumType.STRING)
    private TypeMotif type_motif;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date_arrivee;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date_depart;

    @Column(name = "nbr_passagers")
    private Integer nbr_passagers;

    // voir anomalie un depute peut ne pas avoir de compte !!!!!!!!!!!
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "enregistrement_transport_depute",
            joinColumns = @JoinColumn(name = "enregistrement_transport_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "depute_id", referencedColumnName = "id")
    )
    private Collection<Depute> deputes;

    @Column(name = "supprim")
    private boolean supprim = false;

}
