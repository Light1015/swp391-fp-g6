package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class BookSeries {
    private int series_id;
    private String name;
    private String description;

    public BookSeries() {
    }

    public BookSeries(int series_id, String name, String description) {
        this.series_id = series_id;
        this.name = name;
        this.description = description;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
