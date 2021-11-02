package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Acquisition;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreUnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partnerId", Message.requestPartnerKey());//FIXME add command fields
  }

  @Override
  public void execute() throws CommandException {
    try {
      for(Acquisition acquisition: _receiver.getPartner(stringField("partnerId")).getAcquisitions())
        _display.addLine(acquisition);
      _display.display();
    } catch (CoreUnknownPartnerKeyException e) {
      throw new UnknownPartnerKeyException(stringField("partnerId"));
    }
  }

}
