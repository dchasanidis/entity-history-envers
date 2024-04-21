package com.dchasanidis.envershistory.entities.model;

public class Address {
    private String road;
    private String number;
    private String postalCode;

    public Address(final String road, final String number, final String postalCode) {
        this.road = road;
        this.number = number;
        this.postalCode = postalCode;
    }

    public Address() {
    }


    public String getRoad() {
        return road;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address setRoad(final String road) {
        this.road = road;
        return this;
    }

    public Address setNumber(final String number) {
        this.number = number;
        return this;
    }

    public Address setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }
}
