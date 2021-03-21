package test.work.api.parks;

import java.util.List;

public interface ParkFacade {

    List<ParkResponse> getParks();

    ParkResponse getPark(String stateCode);

    ParkResponse addParks(ParkAddRequest request);

    ParkResponse updateParks(String stateCode, ParkUpdateRequest request);

}
