package test.work.api.parks;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "park")
public class Park {

    @Id
    private UUID id;

    private String parkCode;

    private String states;

    private String name;

    private String designation;

    private String description;

}
