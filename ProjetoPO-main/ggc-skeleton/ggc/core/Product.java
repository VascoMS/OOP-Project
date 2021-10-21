package ggc.core;

import java.util.*;

public abstract class Product{
    private double _maxPrice;
    private String _id;
    private ArrayList<Batch> _batches;
    private long _totalStock;

    Product(String id){
        _id=id;
    }
    
    public double getMaxPrice(){
        return _maxPrice;
    }

    public void updateMaxPrice(double maxPrice){
        _maxPrice=maxPrice;
    }

    public void addBatch(Batch batch) {
        _batches.add(batch);
    }

    public String getId(){
        return _id;
    }

    public ArrayList<Batch> getBatches(){
        return _batches;
    }

    public long getTotalStock(){
        return _totalStock;
    }

    public void updateTotalStock(){
        for(Batch b: _batches){
            _totalStock += b.getQuantity();
        }
    }

    abstract int checkQuantity(int quantity, Partner partner);



}
