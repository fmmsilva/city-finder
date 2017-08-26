package br.com.senior.city_finder.repositories;

import br.com.senior.city_finder.domain.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rsorage on 8/20/17.
 */
@Repository
public interface CityRepository extends MongoRepository<City, String> {

    List<City> findByName(@Param("name") String name);
    List<City> findByUf(@Param("uf") String uf);
    City findByIbgeId(@Param("ibge-id") Integer ibgeId);

    int countByUf(@Param("uf") String uf);

}
