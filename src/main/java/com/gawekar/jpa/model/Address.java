package com.gawekar.jpa.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String addresLine1;
    private String addresLine2;
    private String postcode;
    
    public String getAddresLine1() {
        return addresLine1;
    }
    public void setAddresLine1(String addresLine1) {
        this.addresLine1 = addresLine1;
    }
    public String getAddresLine2() {
        return addresLine2;
    }
    public void setAddresLine2(String addresLine2) {
        this.addresLine2 = addresLine2;
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    
}
