package br.com.senior.city_finder.controller;

import br.com.senior.city_finder.domain.City;
import br.com.senior.city_finder.domain.CityCount;
import br.com.senior.city_finder.domain.ColumnCount;
import br.com.senior.city_finder.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private CityRepository repository;

    @Autowired
    public void setRepository(CityRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/countAllCities")
    public Long countAllCities() {
        return repository.countAllCities();
    }

    @RequestMapping("/countCitiesPerState")
    public List<CityCount> getCityCount() {
        return repository.getCityCount();
    }

    @RequestMapping("/countByColumn")
    public ColumnCount countByColumn(@Param("col") String col) {
        return repository.countByColumn(col);
    }

    @RequestMapping("/searchByColumn")
    public List<City> searchByColumn(@Param("col") String col, @Param("query") String query) {
        return repository.searchByColumn(col, query);
    }

    @RequestMapping("/statesMaxMinCities")
    public Map<String, CityCount> statesMaxMinCities() {
        List<CityCount> countAllStates = repository.getCityCount();

        int first = 0;
        int last = countAllStates.size() - 1;

        Map<String, CityCount> statesMaxMin = new HashMap<>(2);
        statesMaxMin.put("min", countAllStates.get(first));
        statesMaxMin.put("max", countAllStates.get(last));

        return statesMaxMin;
    }

    @RequestMapping("/mostDistant")
    public List<City> mostDistant() {
        List<City> citiesCoords = repository.citiesCoords();

        double maxDistance = 0;
        City a = null;
        City b = null;

        for(int i = 0; i < citiesCoords.size(); i++) {
            for(int j = i + 1; j < citiesCoords.size(); j++) {
                double x1 = citiesCoords.get(i).getLocation().getX();
                double x2 = citiesCoords.get(j).getLocation().getX();

                double y1 = citiesCoords.get(i).getLocation().getY();
                double y2 = citiesCoords.get(j).getLocation().getY();

                double distance = Math.hypot(x1-x2, y1-y2);

                if(distance > maxDistance) {
                    maxDistance = distance;
                    a = citiesCoords.get(i);
                    b = citiesCoords.get(j);
                }
            }
        }

        a = repository.findByIbgeId(a.getIbgeId());
        b = repository.findByIbgeId(b.getIbgeId());

        List<City> mostDistant = new ArrayList<>(2);
        mostDistant.add(a);
        mostDistant.add(b);

        return mostDistant;
    }

}
