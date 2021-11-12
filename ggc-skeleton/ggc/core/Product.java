package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import ggc.core.exception.CoreUnavailableProductException;

 /**
 * Representa um produto 
 * Um produto é uma das entidades guardadas no armazém,é caracterizado por 
 * um identificador(o nome do mesmo) e está contido em lotes.
 * @author Vasco Silva ist199132, Vicente Gomes ist199135
 * @version 1.0
 */
public abstract class Product implements Serializable{
    /*Guarda o preço máximo do produto*/

    private double _maxPrice;

    /*Guarda o identificador do produto*/

    private String _id;

    /*Guarda os lotes onde o produto está contido*/

    private List<Batch> _batches;

    /*Guarda o stock total de um produto no armazém */

    private int _totalStock;

    private List<NotificationObserver> _observers = new ArrayList<>();

    private int _periodN;

    /** 
     * Construtor da classe, define o id do produto.
     * @param id valor de input que define o id do produto.
     */
    Product(String id){
        _id=id;
        _batches = new ArrayList<Batch>();
        _maxPrice = _totalStock=0;
    }
    
    /**
     * Obter o preço máximo do produto.
     * @return preço máximo do produto.
     */
    double getMaxPrice(){
        return _maxPrice;
    }

    int getPeriodN(){
        return _periodN;
    }

    void setPeriodN(int periodN){
        _periodN = periodN;
    }

    /**
     * Atualiza o preço máximo do produto, procurando o preço maximo nos lotes do produto.
     */
    void updateMaxPrice(){
        for(Batch batch: _batches){
            if(batch.getPrice() > _maxPrice)
                _maxPrice = batch.getPrice();
        }
    }

    double getLowestPrice(){
        if(_batches.isEmpty())
            return _maxPrice;
        double lowPrice = _maxPrice;
        for(Batch batch : _batches){
            if(batch.getPrice() < lowPrice)
                lowPrice = batch.getPrice();
        }
        return lowPrice;
    }

    /**
     * Adiciona um lote à coleção que guarda os lotes onde o produto está contido.
     * @param batch lote a adicionar.
     */

    Notification createNotification(TypeNotification type, Batch batch){
        switch(type.name()){
            case "NEW":
            return new Notification(batch.getProduct(), batch.getPrice(), type);

            case "BARGAIN":
            return new Notification(batch.getProduct(), batch.getPrice(), type);
        }
        return null;
    }

    Notification checkNotification(Batch batch){
        if(getTotalStock() == 0)
            return createNotification(TypeNotification.NEW, batch);
        else if(getLowestPrice() > batch.getPrice())
            return createNotification(TypeNotification.BARGAIN, batch);
        return null;
    }

    void addBatch(Batch batch, boolean notifiable) {
        Notification notification;
        if(notifiable){
            notification = checkNotification(batch);
            if(notification!=null)
                notifyObservers(notification);
        } 
        _batches.add(batch);
        updateTotalStock();
        updateMaxPrice();
    }

    void removeBatch(Batch batch){
        _batches.remove(batch);
        updateTotalStock();
    }

    void updateTotalStock(){
        Iterator<Batch> iter = _batches.iterator();
        int totalStock = 0;
        while(iter.hasNext()){
            Batch batch = iter.next();
            totalStock += batch.getQuantity();
        }
        _totalStock = totalStock;
    }

    void removeObserver(NotificationObserver observer){
        if(_observers.contains(observer))
            _observers.remove(observer);
    }

    void addObserver(NotificationObserver observer){
        if(!_observers.contains(observer))
            _observers.add(observer);
    }

    /**
     * Devolve o id do produto
     * @return id do produto 
     */
    String getId(){
        return _id;
    }

    /**
     * Devolve a lista de lotes do produto
     * @return lista de lotes do produto
     */
    public List<Batch> getBatches(){
        return Collections.unmodifiableList(_batches);
    }

    /**
     * Devolve o stock total do produto
     * @return stock total do produto
     */
    int getTotalStock(){
        return _totalStock;
    }

    List<Batch> getBatchesSortedByPrice(){
        List<Batch> sortedBatches = new ArrayList<>(_batches);
        sortedBatches.sort(new BatchPriceComparator());
        return sortedBatches;
    }

    void toggleObserver(NotificationObserver observer){
        if(_observers.contains(observer))
            _observers.remove(observer);
        else
            _observers.add(observer);
    }

    void notifyObservers(Notification notification){
        for(NotificationObserver observer: _observers)
            observer.sendNotifications(notification);
    }

    /** substitui o metodo para comparar produtos utilizando o identificador unico
     * @param Obj um objeto
     * @return boolean se dois objetos sao iguais
     */
    @Override
    public boolean equals(Object obj){
        return obj instanceof Product && _id.equals(((Product)obj).getId());
    }

    /** substitui o metodo para guardar os objetos no Map de modo a certificar que dois produtos diferentes tem dois hashcodes diferentes
     * @return int o valor do id apos a funcao hash
     */
    @Override
    public int hashCode(){
        return Objects.hash(_id);
    }

    abstract boolean checkAggregation(int amount, int available) throws CoreUnavailableProductException;
    abstract boolean checkQuantity(int quantity);
    abstract void aggregateProduct(int quantity, int available, Partner partner, Warehouse warehouse) throws CoreUnavailableProductException;

    double calculatePrice(int amount, Warehouse warehouse){
            double baseValue=0;
            int quantity = amount;
            Batch batch;
            Iterator<Batch> iter = getBatchesSortedByPrice().iterator();
            while(amount != 0 && iter.hasNext()){
              batch = iter.next();
              if(batch.getQuantity() <= amount){
                baseValue += batch.getQuantity()*batch.getPrice();
                amount -= batch.getQuantity();
                warehouse.removeBatch(batch);
            }
            else if(batch.getQuantity() > amount){
                baseValue += amount*batch.getPrice();
                batch.removeAmount(amount);
                amount = 0;
              }
             }
            updateTotalStock();
            return baseValue/quantity;
        }
    }
