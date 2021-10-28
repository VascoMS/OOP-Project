package ggc.core;

import java.io.Serializable;

 /**
 * Representa um Lote 
 * Um lote e uma das entidades guardadas no armazem, e caracterizado por 
 * um produto, a quantidade do produto e do parceiro associado a transacao.
 * @author Vasco Silva ist199132, Vicente Gomes ist199135
 * @version 1.0
 */
public class Batch implements Serializable{
/* preco por unidade de produto armazenado */
    private double _price;
/* quantidade de produto(stock) no lote */
    private int _quantity;
/* parceiro associado a transacao que originou a criacao deste lote */
    private Partner _partner;
/* produto armazenado no lote */
    private Product _product;

/**
 * Construtor da classe
 * @param price preco por unidade de produto
 * @param quantity quantidade de produto no lote
 * @param partner parceiro associado ao lote
 * @param product produto armazenado no lote
*/
public Batch(double price, int quantity, Partner partner, Product product){
    _price=price;
    _quantity=quantity;
    _partner=partner;
    _product=product;
}
/** obter preco por unidade de produto
 * @return double preco
 */
public double getPrice(){
    return _price;
}
/** obter quantidade de produto
 * @return int quantidade
 */
public int getQuantity(){
    return _quantity;
}

/** obter parceiro
 * @return Partner parceiro
 */
public Partner getPartner(){
    return _partner;
}

/** obter produto
 * @return Product produto
 */
public Product getProduct(){
    return _product;
}
/** devolve a representacao interna em String do lote para a sua posterior apresentacao
 * @return String apresentacao
 */
@Override
public String toString(){
    return ""+_product.getId()+"|"+_partner.getId()+"|"+(int)_price+"|"+_quantity;
}

Batch makeCopy(){
    return new Batch(_price, _quantity, _partner, _product);
}

}
