package com.jasdeep.finalproject.Item;

public class Item {

    private String id;
    private String name;
    private Integer cost;
    private String description;
    private String imageUrl;

    private Integer quantity;

    public Item() {
        name = "[PH] Item";
        cost = 10000;
        description = "[PH] Item Description";
    }

    public Item(String id, String name, Integer cost, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description.replace("\n", "");
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
