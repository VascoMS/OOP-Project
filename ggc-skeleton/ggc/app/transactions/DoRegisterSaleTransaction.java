package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.InvalidDateException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Batch;
import ggc.core.Date;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    //FIXME maybe add command fields 
  }

  @Override
  public final void execute() throws CommandException {
    String productId;
    Product product;
    String partnerId;
    Partner partner;
    Date deadline;
    int quantity;
    List<Batch> products;

    partnerId = Form.requestString(Message.requestPartnerKey());
    try{ 
    partner = _receiver.getPartner(partnerId);
    } catch(CoreUnknownPartnerKeyException pa){
      throw new UnknownPartnerKeyException(partnerId);
    }

    deadline = new Date(Form.requestInteger(Message.requestPaymentDeadline()));
    if(deadline.difference(_receiver.getDateWarehouse())<0)
      throw new InvalidDateException(deadline.getDays());

    productId = Form.requestString(Message.requestProductKey());
    try{
      product = _receiver.getProduct(productId);
      quantity = Form.requestInteger(Message.requestAmount());
      if(product.getTotalStock() - quantity < 0)
        throw new UnavailableProductException(productId, quantity, (int)product.getTotalStock());
      products = new ArrayList<>(product.getBatchesSortedByPrice());
      while(quantity != 0){
        
      }
    } catch(CoreUnknownProductKeyException pr){
      throw new UnknownProductKeyException(productId);

    }
    //FIXME implement command
  }

}
