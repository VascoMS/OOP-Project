package ggc.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import ggc.core.exception.*;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  //DATE

  public Date getDateWarehouse(){
    return _warehouse.getDate();
  }

  public void incrementDate(int days) throws CoreInvalidDateException{
    _warehouse.advanceDate(days);
  }

  //PRODUCTS

  Product getProduct(String id) throws CoreUnknownProductKeyException{
    return _warehouse.getProduct(id);
  }

  public boolean hasProduct(String id){
    return _warehouse.hasProduct(id);
  }

  void registerAggregateProduct(String id, double alpha, List<Product> products, List<Integer> quantities){
    List<Component> components = _warehouse.createComponents(products, quantities);
    Recipe recipe = new Recipe(alpha, components);
    Product newProduct = new AggregateProduct(id, recipe);
    _warehouse.addProduct(newProduct);
  }

  void registerSimpleProduct(String id){
    Product newProduct = new SimpleProduct(id);
    _warehouse.addProduct(newProduct);
  }

  public List<Product> getAllProductsWarehouse(){
    return _warehouse.getProducts();
  }

  public List<Product> getProductsSorted(){
    return _warehouse.getSortedProducts();
  }

  //PARTNERS

  public void addPartner(String id, String name, String address) throws CoreDuplicatePartnerKeyException{
    _warehouse.addPartner(id, name, address);
  }

  Partner getPartner(String id) throws CoreUnknownPartnerKeyException {
    return _warehouse.getPartner(id);
  }

  public String showPartner(String partnerId) throws CoreUnknownPartnerKeyException{
    return _warehouse.showPartner(partnerId);
  }

  public List<Notification> showNotifications(String partnerId) throws CoreUnknownPartnerKeyException{
    return _warehouse.showNotifications(partnerId);
  }

  public void toggleNotifications(String partnerId, String productId) throws CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    _warehouse.toggleNotifications(partnerId, productId);
  }


  public List<Partner> getSortedPartners(){
    return _warehouse.getSortedPartners();
  }

  public List<Partner> getAllPartnersWarehouse(){
    return _warehouse.getPartners();
  }

  public List<Acquisition> getPartnerAcquisitions(String partnerId) throws CoreUnknownPartnerKeyException {
    return _warehouse.getPartnerAcquisitions(partnerId);
  }

  public List<Sale> getPartnerSales(String partnerId) throws CoreUnknownPartnerKeyException {
    return _warehouse.getPartnerSales(partnerId);
  }

  //TRANSACTIONS

  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity) throws CoreUnknownPartnerKeyException{
    _warehouse.registerAcquisition(partnerId, productId, productPrice, quantity);
  }

  public void registerAcquisition(String partnerId, String productId, double productPrice, int quantity, List<String> products, List<Integer> quantities, double alpha, int numberComponents) throws CoreUnknownPartnerKeyException, CoreUnknownProductKeyException{
    _warehouse.registerAcquisition(partnerId, productId, productPrice, quantity, products, quantities, alpha, numberComponents);
  }
  public Transaction getTransaction(int id) throws CoreUnknownTransactionKeyException{
    return _warehouse.getTransaction(id);
  }

  public void registerSaleByCredit(String partnerId, String productId, int deadline, int amount)throws CoreUnknownPartnerKeyException, CoreUnavailableProductException, CoreUnknownProductKeyException{
    _warehouse.registerSaleByCredit(partnerId, productId, deadline, amount);
  }

  public void registerBreakdownSale(String partnerId, String productId, int amount) throws CoreUnknownPartnerKeyException, CoreUnavailableProductException, CoreUnknownProductKeyException{
    _warehouse.registerBreakdownSale(partnerId, productId, amount);
  }

  public void payTransaction(int id)throws CoreUnknownTransactionKeyException{
    _warehouse.payTransaction(id);
  }

  public List<Sale> getPayedSalesByPartner(String partnerId) throws CoreUnknownPartnerKeyException{
    return _warehouse.getPayedSalesByPartner(partnerId);
  }

  public String showTransaction(int id) throws CoreUnknownTransactionKeyException{
    return _warehouse.showTransaction(id);
  }

  //BATCHES

  List<Batch> getAllBatchesWarehouse(){
    return _warehouse.getBatches();
  }

  void addBatch(double price, int quantity, Partner partner, Product product){
    Batch batch = new Batch(price, quantity, partner, product);
    _warehouse.addBatch(batch, false);
  }

  public List<Batch> getBatchesSorted(){
    return _warehouse.getSortedBatches();
  }

  public List<Batch> getBatchesPartner(String partnerId) throws CoreUnknownPartnerKeyException{
    Partner partner = _warehouse.getPartner(partnerId);
    return _warehouse.getBatchesPartner(this.getBatchesSorted(), partner);
  }

  public List<Batch> getBatchesProduct(String productId) throws CoreUnknownProductKeyException{
    Product product = _warehouse.getProduct(productId);
    return _warehouse.getBatchesProduct(this.getBatchesSorted(), product);
  }

  public List<Batch> getBatchesProductUnderPrice(double price){
    return _warehouse.getBatchesProductUnderPrice(price);
  }
  

// BALANCE

  public double getAccountingBalance(){
    return _warehouse.getAccountingBalance();
  }

  public double getAvailableBalance(){
    return _warehouse.getAvailableBalance();
  }

  void updateAccountingBalance(){
    _warehouse.updateAccountingBalance();
  }

  void updateAvailableBalance(double payment){
    _warehouse.updateAvailableBalance(payment);
  }


  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws MissingFileAssociationException{
    if(_filename.equals(""))
      throw new MissingFileAssociationException();
    try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename))){
    out.writeObject(_warehouse);
    } catch(IOException e){
      e.printStackTrace();
      //System.err.println("Falha ao guardar estado");
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException{
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
      _warehouse = (Warehouse) in.readObject();
      _filename = filename;
    } catch(IOException a){
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException{
    try {
      _warehouse.importFile(textfile, this);
    } catch (IOException | BadEntryException | CoreDuplicatePartnerKeyException | CoreUnknownPartnerKeyException | CoreUnknownProductKeyException e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
