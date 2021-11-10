package ggc.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Notification implements Serializable{
    Set<NotificationObserver> _observers = new HashSet<>();
    String _productId;
    double _productPrice;
    TypeNotification _type;
    NotificationDeliveryMethod _deliveryMethod;
    
    public Notification(String productId){
        _productId = productId;
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

    public void notifyObservers(){
        for(NotificationObserver observer: _observers)
            observer.notify();
    }

    @Override
    public String toString(){
        return ""+_type.name()+"|"+_productId+"|"+_productPrice;
}

}

