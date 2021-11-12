package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Partner implements Serializable, NotificationObserver{
    private String _name;
    private String _address;
    private String _id;
    private PartnerStatus _status;
    private double _points;
    private List<Batch> _batches;
    private List<Acquisition> _acquisitions;
    private List<Sale> _sales;
    private double _totalSalesValue;
    private double _totalPayedSalesValue;
    private double _totalAcquisitionValue;
    private List<Notification> _notifications;
    private NotificationDeliveryMethod _deliveryMethod;

    Partner(String id, String name, String address){
        _name = name;
        _address = address;
        _id = id;
        _points = _totalSalesValue = _totalPayedSalesValue = _totalAcquisitionValue = 0;
        _status = NormalStatus.getInstance();
        _sales = new ArrayList<>();
        _batches = new ArrayList<>();
        _acquisitions = new ArrayList<>();
        _notifications = new ArrayList<>();
        _deliveryMethod = DefaultDelivery.getInstance();
    }

    String getName(){
        return _name;
    }

    String getAddress(){
        return _address;
    }

    String getId(){
        return _id;
    }

    double getPoints(){
        return _points;
    }

    List<Batch> getBatches(){
        return _batches;
    }

    List<Acquisition> getAcquisitions(){
        return _acquisitions;
    }

    List<Sale> getSales(){
        return _sales;
    }

    List<Notification> getNotifications(){
        return _notifications;
    }

    double getTotalSalesValue(){
        return _totalSalesValue;
    }

    double getTotalPayedSalesValue(){
        return _totalPayedSalesValue;
    }

    double getTotalAcquisitionValue(){
        return _totalAcquisitionValue;
    }

    void updateNotifications(Notification notification){
        _notifications.add(notification);
    }

    @Override
    public void sendNotifications(Notification notification){
        _deliveryMethod.send(this, notification);

    }

    public void removeNotification(Notification notification){
        _notifications.remove(notification);
    }

    public double computeFine(Date currentDate, Date deadline, int period){
        return _status.computeFine(currentDate, deadline, period);
    }

    public double computeDiscount(Date currentDate, Date deadline, int period){
        return _status.computeDiscount(currentDate, deadline, period);
    }

    public double computePoints(Date currentDate, Sale transaction){
        return _status.computePoints(currentDate, transaction, this);
    }
    
    public void updatePoints(Date currentDate, Sale transaction){
        _points += computePoints(currentDate, transaction);
    }

    public void setStatus(PartnerStatus newStatus){
        _status = newStatus;
    }


    public void addAcquisition(Acquisition transaction){
        _acquisitions.add(transaction);
        _totalAcquisitionValue+= transaction.getBaseValue();
    }

    public void addSaleByCredit(SaleByCredit transaction,Date date){
        _sales.add(transaction);
        _totalSalesValue += transaction.getBaseValue();
    }

    public void addBreakdownSale(BreakdownSale transaction,Date date){
        double payment = transaction.getAmountPaid();
        _sales.add(transaction);
        _totalSalesValue += payment;
        _totalPayedSalesValue += payment;
        updatePoints(date, transaction);
    }

    public void addBatch(Batch batch){
        _batches.add(batch);
    }
    
    public void removeBatch(Batch batch){
        _batches.remove(batch);
    }
    
    public void updatePayedSales(double valuePayed){
        _totalPayedSalesValue += valuePayed;
    }

    public String toString(){
        return _id + "|" +_name +"|"+ _address +"|"+ _status +"|"+Math.round(_points)+"|"+Math.round(_totalAcquisitionValue)+"|"
        +Math.round(_totalSalesValue)+"|"+Math.round(_totalPayedSalesValue);
    }

    @Override
    public int hashCode(){
        return Objects.hash(_id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Partner && _id.equals(((Partner)obj).getId());
    }    
}
