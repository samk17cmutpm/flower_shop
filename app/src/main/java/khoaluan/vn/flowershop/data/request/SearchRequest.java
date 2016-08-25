package khoaluan.vn.flowershop.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samnguyen on 8/25/16.
 */
public class SearchRequest {

    @SerializedName("content")
    private String content;

    @SerializedName("currentpage")
    private int currentPage;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("pricefrom")
    private int pricefrom;

    @SerializedName("priceto")
    private int priceto;

    @SerializedName("categoryIds")
    private String categoryIds;

    public SearchRequest(String content, int currentPage, int pageSize, int pricefrom, int priceto, String categoryIds) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pricefrom = pricefrom;
        this.priceto = priceto;
        this.categoryIds = categoryIds;
    }

    public SearchRequest(String content) {
        this.content = content;
    }

    public SearchRequest(String content, int currentPage, int pageSize) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public SearchRequest(String content, int currentPage, int pageSize, int pricefrom, int priceto) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pricefrom = pricefrom;
        this.priceto = priceto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPricefrom() {
        return pricefrom;
    }

    public void setPricefrom(int pricefrom) {
        this.pricefrom = pricefrom;
    }

    public int getPriceto() {
        return priceto;
    }

    public void setPriceto(int priceto) {
        this.priceto = priceto;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}
