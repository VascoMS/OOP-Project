package ggc.core;

import java.io.IOException;
import java.io.Serializable;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)
import java.util.ArrayList;
import java.util.HashMap;

import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransactionId;
  private HashMap<String,Product> _products;
  private ArrayList<Batch> _batches;
  private HashMap<String,Partner> _partners;
  private HashMap<Integer, Transaction> _transactions;

  public Warehouse(){
    _date = new Date(0);
    _products = new HashMap<>();
    _batches = new ArrayList<>();
    _partners = new HashMap<>();
    _transactions = new HashMap<>();
  }

  public Date getDate(){
    return _date;
  }

  public int getNextTransactionId(){
    return _nextTransactionId;
  }

  public HashMap<String,Product> getProducts(){
    return _products;
  }

  public ArrayList<Product> getSortedProducts(){
    ArrayList<Product> products = new ArrayList<Product>();
    for (String i : _products.keySet()) {
      products.add(_products.get(i));
    }
    products.sort(new ProductComparator());
    return products;
  }

  public HashMap<String,Partner> getPartners(){
    return _partners;
  }

  public ArrayList<Partner> getSortedPartners(){
    ArrayList<Partner> partners = new ArrayList<Partner>();
    for (String i : _partners.keySet()) {
      partners.add(_partners.get(i));
    }
    partners.sort(new PartnerComparator());
    return partners;
  }

  public ArrayList<Batch> getBatches(){
    return _batches;
  }

  public ArrayList<Batch> getSortedBatches(){
    ArrayList<Batch> batches = new ArrayList<Batch>();
    batches.addAll(_batches);
    batches.sort(new BatchComparator());
    return batches;
  }

  public ArrayList<Batch> getBatchesPartner(ArrayList<Batch> sortedBatches, Partner partner){
    ArrayList<Batch> batchesPartner = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getPartner().equals(partner))
        batchesPartner.add(b);
    }
    
    return batchesPartner;
  }

  public ArrayList<Batch> getBatchesProduct(ArrayList<Batch> sortedBatches, Product product){
    ArrayList<Batch> batchesProduct = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getProduct().equals(product))
        batchesProduct.add(b);
    }
    
    return batchesProduct;
  }

  public void newDate(int days){
    _date.add(days);
  }

  public void addProduct(Product product) throws BadEntryException{
    if(_products.containsKey(product.getId()))
      throw new BadEntryException(product.getId());
    _products.put(product.getId(), product);
  }

  public Product getProduct(String id) throws BadEntryException{
    if(_products.containsKey(id))
      return _products.get(id);
    throw new BadEntryException(id);
  }

  public ArrayList<Component> createComponents(ArrayList<Product> products, ArrayList<Integer> quantities){
    ArrayList<Component> components = new ArrayList<>();
    for(int i=0; i<quantities.size(); i++){
      components.add(new Component(quantities.get(i), products.get(i)));
    }
    return components;
  }

  public void addPartner(Partner partner) throws BadEntryException{
    if(_partners.containsKey(partner.getId()))
      throw new BadEntryException(partner.getId());
    _partners.put(partner.getId(), partner);
  }

  public Partner getPartner(String id) throws BadEntryException{
    if(_partners.containsKey(id))
      return _partners.get(id);
    throw new BadEntryException(id);
      
  }


  public void addBatch(Batch batch){
    _batches.add(batch);
    batch.getProduct().addBatch(batch);
  }
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method
  }

}
