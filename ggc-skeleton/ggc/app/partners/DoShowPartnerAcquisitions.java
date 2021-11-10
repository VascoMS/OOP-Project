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
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
        _display.popup(_receiver.getPartnerAcquisitions(stringField("partnerId")));
    } catch (CoreUnknownPartnerKeyException e) {
      throw new UnknownPartnerKeyException(stringField("partnerId"));
    }
  }

}
