package test.work.api.nationalparkservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import test.work.api.exceptions.InternalServiceException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class NationalParkServiceImpl implements NationalParkService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${national.park.service.key}")
    private String serviceKey;

    @Value("${national.park.service.limit}")
    private int limit;

    @Override
    public Set<ParkInformation> getParks() {
        var total = 0;
        var parks = new HashSet<ParkInformation>();
        do {
            Parks tmp = getParksResponseEntity(makeUrl("start=" + parks.size() + "&limit=" + limit));
            if(tmp.getParks().isEmpty()) {
                log.warn("getting data from national park service had hiccup");
                break;
            }
            total = tmp.getTotal();
            parks.addAll(tmp.getParks());
        } while (parks.size() < total);
        return parks;
    }

    @Override
    public Optional<ParkInformation> getPark(String parkCode) {
        var parks = getParksResponseEntity(makeUrl("parkCode=" + parkCode)).getParks();
        if(parks.size() > 0) {
            return Optional.of(parks.get(0));
        }
        return Optional.empty();
    }

    private Parks getParksResponseEntity(String url) {
        try {
            ResponseEntity<Parks> response = restTemplate.getForEntity(url, Parks.class);
            log.info("api call executed: {}", url);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
        }
        throw new InternalServiceException("can't get data from national park service");
    }

    private String makeUrl(String param) {
        return "https://developer.nps.gov/api/v1/parks?api_key=" + serviceKey + "&" + param;
    }

}
