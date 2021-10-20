package ggc.core;

public class Component {
    private Product _product;
    private int _quantity;
    
    public Component(int quantity, Product product){
        _product=product;
        _quantity=quantity;
    }

    public Product getProduct(){
        return _product;
    }

    public int getQuantity(){
        return _quantity;
    }
}
