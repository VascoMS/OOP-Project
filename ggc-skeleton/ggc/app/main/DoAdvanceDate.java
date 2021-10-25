package ggc.app.main;

import ggc.app.exception.InvalidDateException;
//FIXME import classes
import ggc.core.WarehouseManager;
import ggc.core.exception.BadEntryException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("numberOfDays", Message.requestDaysToAdvance());//FIXME add command fields
  }

  @Override
  public final void execute() throws CommandException {
    Integer i = integerField("numberOfDays");
    try {
      _receiver.incrementDate(i);
    } catch (BadEntryException e) {
      throw new InvalidDateException(i);//TODO: handle exception
    }//FIXME implement command
  }

}
