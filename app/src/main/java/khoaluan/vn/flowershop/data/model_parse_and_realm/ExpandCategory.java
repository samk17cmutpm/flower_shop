package khoaluan.vn.flowershop.data.model_parse_and_realm;

import java.util.List;

/**
 * Created by samnguyen on 8/24/16.
 */
public class ExpandCategory {
    private String title;
    private List<Category> categories;

    public ExpandCategory(String title, List<Category> categories) {
        this.title = title;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
