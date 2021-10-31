package ggc.app.products;

import java.util.List;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    try{
    List<Batch> batches = _receiver.getBatchesPartner(partnerId);
    for(Batch b: batches){
      _display.addLine(b.toString());
    }
    _display.display();
    } catch (CoreUnknownPartnerKeyException e){
    throw new UnknownPartnerKeyException(partnerId);
    }
  }

}
