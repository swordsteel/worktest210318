package test.work.api.nationalparkservice;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parks {

    private int total;
    private int limit;
    private int start;
    @JsonAlias("data")
    @Builder.Default
    private List<ParkInformation> parks = new ArrayList<>();

}
