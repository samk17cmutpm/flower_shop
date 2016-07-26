package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 7/21/16.
 */
public class Category extends RealmObject {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Description")
    private String description;

    @SerializedName("Type")
    private String type;

    public Category(String id, String name, String description, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
