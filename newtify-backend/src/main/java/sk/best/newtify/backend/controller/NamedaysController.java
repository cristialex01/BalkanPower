package sk.best.newtify.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import sk.best.newtify.backend.service.NamedayService;
import sk.best.newtify.api.NamedaysApi;
import sk.best.newtify.api.dto.NameDayDTO;

/**
 * @author Stanislav Blasko
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Controller
public class NamedaysController implements NamedaysApi {

    @Autowired
    private NamedayService namedayService;

    @Override
    public ResponseEntity<NameDayDTO> retrieveNameDay(Integer month, Integer day) {
        NameDayDTO response = namedayService.retrieveNameDay(month, day);
        return ResponseEntity.ok(response);
    }
}
