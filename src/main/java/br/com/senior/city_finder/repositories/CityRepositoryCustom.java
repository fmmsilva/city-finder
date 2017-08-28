package br.com.senior.city_finder.repositories;

import br.com.senior.city_finder.domain.CityCount;
import br.com.senior.city_finder.domain.ColumnCount;

import java.util.List;

/**
 * Created by rsorage on 8/26/17.
 */
public interface CityRepositoryCustom {

    List<CityCount> getCityCount();

    ColumnCount countByColumn(String col);

}
