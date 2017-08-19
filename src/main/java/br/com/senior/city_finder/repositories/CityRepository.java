package br.com.senior.city_finder.repositories;

import br.com.senior.city_finder.domain.City;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rsorage on 8/18/17.
 */
@RepositoryRestResource(collectionResourceRel = "city", path = "cities")
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {
    List<City> findByName(@Param("name") String name);
}
