package br.com.senior.city_finder.domain;

/**
 * DTO representando a coluna escolhida e o total de registros distintos encontrados.
 */
public class ColumnCount {

    private String column;
    private int total;

    public ColumnCount(String column, int total) {
        this.column = column;
        this.total = total;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
