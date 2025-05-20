package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnregistrementAeroportGroupesDto {
    List<EnregistrementAeroport> enregistrementAeroportsDepart;
    List<EnregistrementAeroport> enregistrementAeroportsArrival;
}
