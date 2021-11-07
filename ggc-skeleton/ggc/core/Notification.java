package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Notification implements Serializable{
    List<NotificationObserver> _observers = new ArrayList<>();
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

