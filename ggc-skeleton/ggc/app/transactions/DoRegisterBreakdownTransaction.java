package ggc.app.transactions;

import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes
import ggc.core.exception.CoreUnavailableProductException;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import ggc.core.exception.CoreUnknownProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    String productId = stringField("productId");
    int amount = integerField("quantity");
    try {
      _receiver.registerBreakdownSale(partnerId, productId, amount);
    } catch (CoreUnknownPartnerKeyException pa) {
      throw new UnknownPartnerKeyException(partnerId);
    } catch(CoreUnknownProductKeyException pr){
      throw new UnknownProductKeyException(productId);
    } catch(CoreUnavailableProductException apr){
      throw new UnavailableProductException(productId, amount, (int) apr.getAvailable());
    }
    
  }

}
