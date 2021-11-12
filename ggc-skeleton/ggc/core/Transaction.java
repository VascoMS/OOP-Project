package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{
    private int _id;
    private Date _paymentDate;
    private double _baseValue;
    private double _amountPaid;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private Date _deadline;

    
    Transaction(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        _id = id;
        _baseValue = baseValue;
        _quantity = quantity;
        _product = product;
        _partner = partner;
        _deadline = new Date(deadline);
    }

    int getId(){
        return _id;
    }
    
    double getBaseValue(){
        return _baseValue;
    }
    
    double getAmountPaid(){
        return _amountPaid;
    }
    
    Date getDeadline(){
        return _deadline;
    }
    
    void setAmountPaid(double amount){
        _amountPaid = amount;
    }
    
    int getQuantity(){
        return _quantity;
    }
    
    Product getProduct(){
        return _product;
    }
    
    Partner getPartner(){
        return _partner;
    }
    
    Date getPaymentDate(){
        return _paymentDate;
    }
    
    void setPaymentDate(int date){
        
        _paymentDate=new Date(date);
    }
    
    abstract boolean isPaid();

    abstract double calculatePayment(Date currentDate);
    
    public abstract String toString(Date date);
}