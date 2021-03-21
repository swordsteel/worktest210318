package test.work.api.nationalparkservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import test.work.api.NationalParkTestData;
import test.work.api.exceptions.InternalServiceException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NationalParkServiceImplTest implements NationalParkTestData {

    @InjectMocks
    NationalParkServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(service, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(service, "serviceKey", "myApiKey");
        ReflectionTestUtils.setField(service, "limit", 2);
    }

    @Test
    void invalidApiCall() {
        // given
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(HttpClientErrorException.class);

        // then
        assertThrows(InternalServiceException.class, () -> service.getParks());
    }

    @Test
    void getParksDefault() {
        // given
        when(restTemplate.getForEntity(anyString(), any())).then(this::makeNationalParksResponse);

        // when
        var response = service.getParks();

        // then
        assertEquals(3, response.size());
    }

    @Test
    void getParksDataEmpty() {
        // given
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(Parks.builder()
                .limit(2)
                .total(2)
                .build()));

        // when
        var response = service.getParks();

        // then
        assertTrue(response.isEmpty());
    }

    @Test
    void getParkEmpty() {
        // given
        var parkCode = "abcd";
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(Parks.builder().build()));

        // when
        var response = service.getPark(parkCode);

        // then
        assertTrue(response.isEmpty());
    }

    @Test
    void getPark() {
        // given
        var parkCode = "abcd";
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(Parks.builder()
                .limit(2)
                .total(1)
                .parks(List.of(ParkInformation.builder().build()))
                .build()));

        // when
        var response = service.getPark(parkCode);

        // then
        assertTrue(response.isPresent());
    }






}