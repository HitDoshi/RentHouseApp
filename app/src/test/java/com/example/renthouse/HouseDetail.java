package com.example.renthouse;

public class HouseDetail {

    // string variable for
    // storing employee name.
    private String OwnerEmail;

    // string variable for storing
    // employee contact number
    private String employeeContactNumber;

    // string variable for storing
    // employee address.
    private String OwnerName;

    private String City;

    private String Area;

    private String BHK;

    private Integer Price;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public HouseDetail(String name, String email, String number,String city , String area, String bhk , Integer price) {
        this.OwnerName = name;
        this.OwnerEmail = email;
        this.employeeContactNumber = number;
        this.City = city ;
        this.Area = area;
        this.BHK = bhk;
        this.Price = price;
    }

    public HouseDetail()
    {

    }

    // created getter and setter methods
    // for all our variables.
    public String getOwnerEmail() {
        return OwnerEmail;
    }

    public void setOwnerEmail(String OwnerEmail) {
        this.OwnerEmail = OwnerEmail;
    }

    public String getEmployeeContactNumber() {
        return employeeContactNumber;
    }

    public void setEmployeeContactNumber(String employeeContactNumber) {
        this.employeeContactNumber = employeeContactNumber;
    }

    public String getOwnerNames() {
        return OwnerName;
    }

    public void setOwnerName(String OwnerName) {
        this.OwnerName = OwnerName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getBHK() {
        return BHK;
    }


    public void setBHK(String BHK) {
        this.BHK = BHK;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer Price) {
        this.Price = Price;
    }

}
