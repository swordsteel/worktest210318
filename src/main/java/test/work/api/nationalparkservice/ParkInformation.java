package test.work.api.nationalparkservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkInformation {

    private UUID id;

    private String parkCode;

    private String states;

    private String name;

    private String designation;

    private String description;

}
