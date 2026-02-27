package com.chapter.foodcourt.insfrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "restaurant")
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(unique = true)
    private String nit;
    private String address;
    @Column(unique = true)
    private String phone;
    private String urlLogo;
    private Integer ownerId;
}
