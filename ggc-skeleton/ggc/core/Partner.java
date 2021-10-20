package ggc.core;

import java.util.*;

public class Partner {
    private String _name;
    private String _address;
    private String _id;
    private String _status;
    private double _points;
    private ArrayList<Batch> _batches;
    private ArrayList<Acquisition> _acquisitions;
    private ArrayList<Sale> _sales;
    private ArrayList<Notification> _notifications;

    public Partner(String name, String address, String id){
        _name = name;
        _address = address;
        _id = id;
        _points = 0;
        _status = "NORMAL";
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

    public String getStatus(){
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

    @Override
    public int hashCode(){
        return Objects.hash(_id);
    }

    
}
