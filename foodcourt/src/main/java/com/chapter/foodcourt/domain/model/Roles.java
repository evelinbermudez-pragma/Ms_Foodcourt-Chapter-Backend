package com.chapter.foodcourt.domain.model;

public enum Roles {
    ADMIN_ROLE_ID(1),
    OWNER_ROLE_ID(2),
    CLIENT_ROLE_ID(3),
    EMPLOYEE_ROLE_ID(4);

    private final Integer id;

    Roles(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
