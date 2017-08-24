package br.com.senior.city_finder.controllers;

import br.com.senior.city_finder.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rsorage on 8/18/17.
 */
@RestController
public class CityController {

    private CityRepository cityRepository;

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

}
