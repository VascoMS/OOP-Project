package ggc.core;

import java.io.Serializable;


public class Notification implements Serializable{
    Product _product;
    double _productPrice;
    TypeNotification _type;
    
    Notification(Product product, double productPrice, TypeNotification type){
        _product = product;
        _productPrice = productPrice;
        _type = type;
    }

    @Override
    public String toString(){
        return ""+_type.name()+"|"+_product.getId()+"|"+Math.round(_productPrice);
}

}

