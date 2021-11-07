package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{
    

    public String toString(){
        return ""+_type.name()+"|"+_product.getId()+"|"+"precoMaisBaixo";
}

}

