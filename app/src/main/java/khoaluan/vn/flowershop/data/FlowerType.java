package khoaluan.vn.flowershop.data;

/**
 * Created by samnguyen on 7/21/16.
 */
public class FlowerType {
    private int id;
    private String name;

    public FlowerType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
