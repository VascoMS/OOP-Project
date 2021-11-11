package ggc.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.CoreDuplicatePartnerKeyException;
import ggc.core.exception.CoreInvalidDateException;
import ggc.core.exception.CoreUnavailableProductException;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;
import ggc.core.exception.CoreUnknownTransactionKeyException;


/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransactionId;
  private double _accountingBalance;
  private double _availableBalance;
  private Map<String,Product> _products;
  private List<Batch> _batches;
  private Map<String,Partner> _partners;
  private Map<Integer, Transaction> _transactions;

  public Warehouse(){
    _date = new Date(0);
    _products = new HashMap<>();
    _batches = new ArrayList<>();
    _partners = new HashMap<>();
    _transactions = new HashMap<>();
    _accountingBalance = 0;
    _availableBalance = 0;
  }

  //DATE

  public Date getDate(){
    return _date;
  }

  public void advanceDate(int days) throws CoreInvalidDateException{
    if(days < 0)
      throw new CoreInvalidDateException(days);
    _date.add(days);
  }

  //PRODUCTS

  public List<Product> getProducts(){
    List<Product> products = new ArrayList<>();
    products.addAll(_products.values());
    return products;
  }

  public List<Product> getSortedProducts(){
    List<Product> products = getProducts();
    products.sort(new ProductComparator());
    return products;
  }

  public void addProduct(Product product) {
    setBaseObservers(product);
    _products.put(product.getId(), product);
  }

  public Product getProduct(String id) throws CoreUnknownProductKeyException{
    if(_products.containsKey(id))
      return _products.get(id);
    throw new CoreUnknownProductKeyException(id);
  }

  public boolean hasProduct(String id){
    try {
      getProduct(id);
      return true;
    } catch (CoreUnknownProductKeyException e) {
      return false;
    }
  }

  public List<Component> createComponents(List<Product> products, List<Integer> quantities){
    List<Component> components = new ArrayList<>();
    for(int i=0; i<quantities.size(); i++){
      components.add(new Component(quantities.get(i), products.get(i)));
    }
    return components;
  }
  
  public void setBaseObservers(Product product){
    for(Partner partner : _partners.values())
      product.addObserver(partner);
  }


  //PARTNERS

  public List<Partner> getPartners(){
    List<Partner> partners = new ArrayList<>();
    partners.addAll(_partners.values());
    return partners;
  }

  public List<Partner> getSortedPartners(){
    List<Partner> partners = getPartners();
    partners.sort(new PartnerComparator());
    return partners;
  }

  public void addPartner(String id, String name, String address) throws CoreDuplicatePartnerKeyException{
    if(_partners.containsKey(id.toLowerCase()))
      throw new CoreDuplicatePartnerKeyException(id);
    Partner newPartner = new Partner(id, name, address);
    addObserverToAllProducts(newPartner);
    _partners.put(id.toLowerCase(), newPartner);
  }

  public void addObserverToAllProducts(Partner partner){
    for(Product product: _products.values()){
      product.addObserver(partner);
    }
  }

  public Partner getPartner(String id) throws CoreUnknownPartnerKeyException{
    if(_partners.containsKey(id.toLowerCase()))
      return _partners.get(id.toLowerCase());
    throw new CoreUnknownPartnerKeyException(id);
  }

  public String getPartnerAcquisitions(String partnerId) throws CoreUnknownPartnerKeyException {
    String acquisitions="";
    Partner partner = getPartner(partnerId);
    for(Acquisition acquisition : partner.getAcquisitions()){
      acquisitions+=acquisition.toString(_date);
    }
    return acquisitions;
  }

  public String getPartnerSales(String partnerId) throws CoreUnknownPartnerKeyException{
    String sales = "";
    Partner partner = getPartner(partnerId);
    for(Sale sale : partner.getSales()){
      sales+=sale.toString(_date);
    }
    return sales;
  }

  //TRANSACTIONS

  public int getNextTransactionId(){
    return _nextTransactionId;
  }

  public Transaction getTransaction(int id) throws CoreUnknownTransactionKeyException{
    if(_transactions.containsKey(id))
      return _transactions.get(id);
    throw new CoreUnknownTransactionKeyException(id);
  }

  public List<Transaction> getTransactions(){
    List<Transaction> transactions = new ArrayList<>();
    transactions.addAll(_transactions.values());
    return transactions;
  }

  public String showTransaction(int id) throws CoreUnknownTransactionKeyException{
    Transaction transaction = getTransaction(id);
    return transaction.toString(_date);
  }

  public void payTransaction(int id)throws CoreUnknownTransactionKeyException{
      Transaction transaction = getTransaction(id);
      if(transaction.isPaid())
        return;
      SaleByCredit sale = (SaleByCredit) transaction;
      double payment = sale.getAmountOwed();
      sale.paySale(payment, _date);
      updateAvailableBalance(payment);
  }

  public void registerBreakdownSale(String partnerId, String productId, int amount) throws CoreUnknownPartnerKeyException, CoreUnknownProductKeyException, CoreUnavailableProductException{
    Partner partner = getPartner(partnerId);
    Product product = getProduct(productId);
    if(!product.checkQuantity(amount, partner))
      throw new CoreUnavailableProductException(product.getId(), product.getTotalStock());
    if(!(product instanceof AggregateProduct))
      return;
    AggregateProduct aggregateProduct = (AggregateProduct) product;
    List<Batch> batches = product.getBatches();
    double aggregateProductValue = calculateBaseValueAndUpdateBatches(product, partner, amount, batches);
    double componentsValue=0;
    List<Component> components = aggregateProduct.getRecipe().getComponents();
    for(Component component : components){
      double price = component.getProduct().getLowestPrice();
      int quantity = component.getQuantity();
      componentsValue += price*quantity;
      addBatch(new Batch(price, quantity*amount, partner, component.getProduct()), true);
    }
    //cada produto derivado da este valor
    componentsValue *= amount;
    double realValue = aggregateProductValue - componentsValue;
    double payedValue;
    if(realValue < 0)
      payedValue = 0;
    else
      payedValue=realValue;
    Transaction transaction = new BreakdownSale(_nextTransactionId, realValue, amount, product, partner, payedValue, _date.getDays());
    transaction.setPaymentDate(_date);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addBreakdownSale((BreakdownSale)transaction, _date);
    partner.updatePoints(_date, (BreakdownSale)transaction);
    updateAvailableBalance(payedValue);    
  }

  public void registerSaleByCredit(String partnerId, String productId, int deadline, int amount) throws CoreUnknownPartnerKeyException, CoreUnavailableProductException, CoreUnknownProductKeyException{
    Partner partner = getPartner(partnerId);
    Product product = getProduct(productId);
    if(!product.checkQuantity(amount, partner))
      product.aggregateProduct(productId, amount);
    List<Batch> batches = product.getBatchesSortedByPrice();
    double baseValue=calculateBaseValueAndUpdateBatches(product, partner, amount, batches);
    Transaction transaction = new SaleByCredit(_nextTransactionId, baseValue, amount, product, partner, deadline);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addSaleByCredit((SaleByCredit)transaction, _date);
  }

  public double calculateBaseValueAndUpdateBatches(Product product, Partner partner, int amount, List<Batch> batches){
    double baseValue=0;
    Batch batch;
    Iterator<Batch> iter = product.getBatchesSortedByPrice().iterator();
    while(amount != 0 && iter.hasNext()){
      batch = iter.next();
      if(batch.getQuantity() <= amount){
        baseValue += batch.getQuantity()*batch.getPrice();
        amount -= batch.getQuantity();
        removeBatch(batch);
      }
      else if(batch.getQuantity() > amount){
        baseValue += amount*batch.getPrice();
        batch.removeAmount(amount);
        amount = 0;
      }
     }
    product.updateTotalStock();
    return baseValue;
  }

  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity) throws CoreUnknownPartnerKeyException{
    Partner partner = getPartner(partnerId);
    Product product;
    double baseValue=quantity*productPrice;
    boolean notifiable = false;
    try {
      product = getProduct(productId);
      notifiable = true;
    } catch (CoreUnknownProductKeyException e) {
      product = new SimpleProduct(productId);
      addProduct(product);
    }
    Transaction transaction = new Acquisition(_nextTransactionId, baseValue, quantity, product, partner, _date.getDays());
    transaction.setPaymentDate(_date);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addAcquisition((Acquisition) transaction);
    addBatch(new Batch(productPrice, quantity, partner, product), notifiable);
    updateAvailableBalance(-baseValue);
  }

  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity, List<String> productsIDs, List<Integer> quantities, double alpha, int numberComponents) throws CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    Partner partner = getPartner(partnerId);
    double baseValue=quantity*productPrice;
    boolean notifiable = false;
    List<Component> components = new ArrayList<>();
    try{
      getProduct(productId);
      notifiable = true;
    } catch(CoreUnknownProductKeyException e){
    for(int i = 0;i < numberComponents; i++){
      components.add(new Component(quantities.get(i), getProduct(productsIDs.get(i))));
    }
    addProduct(new AggregateProduct(productId, new Recipe(alpha, components)));
    }
    Transaction transaction = new Acquisition(_nextTransactionId, baseValue, quantity, _products.get(productId), partner, _date.getDays());
    transaction.setPaymentDate(_date);
    _transactions.put(_nextTransactionId++, transaction);
    partner.addAcquisition((Acquisition) transaction);
    addBatch(new Batch(productPrice, quantity, partner, _products.get(productId)), notifiable);
    updateAvailableBalance(-baseValue);
  }

  public String getPayedSalesByPartner(String partnerId) throws CoreUnknownPartnerKeyException{
    Partner partner = getPartner(partnerId);
    String payments="";
    for(Sale sale: partner.getSales()){
      if(sale.isPaid())
        payments = sale.toString(_date);
    }
    return payments;
  }


  //BATCHES

  public List<Batch> getBatches(){
    return _batches;
  }

  public List<Batch> getSortedBatches(){
    List<Batch> batches = _batches;
    batches.sort(new BatchComparator());
    return Collections.unmodifiableList(batches);
  }

  public List<Batch> getBatchesPartner(List<Batch> sortedBatches, Partner partner){
    List<Batch> batchesPartner = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getPartner().equals(partner))
        batchesPartner.add(b);
    }
    return batchesPartner;
  }

  public List<Batch> getBatchesProduct(List<Batch> sortedBatches, Product product){
    List<Batch> batchesProduct = new ArrayList<>();
    for(Batch b: sortedBatches){
      if(b.getProduct().equals(product))
        batchesProduct.add(b);
    }
    
    return batchesProduct;
  }

  public void addBatch(Batch batch, boolean notifiable){
    _batches.add(batch);
    batch.getProduct().addBatch(batch, notifiable);
    batch.getPartner().addBatch(batch);
  }

  public void removeBatch(Batch batch){
    _batches.remove(batch);
    batch.getProduct().removeBatch(batch);
    batch.getPartner().removeBatch(batch);
  }

  public List<Batch> getBatchesProductUnderPrice(int price){
    List<Batch> batches = getSortedBatches();
    List<Batch> batchesPrice = new ArrayList<>();
    for(Batch batch: batches)
      if(batch.getPrice() < price)
        batchesPrice.add(batch);
    return batchesPrice;
  }

  //BALANCE

  public double getAvailableBalance(){
    return _availableBalance;
  }

  public double getAccountingBalance(){
    updateAccountingBalance();
    return _accountingBalance;
  }

  public void updateAvailableBalance(double payment){
    _availableBalance += payment;
  }

  public void updateAccountingBalance(){
    _accountingBalance=0;
    List<Transaction> transactions = new ArrayList<>(_transactions.values());
    for(Transaction transaction : transactions){
        _accountingBalance += transaction.calculatePayment(_date);
      }
  }
  


  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile, WarehouseManager warehouseManager) throws IOException, BadEntryException, CoreDuplicatePartnerKeyException, CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    Parser parser = new Parser(warehouseManager);
    parser.parseFile(txtfile);
  }

}
