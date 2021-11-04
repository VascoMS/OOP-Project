package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownTransactionKeyException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("saleID", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int saleID = integerField("saleID");
    try {
      if(_receiver.getTransaction(saleID));
    } catch (CoreUnknownTransactionKeyException e) {
      throw new UnknownTransactionKeyException(saleID);
    }

  }

}
