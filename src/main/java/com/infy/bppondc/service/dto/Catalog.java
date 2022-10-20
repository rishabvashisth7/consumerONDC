package com.infy.bppondc.service.dto;

import java.util.List;

public class Catalog {

    private String id;
    private List<Item> items;

    @Override
    public String toString() {
        return "Catalog{" + "id='" + id + '\'' + ", items=" + items + '}';
    }

    public Catalog() {}

    public Catalog(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
