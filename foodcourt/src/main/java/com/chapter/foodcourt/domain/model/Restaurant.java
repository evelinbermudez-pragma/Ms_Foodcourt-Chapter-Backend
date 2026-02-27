package com.chapter.foodcourt.domain.model;

public class Restaurant {
    private Integer id;
    private String name;
    private String nit;
    private String address;
    private String phone;
    private String urlLogo;
    private Integer ownerId;

    public Restaurant(){

    }

    public Restaurant(Integer id, String name, String address, String nit, String phone, String urlLogo, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nit = nit;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
