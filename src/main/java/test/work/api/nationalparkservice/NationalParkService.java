package test.work.api.nationalparkservice;

import java.util.Optional;
import java.util.Set;

public interface NationalParkService {

    Set<ParkInformation> getParks();

    Optional<ParkInformation> getPark(String parkCode);

}
