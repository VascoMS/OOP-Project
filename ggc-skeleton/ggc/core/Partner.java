package ggc.core;

import java.io.Serializable;
import java.util.*;

public class Partner implements Serializable{
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

    public Partner(String id, String name, String address){
        _name = name;
        _address = address;
        _id = id;
        _points = _totalSalesValue = _totalPayedSalesValue = _totalAcquisitionValue = 0;
        _status = new NormalStatus();
        _sales = new ArrayList<>();
        _batches = new ArrayList<>();
        _acquisitions = new ArrayList<>();
        _notifications = new ArrayList<>();
    }

    public String getName(){
        return _name;
    }

    public String getAddress(){
        return _address;
    }

    public String getId(){
        return _id;
    }

    public double getPoints(){
        return _points;
    }

    public List<Batch> getBatches(){
        return _batches;
    }

    public List<Acquisition> getAcquisitions(){
        return _acquisitions;
    }

    public List<Sale> getSales(){
        return _sales;
    }

    public List<Notification> getNotifications(){
        return _notifications;
    }

    public double getTotalSalesValue(){
        return _totalSalesValue;
    }

    public double getTotalPayedSalesValue(){
        return _totalPayedSalesValue;
    }

    public double getTotalAcquisitionValue(){
        return _totalAcquisitionValue;
    }

    public void addAcquisition(Acquisition transaction){
        _acquisitions.add(transaction);
        _totalAcquisitionValue+= transaction.getBaseValue();
    }

    public void addBatch(Batch batch){
        _batches.add(batch);
    }
    
    public String toString(){
        return _id + "|" +_name +"|"+ _address +"|"+ _status +"|"+(long)_points+"|"+(long)_totalAcquisitionValue+"|"+(long)_totalSalesValue+"|"+(long)_totalPayedSalesValue;
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
