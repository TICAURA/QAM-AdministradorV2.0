package com.aura.admin.adminqamm.util;

public enum PromoENUM {

    MONTO(0),
    PORCENTAJE(1);


    private int id;


    private PromoENUM(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}