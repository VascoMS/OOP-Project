package ggc.app.transactions;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
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
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("amount", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    double productPrice=realField("price");
    int productAmount = integerField("amount");
    List<String> products = new ArrayList<>();
    List<Integer> quantities = new ArrayList<>();
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");
    int numberComponents;
    if(!_receiver.hasProduct(productId)){
      String hasRecipe = Form.requestString(Message.requestAddRecipe());
      if(hasRecipe.equals("s") || hasRecipe.equals("S")){
        numberComponents = Form.requestInteger(Message.requestNumberOfComponents());
        double alpha = Form.requestReal(Message.requestAlpha());
        int i = 0;
        while(i++ < numberComponents){
          String componentProductId = Form.requestString(Message.requestProductKey());
          int componentProductAmount = Form.requestInteger(Message.requestAmount());
          products.add(componentProductId);
          quantities.add(componentProductAmount);  
        }
        try{
          _receiver.registerAcquisition(partnerId, productId, productPrice, productAmount, products, quantities, alpha, numberComponents);
          return;
        } catch(CoreUnknownPartnerKeyException ex){
            throw new UnknownPartnerKeyException(partnerId);
        } catch(CoreUnknownProductKeyException ex){
            throw new UnknownProductKeyException(ex.getId());
        }
      }
    }
      try{
      _receiver.registerAcquisition(partnerId, productId, productPrice, productAmount);
      } catch(CoreUnknownPartnerKeyException ex){
          throw new UnknownPartnerKeyException(partnerId);
      }

}
}
      
