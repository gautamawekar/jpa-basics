package com.gawekar.jpa.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Employee {
    @Id
    int id;
    private String name; 
    @Embedded private Address address;
    
    @ManyToOne //many-to-one side should be the owning side, so the join column should be defined on that side.
    @JoinColumn(name="DEPT_ID")//The @JoinColumn annotation goes on the mapping of the entity that is mapped to the table containing the join column
    private Department department;
    
    @OneToOne
    @JoinColumn(name="PSPACE_ID")//The @JoinColumn annotation goes on the mapping of the entity that is mapped to the table containing the join column
    private ParkingSpace parkingSpace;
    
    @Temporal(TemporalType.DATE) 
    private Date dateOfBirth;
    
    @ElementCollection(targetClass=VacationEntry.class)
    private Collection<VacationEntry> vacationBookings;
    
    @ElementCollection
    private Set<String> nickNames;
    
    @ElementCollection
    @CollectionTable(name="EMP_PHONE")
    @MapKeyColumn(name="PHONE_TYPE")
    @Column(name="PHONE_NUM")
    private Map<String, String> phoneNumbers;
    
    
    
    public Map<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public Collection<VacationEntry> getVacationBookings() {
        return vacationBookings;
    }
    public void setVacationBookings(Collection<VacationEntry> vacationBookings) {
        this.vacationBookings = vacationBookings;
    }
    public Set<String> getNickNames() {
        return nickNames;
    }
    public void setNickNames(Set<String> nickNames) {
        this.nickNames = nickNames;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }
    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
