package br.com.senior.city_finder.repositories;

import br.com.senior.city_finder.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface CityRepository extends MongoRepository<City, String>, CityRepositoryCustom {

    List<City> findByName(@Param("name") String name);
    List<City> findByCapitalIsTrue(Sort sort);
    Page<City> findByUf(@Param("uf") String uf, Pageable pageable);
    City findByIbgeId(@Param("ibge-id") Integer ibgeId);

    int countByUf(@Param("uf") String uf);

    @Query(value = "{?0: ?1}")
    List<City> searchByColumn(String column, String query);

    @Query(value = "{ }", count = true)
    Long countAllCities();

    @Query(value = "{ }", fields = "{ _id: 0, ibge_id: 1, location: 1 }")
    List<City> citiesCoords();

}
