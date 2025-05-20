package apn.gov.aeroport.domain.model.enregistrements;

import apn.gov.aeroport.domain.enums.TypeTicket;
import apn.gov.aeroport.domain.enums.TypeVol;
import apn.gov.aeroport.domain.enums.TypeVoyage;
import apn.gov.aeroport.domain.model.referentiels.Aeroport;
import apn.gov.aeroport.domain.model.referentiels.Compagnie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "Enregistrement_aeroport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EnregistrementAeroport  extends EnregistrementTransport{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_vol_arrivee")
    private String numero_vol_arrivee;

    @Column(name = "numero_vol_depart")
    private String numero_vol_depart;

    @ManyToOne()
    @JoinColumn(name = "compagnie_depart_id" , referencedColumnName = "id")
    private Compagnie compagnie_depart;

    @ManyToOne()
    @JoinColumn(name = "compagnie_arrivee_id" , referencedColumnName = "id")
    private Compagnie compagnie_arrivee;

    @ManyToOne()
    @JoinColumn(name = "aeroport_depart_id" , referencedColumnName = "id") // destination
    private Aeroport aeroport_depart;

    @ManyToOne()
    @JoinColumn(name = "aeroport_arrivee_id" , referencedColumnName = "id")// provenance
    private Aeroport aeroport_arrivee;

    @Column(name = "type_vol_depart")
    @Enumerated(value = EnumType.STRING)
    private TypeVol type_vol_depart;

    @Column(name = "type_vol_arrivee")
    @Enumerated(value = EnumType.STRING)
    private TypeVol type_vol_arrivee;

    @Column(name = "type_voyage")
    @Enumerated(value = EnumType.STRING)
    private TypeVoyage type_voyage;

    @Column(name = "type_ticket")
    @Enumerated(value = EnumType.STRING)
    private TypeTicket type_ticket;


}
