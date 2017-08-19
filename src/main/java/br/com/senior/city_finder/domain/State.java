package br.com.senior.city_finder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsorage on 8/18/17.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    private Set<City> cities = new HashSet<>();


    public State() { }

    public State(Set<City> cities) {
        this.cities = cities;
    }


    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public void addCity(City city) {
        this.cities.add(city);
        city.setState(this);
    }

    public void removeCity(City city) {
        city.setState(null);
        this.cities.remove(city);
    }

}
