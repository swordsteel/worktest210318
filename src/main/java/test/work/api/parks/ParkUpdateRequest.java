package test.work.api.parks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkUpdateRequest {

    @NotBlank
    private String states;

    @NotBlank
    private String name;

    @NotBlank
    private String designation;

    @NotBlank
    private String description;

}
