package br.com.senior.city_finder.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by rsorage on 8/20/17.
 */
@Document(collection = "cities")
public class City {
    @Id
    private String id;

    @Indexed
    @Field("ibge_id")
    @JsonProperty("ibge_id")
    private Integer ibgeId;

    @Field("no_accents")
    @JsonProperty("no_accents")
    private String noAccents;

    @Field("alternative_names")
    @JsonProperty("alternative_names")
    private String alternativeNames;

    @Indexed
    private String name;

    @Indexed
    private GeoJsonPoint location;

    private String uf;
    private Boolean capital;
    private String microregion;
    private String mesoregion;

    public String getId() {
        return id;
    }

    public Integer getIbgeId() {
        return ibgeId;
    }

    public void setIbgeId(Integer ibgeId) {
        this.ibgeId = ibgeId;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public String getNoAccents() {
        return noAccents;
    }

    public void setNoAccents(String noAccents) {
        this.noAccents = noAccents;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String getMicroregion() {
        return microregion;
    }

    public void setMicroregion(String microregion) {
        this.microregion = microregion;
    }

    public String getMesoregion() {
        return mesoregion;
    }

    public void setMesoregion(String mesoregion) {
        this.mesoregion = mesoregion;
    }
}
