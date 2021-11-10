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
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnavailableProductException;
//FIXME import classes
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addIntegerField("deadline", Message.requestPaymentDeadline());
    addStringField("productId", Message.requestProductKey());
    addIntegerField("amount", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    String productId = stringField("productId");
    String partnerId = stringField("partnerId");
    int deadline = integerField("deadline");
    int amount = integerField("amount");

    try {
      _receiver.registerSaleByCredit(partnerId, productId, deadline, amount);
    } catch (CoreUnknownPartnerKeyException pa) {
      throw new UnknownPartnerKeyException(partnerId);
    } catch(CoreUnknownProductKeyException pr){
      throw new UnknownProductKeyException(productId);
    } catch(CoreUnavailableProductException apr){
      throw new UnavailableProductException(productId, amount, (int) apr.getAvailable());
    }

    

}
}
