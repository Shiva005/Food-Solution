package com.bd.foodsolution.orderdetails;

public class OrderGetterSetter {
    String uName;
    String itemName;
    String totalPrice;
    String phoneNumber;
    String userAddress;
    String quantity;


    public OrderGetterSetter()
    {

    }

    public OrderGetterSetter(String uName, String itemName, String totalPrice, String phoneNumber, String userAddress,String Quantity) {
        this.uName = uName;
        this.itemName = itemName;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.userAddress = userAddress;
        this.quantity=Quantity;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}