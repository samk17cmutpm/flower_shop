package khoaluan.vn.flowershop.data.model_parse_and_realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by samnguyen on 9/19/16.
 */
public class Notifycation extends RealmObject{

    @SerializedName("Id")
    private String Id;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("Subject")
    private String Subject;

    @SerializedName("Content")
    private String Content;

    @SerializedName("CreatedOnUtc")
    private long CreatedOnUtc;

    public Notifycation(String id, String userId, String subject, String content, long createdOnUtc) {
        Id = id;
        UserId = userId;
        Subject = subject;
        Content = content;
        CreatedOnUtc = createdOnUtc;
    }

    public Notifycation() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getCreatedOnUtc() {
        return CreatedOnUtc;
    }

    public void setCreatedOnUtc(long createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }
}
