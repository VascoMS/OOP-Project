package ggc.core;

import java.io.Serializable;


public class CorreioDeliveryMethod implements NotificationDeliveryMethod, Serializable{
    private static CorreioDeliveryMethod _delivery;

    private CorreioDeliveryMethod(){};

    public static CorreioDeliveryMethod getInstance(){
        if(_delivery == null)
            _delivery = new CorreioDeliveryMethod();
        return _delivery;
    }

    @Override
    public void send(Partner partner, Notification notification){
        partner.updateNotifications(notification);
    }
    
}
