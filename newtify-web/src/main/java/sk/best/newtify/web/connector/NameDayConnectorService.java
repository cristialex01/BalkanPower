package sk.best.newtify.web.connector;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.best.newtify.api.NamedaysApi;
import sk.best.newtify.api.dto.NameDayDTO;

/**
 * @author Marek Urban
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Slf4j
@Service
public class NameDayConnectorService implements NamedaysApi {

    private static final String NAMEDAY_API_URL = "http://localhost:8081/v1/namedays?month={month}&day={day}";

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public ResponseEntity<NameDayDTO> retrieveNameDay(@NonNull Integer month,
                                                      @NonNull Integer day) {
        try {
            return restTemplate.getForEntity(NAMEDAY_API_URL, NameDayDTO.class, month, day);
        } catch (Exception e) {
            log.error("ERROR retrieveNameDay", e);
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
    
}
