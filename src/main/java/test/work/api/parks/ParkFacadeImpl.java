package test.work.api.parks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.work.api.exceptions.FoundException;
import test.work.api.exceptions.NotFoundException;
import test.work.api.nationalparkservice.NationalParkService;
import test.work.api.nationalparkservice.ParkInformation;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParkFacadeImpl implements ParkFacade {

    private final NationalParkService nationalParkService;
    private final ParkService parkService;

    @Override
    public List<ParkResponse> getParks() {
        return Stream.concat(
                parkService.getParks().stream(),
                nationalParkService.getParks().stream())
                .map(this::mapToParkResponse)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(ParkResponse::getParkCode))
                .collect(Collectors.toList());
    }

    @Override
    public ParkResponse getPark(String parkCode) {
        var park = parkService.getPark(parkCode);
        if (park.isPresent()) {
            return mapParkToParkResponse(park.get());
        }
        var parkInformation = nationalParkService.getPark(parkCode);
        if (parkInformation.isPresent()) {
            return mapParkInformationToParkResponse(parkInformation.get());
        }
        throw new NotFoundException("Cant find park with park code " + parkCode);
    }

    @Override
    public ParkResponse addParks(ParkAddRequest request) {
        if (isPark(request.getParkCode())) {
            throw new FoundException("park with code '" + request.getParkCode() + "' exists");
        }
        return mapParkToParkResponse(parkService.addPark(request));
    }

    @Override
    public ParkResponse updateParks(String parkCode, ParkUpdateRequest request) {
        var park = parkService.getPark(parkCode);
        if (park.isPresent()) {
            return mapParkToParkResponse(parkService.updatePark(request, park.get()));
        }
        var parkInformation = nationalParkService.getPark(parkCode);
        if (parkInformation.isPresent()) {
            return mapParkToParkResponse(parkService.addParkFrom(request, parkInformation.get()));
        }
        throw new NotFoundException("Cant find park with park code " + parkCode);
    }

    private boolean isPark(String parkCode) {
        return parkService.getPark(parkCode).isPresent() || nationalParkService.getPark(parkCode).isPresent();
    }

    private ParkResponse mapToParkResponse(Object o) {
        if (o instanceof Park) {
            var park = (Park) o;
            return ParkResponse.builder()
                    .parkCode(park.getParkCode())
                    .name(park.getName())
                    .states(park.getStates())
                    .build();
        } else if (o instanceof ParkInformation) {
            var parkInformation = (ParkInformation) o;
            return ParkResponse.builder()
                    .parkCode(parkInformation.getParkCode())
                    .name(parkInformation.getName())
                    .states(parkInformation.getStates())
                    .build();
        }
        return null;
    }

    private ParkResponse mapParkToParkResponse(Park park) {
        return ParkResponse.builder()
                .parkCode(park.getParkCode())
                .name(park.getName())
                .states(park.getStates())
                .designation(park.getDesignation())
                .description(park.getDescription())
                .build();
    }

    private ParkResponse mapParkInformationToParkResponse(ParkInformation parkInformation) {
        return ParkResponse.builder()
                .parkCode(parkInformation.getParkCode())
                .name(parkInformation.getName())
                .states(parkInformation.getStates())
                .designation(parkInformation.getDesignation())
                .description(parkInformation.getDescription())
                .build();
    }

}
