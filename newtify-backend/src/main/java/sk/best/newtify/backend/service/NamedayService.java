package sk.best.newtify.backend.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sk.best.newtify.api.dto.NameDayDTO;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Stanislav Blasko
 * Copyright © 2022 BEST Technická univerzita Košice.
 * All rights reserved.
 */
@Service
public class NamedayService {

    @Value("classpath:nameday.json")
    Resource resourceFile;

    private static JSONObject jsonObject;

    public NameDayDTO retrieveNameDay(Integer month, Integer day) {
        String name;

        // read JSON file and map/convert to java POJO
        try {
            if (jsonObject == null) {
                jsonObject = parse();
            }

            // retrieve name from json object
            String actualMonth = Integer.toString(month - 1);
            JSONObject nameDayMonthList = (JSONObject) jsonObject.get(actualMonth);
            name = (String) nameDayMonthList.get(Integer.toString(day));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        NameDayDTO nameDayDTO = new NameDayDTO();
        nameDayDTO.setMonth(month);
        nameDayDTO.setDay(day);
        nameDayDTO.setName(name);
        return nameDayDTO;
    }

    private JSONObject parse() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new FileReader(resourceFile.getFile()));
    }

}
