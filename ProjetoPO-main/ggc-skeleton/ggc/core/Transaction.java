package ggc.core;

public abstract class Transaction{
    private int _id;
    private Date _paymentDate;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;

    public abstract boolean isPaid();

    public int getId(){
        return _id;
    }

    public double getBaseValue(){
        return _baseValue;
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
}