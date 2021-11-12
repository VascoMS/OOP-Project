package ggc.core;

import java.io.Serializable;

public class DefaultDelivery implements NotificationDeliveryMethod, Serializable{
    private static DefaultDelivery _delivery;

    private DefaultDelivery(){};

    public static DefaultDelivery getInstance(){
        if(_delivery == null)
            _delivery = new DefaultDelivery();
        return _delivery;
    }

    @Override
    public void send(Partner partner, Notification notification){
        partner.updateNotifications(notification);
    }
    
}
