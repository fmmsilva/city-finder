package br.com.senior.city_finder.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO reprensentando o estado e o total de cidades que este possui.
 */
public class CityCount {

    @JsonProperty("uf")
    private String _id;
    private int total;

    public CityCount(String _id, int total) {
        this._id = _id;
        this.total = total;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
