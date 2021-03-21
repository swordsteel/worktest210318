package test.work.api.parks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import test.work.api.nationalparkservice.ParkInformation;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkServiceImplTest {

    @Mock
    private ParkRepository repository;

    @InjectMocks
    ParkServiceImpl service;

    @Test
    void getParks() {
        // given
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // when
        service.getParks();

        // then
        verify(repository).findAll();
    }

    @Test
    void getPark() {
        // given
        var parkCode = "abcd";
        when(repository.findByParkCode(parkCode)).thenReturn(Optional.empty());

        // when
        service.getPark(parkCode);

        // then
        verify(repository).findByParkCode(parkCode);
    }

    @Test
    void addPark() {
        // given
        var request = ParkAddRequest.builder()
                .parkCode("abcd")
                .states("AA,BB")
                .name("my park")
                .designation("local park")
                .description("a park of fun")
                .build();
        var uuid = UUID.fromString("8133d428-73b1-11eb-9439-0242ac130002");
        MockedStatic<UUID> mocked = mockStatic(UUID.class);
        mocked.when(UUID::randomUUID).thenReturn(uuid);
        when(repository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        var response = service.addPark(request);

        // then
        verify(repository).save(any());
        assertEquals(uuid, response.getId());
        assertEquals("abcd", response.getParkCode());
        assertEquals("AA,BB", response.getStates());
        assertEquals("my park", response.getName());
        assertEquals("local park", response.getDesignation());
        assertEquals("a park of fun", response.getDescription());
    }

    @Test
    void updatePark() {
        // given
        var parkCode = "abcd";
        var uuid = UUID.fromString("8133d428-73b1-11eb-9439-0242ac130002");
        var request = ParkUpdateRequest.builder()
                .states("AA,BB")
                .name("my park")
                .designation("local park")
                .description("a park of fun")
                .build();
        var park = Park.builder()
                .id(uuid)
                .parkCode(parkCode)
                .states("AA")
                .name("old park")
                .designation("old local park")
                .description("a old park of fun")
                .build();
        when(repository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        var response = service.updatePark(request, park);

        // then
        verify(repository).save(any());
        assertEquals(uuid, response.getId());
        assertEquals(parkCode, response.getParkCode());
        assertEquals("AA,BB", response.getStates());
        assertEquals("my park", response.getName());
        assertEquals("local park", response.getDesignation());
        assertEquals("a park of fun", response.getDescription());
    }

    @Test
    void addParkFromParkInformation() {
        // given
        var parkCode = "abcd";
        var uuid = UUID.fromString("8133d428-73b1-11eb-9439-0242ac130002");
        var request = ParkUpdateRequest.builder()
                .states("AA,BB")
                .name("my park")
                .designation("local park")
                .description("a park of fun")
                .build();
        var parkInformation = ParkInformation.builder()
                .id(uuid)
                .parkCode(parkCode)
                .states("AA")
                .name("old park")
                .designation("old local park")
                .description("a old park of fun")
                .build();
        when(repository.save(any())).then(invocationOnMock -> invocationOnMock.getArgument(0));

        // when
        var response = service.addParkFrom(request, parkInformation);

        // then
        verify(repository).save(any());
        assertEquals(uuid, response.getId());
        assertEquals(parkCode, response.getParkCode());
        assertEquals("AA,BB", response.getStates());
        assertEquals("my park", response.getName());
        assertEquals("local park", response.getDesignation());
        assertEquals("a park of fun", response.getDescription());
    }

}