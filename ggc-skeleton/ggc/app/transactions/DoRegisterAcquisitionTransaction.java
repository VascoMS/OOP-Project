package ggc.app.transactions;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId;
    String productId;
    double productPrice;
    int productAmount;
    Partner partner;
    
    partnerId = Form.requestString(Message.requestPartnerKey());
    try{ 
    partner = _receiver.getPartner(partnerId);
    } catch(CoreUnknownPartnerKeyException pa){
      throw new UnknownPartnerKeyException(partnerId);
    }
    productId = Form.requestString(Message.requestProductKey());
    if(!_receiver.hasProduct(productId)){
        String hasRecipe = Form.requestString(Message.requestAddRecipe());
        if(hasRecipe.equals("yes")){
          List<Product> products = new ArrayList<>();
          List<Integer> quantities = new ArrayList<>();
          int numberComponents = Form.requestInteger(Message.requestNumberOfComponents());
          double alpha = Form.requestReal(Message.requestAlpha());
          for(int i = 0; i < numberComponents; i++){
            String componentProductId = Form.requestString(Message.requestProductKey());
            int componentProductAmount = Form.requestInteger(Message.requestAmount());
            try{
              products.add(_receiver.getProduct(componentProductId));
              quantities.add(componentProductAmount);
            } catch(CoreUnknownProductKeyException pr){
              throw new UnknownProductKeyException(componentProductId);
              } 
          }
          _receiver.registerAggregateProduct(productId, alpha, products, quantities);
        }
        else{
          _receiver.registerSimpleProduct(productId);
        }
      }
    productPrice = Form.requestReal(Message.requestPrice());
    productAmount = Form.requestInteger(Message.requestAmount());
    _receiver.registerAcquisition(partner, productId, productPrice, productAmount);
  }

}
