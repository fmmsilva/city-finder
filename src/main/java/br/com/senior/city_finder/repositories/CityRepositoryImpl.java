package br.com.senior.city_finder.repositories;

import br.com.senior.city_finder.domain.CityCount;
import br.com.senior.city_finder.domain.ColumnCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class CityRepositoryImpl implements CityRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final String collection = "cities";

    @Autowired
    public CityRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<CityCount> getCityCount() {
        GroupOperation groupOperation = group("uf")
                .count().as("total");

        SortOperation sortOperation = sort(Sort.Direction.ASC, "_id");

        AggregationResults<CityCount> results = mongoTemplate.aggregate(
                Aggregation.newAggregation(groupOperation, sortOperation), collection, CityCount.class);

        return results.getMappedResults();
    }

    @Override
    public ColumnCount countByColumn(String column) {
        int total = mongoTemplate.getCollection(collection).distinct(column).size();
        return new ColumnCount(column, total);
    }

}
