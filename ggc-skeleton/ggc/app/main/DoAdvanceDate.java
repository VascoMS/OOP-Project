package ggc.app.main;

//FIXME import classes
import ggc.core.WarehouseManager;
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
    Integer i = integerField("numberOfDays");//FIXME implement command
    _receiver.incrementDate(i);
  }

}
