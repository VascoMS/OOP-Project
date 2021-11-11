package ggc.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Notification implements Serializable{
    Set<NotificationObserver> _observers = new HashSet<>();
    Product _product;
    double _productPrice;
    TypeNotification _type;
    NotificationDeliveryMethod _deliveryMethod;
    
    public Notification(Product product, double productPrice, TypeNotification type){
        _product = product;
        _productPrice = productPrice;
        _type = type;
    }
    
    public void setProductPrice(double productPrice){
        _productPrice = productPrice;
    }

    public void setType(TypeNotification type){
        _type = type;
    }

    public void setDeliveryMethod(NotificationDeliveryMethod delivery){
        _deliveryMethod=delivery;
    }


    @Override
    public String toString(){
        return ""+_type.name()+"|"+_product+"|"+_productPrice;
}

}

