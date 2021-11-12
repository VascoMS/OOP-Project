package ggc.core;

import java.io.Serializable;

public class Component implements Serializable{
    private Product _product;
    private int _quantity;
    
    Component(int quantity, Product product){
        _product=product;
        _quantity=quantity;
    }

    Product getProduct(){
        return _product;
    }

    int getQuantity(){
        return _quantity;
    }

    @Override
    public String toString(){
		return ""+_product.getId()+":"+_quantity; 
        
    }
}
