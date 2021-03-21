package test.work.api.parks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.work.api.LocalParkTestData;
import test.work.api.NationalParkTestData;
import test.work.api.exceptions.FoundException;
import test.work.api.exceptions.NotFoundException;
import test.work.api.nationalparkservice.NationalParkService;
import test.work.api.nationalparkservice.ParkInformation;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkFacadeImplTest implements NationalParkTestData, LocalParkTestData {

    @Mock
    private NationalParkService nationalParkService;
    @Mock
    private ParkService parkService;
    @InjectMocks
    ParkFacadeImpl service;

    @Test
    void getParks() {
        // given
        when(nationalParkService.getParks()).then(this::getNationalParks);
        when(parkService.getParks()).then(this::getLocalParks);

        // when
        var response = service.getParks();

        // then
        assertEquals("aa01", response.get(0).getParkCode());
        assertEquals("aa name", response.get(0).getName());
        assertEquals("bb02", response.get(1).getParkCode());
        assertEquals("bb name", response.get(1).getName());
        assertEquals("cc03", response.get(2).getParkCode());
        assertEquals("cc name (local change)", response.get(2).getName());
        assertEquals("dd04", response.get(3).getParkCode());
        assertEquals("dd name (local)", response.get(3).getName());
    }

    @Test
    void getParkNotFound() {
        // then exception
        assertThrows(NotFoundException.class, () -> service.getPark("abcd"));
    }

    @Test
    void getParkFindLocal() {
        // given
        when(parkService.getPark(anyString())).then(this::getLocalPark);

        // when
        var response = service.getPark("cc03");

        // then
        verify(parkService).getPark(anyString());
        verify(nationalParkService, never()).getPark(anyString());
        assertEquals("cc03", response.getParkCode());
        assertEquals("cc name (local change)", response.getName());
    }

    @Test
    void getParkFindNational() {
        // given
        when(nationalParkService.getPark(anyString())).then(this::getNationalPark);

        // when
        var response = service.getPark("cc03");

        // then
        verify(parkService).getPark(anyString());
        verify(nationalParkService).getPark(anyString());
        assertEquals("cc03", response.getParkCode());
        assertEquals("cc name", response.getName());
    }

    @Test
    void addParkFindLocal() {
        //given
        var request = ParkAddRequest.builder().parkCode("cc03").build();
        when(parkService.getPark(anyString())).then(this::getLocalPark);

        // then exception
        assertThrows(FoundException.class, () -> service.addParks(request));
        verify(parkService).getPark(anyString());
        verify(nationalParkService, never()).getPark(anyString());
    }

    @Test
    void addParkFindNational() {
        //given
        var request = ParkAddRequest.builder().parkCode("cc03").build();
        when(nationalParkService.getPark(anyString())).then(this::getNationalPark);

        // then exception
        assertThrows(FoundException.class, () -> service.addParks(request));
        verify(parkService).getPark(anyString());
        verify(nationalParkService).getPark(anyString());
    }

    @Test
    void addPark() {
        //given
        var uuid = UUID.fromString("8133d428-73b1-11eb-9439-0242ac130002");
        var request = ParkAddRequest.builder().parkCode("abcd").build();
        when(parkService.addPark(request)).thenReturn(Park.builder()
                .id(uuid)
                .parkCode("abcd")
                .states("AA")
                .name("a name")
                .designation("local park")
                .description("a park of fun")
                .build());

        // when
        var response = service.addParks(request);

        // then
        verify(parkService).getPark("abcd");
        verify(nationalParkService).getPark("abcd");
        verify(parkService).addPark(request);
        assertEquals("abcd", response.getParkCode());
        assertEquals("AA", response.getStates());
        assertEquals("a name", response.getName());
        assertEquals("local park", response.getDesignation());
        assertEquals("a park of fun", response.getDescription());
    }

    @Test
    void updateParkNotFound() {
        // given
        var request = ParkUpdateRequest.builder().build();

        // then exception
        assertThrows(NotFoundException.class, () -> service.updateParks("abcd", request));
    }

    @Test
    void updateParkFindLocal() {
        // given
        var request = ParkUpdateRequest.builder()
                .states("S3")
                .name("cc name (local change 2)")
                .designation("cc designation (local change 2)")
                .description("cc description (local change 2)")
                .build();


        when(parkService.getPark(anyString())).then(this::getLocalPark);
        when(parkService.updatePark(any(), any())).then(this::changeLocal);

        // when
        var response = service.updateParks("cc03", request);

        // then
        verify(parkService).getPark(anyString());
        verify(nationalParkService, never()).getPark(anyString());
        verify(parkService).updatePark(any(), any());
        verify(parkService, never()).addParkFrom(any(), any());
        assertEquals("cc03", response.getParkCode());
        assertEquals("S3", response.getStates());
        assertEquals("cc name (local change 2)", response.getName());
        assertEquals("cc designation (local change 2)", response.getDesignation());
        assertEquals("cc description (local change 2)", response.getDescription());
    }

    @Test
    void updateParkFindNational() {
        // given
        var request = ParkUpdateRequest.builder()
                .states("S3")
                .name("cc name (local change 2)")
                .designation("cc designation (local change 2)")
                .description("cc description (local change 2)")
                .build();

        when(nationalParkService.getPark(anyString())).then(this::getNationalPark);
        when(parkService.addParkFrom(any(), any())).then(this::changeNational);

        // when
        var response = service.updateParks("cc03", request);

        // then
        verify(parkService).getPark(anyString());
        verify(nationalParkService).getPark(anyString());
        verify(parkService, never()).updatePark(any(), any());
        verify(parkService).addParkFrom(any(), any());
        assertEquals("cc03", response.getParkCode());
        assertEquals("S3", response.getStates());
        assertEquals("cc name (local change 2)", response.getName());
        assertEquals("cc designation (local change 2)", response.getDesignation());
        assertEquals("cc description (local change 2)", response.getDescription());
    }

    private Park changeLocal(InvocationOnMock invocationOnMock) {
        var request = (ParkUpdateRequest) invocationOnMock.getArgument(0);
        var park = (Park) invocationOnMock.getArgument(1);
        park.setName(request.getName());
        park.setStates(request.getStates());
        park.setDesignation(request.getDesignation());
        park.setDescription(request.getDescription());
        return park;
    }

    private Park changeNational(InvocationOnMock invocationOnMock) {
        var request = (ParkUpdateRequest) invocationOnMock.getArgument(0);
        var parkInformation = (ParkInformation) invocationOnMock.getArgument(1);
        return Park.builder()
                .id(parkInformation.getId())
                .parkCode(parkInformation.getParkCode())
                .states(request.getStates())
                .name(request.getName())
                .designation(request.getDesignation())
                .description(request.getDescription())
                .build();
    }

    private Set<ParkInformation> getNationalParks(InvocationOnMock invocationOnMock) {
        return new HashSet<>(getNationalTestParksData());
    }

}