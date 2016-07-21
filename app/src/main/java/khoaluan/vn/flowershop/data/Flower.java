package khoaluan.vn.flowershop.data;

/**
 * Created by samnguyen on 7/19/16.
 */
public class Flower {
    private String content;

    private String urlImage;

    public Flower(String content, String urlImage) {
        this.content = content;
        this.urlImage = urlImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
