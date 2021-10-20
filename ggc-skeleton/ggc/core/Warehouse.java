package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.*;
import java.io.Serializable;
import java.io.IOException;

import ggc.app.exception.DuplicatePartnerKeyException;
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
  private HashMap<String,Partner> _partners;

  public Warehouse(){
    _date = new Date(0);
  }

  public Date getDate(){
    return _date;
  }

  public void newDate(int days){
    _date.add(days);
  }

  public void addPartner(Partner partner) throws DuplicatePartnerKeyException{
    if(_partners.containsKey(partner.getId()))
      throw new DuplicatePartnerKeyException(partner.getId());
    _partners.put(partner.getId(), partner);
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
