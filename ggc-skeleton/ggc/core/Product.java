package ggc.core;

import java.io.Serializable;
import java.util.*;

public abstract class Product implements Serializable{
    private double _maxPrice;
    private String _id;
    private List<Batch> _batches;
    private long _totalStock;

    Product(String id){
        _id=id;
        _batches = new ArrayList<Batch>();
        _maxPrice = _totalStock=0;
    }
    
    public double getMaxPrice(){
        return _maxPrice;
    }

    public void updateMaxPrice(){
        for(Batch batch: _batches){
            if(batch.getPrice() > _maxPrice)
                _maxPrice = batch.getPrice();
        }
    }

    public void addBatch(Batch batch) {
        _batches.add(batch);
        _totalStock += batch.getQuantity();
        updateMaxPrice();
    }

    public String getId(){
        return _id;
    }

    public List<Batch> getBatches(){
        return _batches;
    }

    public long getTotalStock(){
        return _totalStock;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Product && _id.equals(((Product)obj).getId());
    }

    public int hashCode(){
        return Objects.hash(_id);
    }

    abstract int checkQuantity(int quantity, Partner partner);



}
