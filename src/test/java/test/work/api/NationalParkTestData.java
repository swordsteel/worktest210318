package test.work.api;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.http.ResponseEntity;
import test.work.api.nationalparkservice.ParkInformation;
import test.work.api.nationalparkservice.Parks;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface NationalParkTestData {

    default Optional<ParkInformation> getNationalPark(InvocationOnMock invocationOnMock) {
        return getNationalTestParksData().stream()
                .filter(park -> park.getParkCode().equals(invocationOnMock.getArgument(0)))
                .findFirst();
    }

    default ResponseEntity<Parks> makeNationalParksResponse(InvocationOnMock invocationOnMock) {
        return ResponseEntity.ok(makeNationalParkData(invocationOnMock).orElse(Parks.builder().build()));
    }

    default Optional<Parks> makeNationalParkData(InvocationOnMock invocationOnMock) {
        var matcher = Pattern.compile("start=(\\d+)&limit=(\\d+)")
                .matcher(invocationOnMock.getArgument(0));
        if (matcher.find()) {
            var parkList = getNationalTestParksData();
            var start = Integer.parseInt(matcher.group(1));
            var limit = Integer.parseInt(matcher.group(2));
            return Optional.of(Parks.builder()
                    .limit(limit)
                    .start(start)
                    .total(parkList.size())
                    .parks(parkList.stream()
                            .skip(start)
                            .limit(limit)
                            .collect(Collectors.toList()))
                    .build());
        }
        return Optional.empty();
    }

    default List<ParkInformation> getNationalTestParksData() {
        return List.of(
                ParkInformation.builder()
                        .id(UUID.fromString("048bbb5d-fa39-45e7-8845-99ec19b6a495"))
                        .parkCode("aa01")
                        .states("S1")
                        .name("aa name")
                        .designation("aa designation")
                        .description("aa description")
                        .build(),
                ParkInformation.builder()
                        .id(UUID.fromString("4c99a4af-b5e4-45c3-8b8b-4311396300c2"))
                        .parkCode("bb02")
                        .states("S2")
                        .name("bb name")
                        .designation("bb designation")
                        .description("bb description")
                        .build(),
                ParkInformation.builder()
                        .id(UUID.fromString("3b94bfe4-80ef-49eb-ba46-5582b96ebafa"))
                        .parkCode("cc03")
                        .states("S3")
                        .name("cc name")
                        .designation("cc designation")
                        .description("cc description")
                        .build());
    }

}
