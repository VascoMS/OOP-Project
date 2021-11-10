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

    public abstract boolean isPaid();

    public Transaction(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        _id = id;
        _baseValue = baseValue;
        _quantity = quantity;
        _product = product;
        _partner = partner;
        _deadline = new Date(deadline);
    }
    public int getId(){
        return _id;
    }

    public double getBaseValue(){
        return _baseValue;
    }

    public double getAmountPaid(){
        return _amountPaid;
    }

    public Date getDeadline(){
        return _deadline;
    }

    public void setAmountPaid(double amount){
        _amountPaid = amount;
    }

    public int getQuantity(){
        return _quantity;
    }

    public Product getProduct(){
        return _product;
    }

    public Partner getPartner(){
        return _partner;
    }

    public Date getPaymentDate(){
        return _paymentDate;
    }

    public void setPaymentDate(Date date){
        _paymentDate=date;
    }

    public abstract double calculatePayment(Date currentDate);

    public abstract String toString(Date date);
}