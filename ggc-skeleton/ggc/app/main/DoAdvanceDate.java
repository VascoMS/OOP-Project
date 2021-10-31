package ggc.app.main;

import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;
import ggc.core.exception.CoreInvalidDateException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("numberOfDays", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    Integer i = integerField("numberOfDays");
    try {
      _receiver.incrementDate(i);
    } catch (CoreInvalidDateException e) {
      throw new InvalidDateException(i);
    }
  }

}
