package khoaluan.vn.flowershop.data;

/**
 * Created by samnguyen on 7/23/16.
 */
public class InfoType {
    private int id;
    private String name;

    public InfoType(int id, String name) {
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
