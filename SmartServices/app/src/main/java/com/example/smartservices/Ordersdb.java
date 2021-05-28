package com.example.smartservices;

public class Ordersdb {
    private String items;
    private String quantity;
    public Ordersdb(String items, String orderno, String email)
    {
        this.email=email;
        this.items=items;
        this.orderno=orderno;
    }
    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    private String orderno;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    public Ordersdb()
    {

    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
