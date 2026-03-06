package com.chapter.foodcourt.domain.model;

public class User {
    private Integer id;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private Integer roleId;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public Integer getRoleId() {return roleId;}
    public void setRoleId(Integer roleId) {this.roleId = roleId;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}


}
