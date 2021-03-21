package test.work.api.parks;

import test.work.api.nationalparkservice.ParkInformation;

import java.util.List;
import java.util.Optional;

public interface ParkService {

    List<Park> getParks();

    Optional<Park> getPark(String parkCode);

    Park addPark(ParkAddRequest parkAddRequest);

    Park updatePark(ParkUpdateRequest request, Park park);

    Park addParkFrom(ParkUpdateRequest request, ParkInformation parkInformation);

}
