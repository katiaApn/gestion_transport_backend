package apn.gov.aeroport.domain.model.users;

import apn.gov.aeroport.domain.enums.Roles;
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
@Table(name = "roles")
//@SequenceGenerator(name = "SEQ_AEROPORT_ROLS", sequenceName = "SEQ_AEROPORT_ROLS", allocationSize = 1, initialValue = 1)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    private Roles name;

    public Role(Roles roleName) {
        this.name = roleName;
    }

}
