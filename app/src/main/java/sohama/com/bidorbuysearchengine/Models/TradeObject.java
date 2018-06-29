package sohama.com.bidorbuysearchengine.Models;

import java.util.List;

public class TradeObject {

    private List<ImageObject> images;
    private float amount;
    private String title;
    private String type;
    private long userId;
    private boolean hotSelling;
    private String categoryBreadCrumb;
    private String userAlias;
    private String closeTime;
    private long homeCategoryId;
    private String location;
    private String openTime;
    private long tradeId;
    private String status;

    public List<ImageObject> getImages() {
        return images;
    }

    public void setImages(List<ImageObject> images) {
        this.images = images;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isHotSelling() {
        return hotSelling;
    }

    public void setHotSelling(boolean hotSelling) {
        this.hotSelling = hotSelling;
    }

    public String getCategoryBreadCrumb() {
        return categoryBreadCrumb;
    }

    public void setCategoryBreadCrumb(String categoryBreadCrumb) {
        this.categoryBreadCrumb = categoryBreadCrumb;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public long getHomeCategoryId() {
        return homeCategoryId;
    }

    public void setHomeCategoryId(long homeCategoryId) {
        this.homeCategoryId = homeCategoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
