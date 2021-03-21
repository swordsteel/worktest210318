package test.work.api.parks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.work.api.nationalparkservice.ParkInformation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkServiceImpl implements ParkService {

    private final ParkRepository repository;

    @Override
    public List<Park> getParks() {
        return repository.findAll();
    }

    @Override
    public Optional<Park> getPark(String parkCode) {
        return repository.findByParkCode(parkCode);
    }

    @Override
    public Park addPark(ParkAddRequest parkAddRequest) {
        return repository.save(Park.builder()
                .id(UUID.randomUUID())
                .parkCode(parkAddRequest.getParkCode())
                .states(parkAddRequest.getStates())
                .name(parkAddRequest.getName())
                .designation(parkAddRequest.getDesignation())
                .description(parkAddRequest.getDescription())
                .build());
    }

    @Override
    public Park updatePark(ParkUpdateRequest request, Park park) {
        park.setName(request.getName());
        park.setStates(request.getStates());
        park.setDesignation(request.getDesignation());
        park.setDescription(request.getDescription());
        return repository.save(park);
    }

    @Override
    public Park addParkFrom(ParkUpdateRequest request, ParkInformation parkInformation) {
        return repository.save(Park.builder()
                .id(parkInformation.getId())
                .parkCode(parkInformation.getParkCode())
                .states(request.getStates())
                .name(request.getName())
                .designation(request.getDesignation())
                .description(request.getDescription())
                .build());
    }

}
