package ggc.core;

interface NotificationDeliveryMethod {
    void send(Partner partner, Notification notification);
}