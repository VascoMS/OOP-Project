package ggc.core;

import java.io.Serializable;

public class SMSDeliveryMethod implements NotificationDeliveryMethod, Serializable{
        private static SMSDeliveryMethod _delivery;
    
        private SMSDeliveryMethod(){};
    
        public static SMSDeliveryMethod getInstance(){
            if(_delivery == null)
                _delivery = new SMSDeliveryMethod();
            return _delivery;
        }
    
        @Override
        public void send(Partner partner, Notification notification){
            partner.updateNotifications(notification);
        }
        
}
