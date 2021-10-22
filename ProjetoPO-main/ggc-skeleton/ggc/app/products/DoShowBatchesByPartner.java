package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.exception.BadEntryException;
import ggc.app.exception.UnknownPartnerKeyException;

import java.util.*;

import ggc.core.Batch;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    //FIXME maybe add command fields
  }

  @Override
  public final void execute() throws CommandException {
    String partnerId = stringField("partnerId");
    try{
    ArrayList<Batch> batches = _receiver.getBatchesPartner(partnerId);
    for(Batch b: batches){
      _display.addLine(b.toString());
    }
    _display.display();
    } catch (BadEntryException e){
    throw new UnknownPartnerKeyException(partnerId);//TODO: handle exception
    }
    //FIXME implement command
  }

}
