package br.com.senior.city_finder.config;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.net.UnknownHostException;

/**
 * Created by rsorage on 8/18/17.
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    private String uri = String.format("mongodb://%s:%s/%s", host, port, database);


    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws UnknownHostException {
        return new Mongo(host, port);
    }
}
