package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/12/16.
 */
public class City extends RealmObject{

    @SerializedName("Id")
    private String Id;

    @SerializedName("Name")
    private String Name;

    private String flag;

    public City() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
