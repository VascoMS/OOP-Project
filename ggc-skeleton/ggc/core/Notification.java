package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{
    private Product _product;
    private TypeNotification _type;

public Notification(String type){
    if(type.compareTo("BARGAIN")==0)
        _type=TypeNotification.BARGAIN;
    else
        _type=TypeNotification.NEW;
}

public Product getProduct(){
    return _product;
}

public TypeNotification getType(){
    return _type;
}

public String toString(){
    //FIXME nao tenho a certeza do maxprice deveria ser o menor
    return ""+_type.name()+"|"+_product.getId()+"|"+_product.getMaxPrice();
}

}

