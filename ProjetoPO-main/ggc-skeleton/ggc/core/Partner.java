package ggc.core;

import java.util.*;

public class Partner{
    private String _name;
    private String _address;
    private String _id;
    private Status _status;
    private double _points;
    private ArrayList<Batch> _batches;
    private ArrayList<Acquisition> _acquisitions;
    private ArrayList<Sale> _sales;
    private double _totalPayedSalesValue;
    private double _totalAcquisitionValue;
    private ArrayList<Notification> _notifications;

    public Partner(String name, String address, String id){
        _name = name;
        _address = address;
        _id = id;
        _points = 0;
        _status = Status.NORMAL;
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

    public Status getStatus(){
        return _status;
    }

    public double getPoints(){
        return _points;
    }

    public ArrayList<Batch> getBatches(){
        return _batches;
    }

    public ArrayList<Acquisition> getAcquisitions(){
        return _acquisitions;
    }

    public ArrayList<Sale> getSales(){
        return _sales;
    }

    public ArrayList<Notification> getNotifications(){
        return _notifications;
    }

  /*  public int getAcquisitionValue(){
        int value = 0
        for(Acquisition acquisition:_acquisitions){
            value += acquisition.getValue()
        }
        return value;
    }
*/
    public double getTotalSalesValue(){
        return _totalPayedSalesValue;
    }

    public double getTotalAcquisitionValue(){
        return _totalAcquisitionValue;
    }
    
    public String toString(){
        return _id + "|" +_name +"|"+ _address +"|"+ _status +"|"+_points+"|"+_totalAcquisitionValue+"|"+_totalPayedSalesValue;
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
