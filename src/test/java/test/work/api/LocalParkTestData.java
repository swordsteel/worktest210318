package test.work.api;

import org.mockito.invocation.InvocationOnMock;
import test.work.api.parks.Park;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalParkTestData {

    default Optional<Park> getLocalPark(InvocationOnMock invocationOnMock) {
        return getLocalTestParksData().stream()
                .filter(park -> park.getParkCode().equals(invocationOnMock.getArgument(0)))
                .findFirst();
    }

    default List<Park> getLocalParks(InvocationOnMock invocationOnMock) {
        return getLocalTestParksData();
    }

    default List<Park> getLocalTestParksData() {
        return List.of(
                Park.builder()
                        .id(UUID.fromString("3b94bfe4-80ef-49eb-ba46-5582b96ebafa"))
                        .parkCode("cc03")
                        .states("S3")
                        .name("cc name (local change)")
                        .designation("cc designation (local change)")
                        .description("cc description (local change)")
                        .build(),
                Park.builder()
                        .id(UUID.fromString("b8f58479-b9e0-496d-a325-63f9b720fb6a"))
                        .parkCode("dd04")
                        .states("S4")
                        .name("dd name (local)")
                        .designation("dd designation (local)")
                        .description("dd description (local)")
                        .build());
    }

}
