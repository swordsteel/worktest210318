package test.work.api.parks;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "parkCode")
public class ParkResponse {

    private final String parkCode;

    private final String states;

    private final String name;

    private final String designation;

    private final String description;

}
