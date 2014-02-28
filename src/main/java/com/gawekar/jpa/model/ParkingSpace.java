package com.gawekar.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpace {
    @Id
    int id;
    int vehicleId;
    @OneToOne(mappedBy="parkingSpace")//note mappedBy is not the column id its the java property name. 
    private Employee employee;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
