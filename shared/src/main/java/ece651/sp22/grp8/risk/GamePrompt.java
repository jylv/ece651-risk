package ece651.sp22.grp8.risk;

import java.io.PrintWriter;
import java.util.List;

/**
 * This class handles the input and output for the factor
 * program.  Specifically, it reads number from a specified input source
 * until it indicates end of input, and for each number,
 * factors it (using a provided Factorer) and writes the result
 * to the specified output.
 */
public class GamePrompt {
  /**
   * This is the greeting message that is printed at the start
   * of processing a request. Change this to try out the CI pipeline!
   */

//  public static String HOST = "localhost";
//  public static String HOST = "vcm-25567.vm.duke.edu";
  public static String HOST = "vcm-25264.vm.duke.edu";

  public static int PORT = 8887;

  public static String TITLE = "GRP 8 RISK GAME";

  public static String GREETING = "Hello, welcome to group 8's RISK game!\n";

  public static String OK = "ok!\n";

  public static String LOGIN = "login\n";

  public static String BADLOGIN = "Your username or password is not correct, try another one.\n";

  public static String ISLOGIN = "This user has already logged in, try another one.\n";

  public static String REGISTER = "register\n";

  public static String BADREGISTER = "username or password can't be null!\n";

  public static String REGISTED = "This username has already been registered.\n";

  public static String NEWGAME = "Apply for my game ID\n";

  public static String REJOIN = "rejoin thie game\n";
  /**
   * This is the prompt that is printed to ask the user for each number
   * to factor.
   */
  public static String LOSE = "Oops! you lose the game.\n";

  public static String WIN = "Congratulations! you win!\n";

  public static String LOSECHOICE = "Would you like to (C)ontinue to watch the game?\n" +
          "Or you can (D)isconnect with the server\n";
  /**
   * This is the message that is printed when the user inputs
   * something that cannot be converted to an integer.
   */
  public static String GOODBYE = "Bye, have a good day!\n";
  /**
   * This is the message that is printed when the user enters a number
   * that cannot be factored, namely a number that is 0 or negative.
   */
  public static String UNRECOGNIZED = "I don't understand.Please enter a valid input.\n";

  public static String WATCHGREETING = "You are welcomed to watch the rest of the game.\n";


  public static String BACKTOGALLERY = "back to gallery";

  //for move
  public static String NOT_BELONG="The source territory do not belong to you!\n";

  public static String DEST_NOT_BELONG="The dest territory doesn't belong to you!\n";

  public static String UNIT_NOT_ENOUGH_MOVE="You don't have enough units to move!\n";

  public static String CANNOT_REACH_DEST="Can not reach the destination!\n";

  //for attack
  public static String DEST_IS_BELONG="The dest territory is yours!\n";

  public static String CANNOT_ATTACK ="The dest you choose is not the neighbor\n";

  public static String UNIT_NOT_ENOUGH_ATTACK="You don't have enough units to attack!\n";

  public static String START_NOT_BELONG="The start territory doesn't belong to you!\n";

  public static String FOOD_NOT_ENOUGH="Oops! You don't have enough food right now!\n";

  public static String TECH_NOT_ENOUGH="Oops! You don't have enough tech resource right now!\n";

  public static String TECH_LEVEL_NOT_ENOUGH="Oops! You don't have enough tech level right now!\n";

  public static String TECH_LEVEL_MAX="Oops! You are in the highest tech level and can't upgrade!\n";

  public static String UNIT_NOT_EXIST="This kind of unit do not exits!\n";

  public static String UNIT_NOT_ENOUGH="This kind of unit do not enough!\n";

  public static String UNIT_CANNOT_UPDATE="This unit cannot update anymore.\n";

  public static String DIFFERENCE_WRONG="The target level should be larger than original level.\n";

  public static String HAS_UPDATE="You have updated in this turn.\n";

  public static String UNIT_SHOULD_POSITIVE="The number of unit cannot be 0 or a negative number.\n";

  public static String LEVEL_NOT_EXIST="The level is not exists.\n";

  //for client
  //for initialzie units
  public static String LACK_UNITS = "You don't have enough units to initialize this territory!\n";

  public static String INITIAL_END_EARLY = "You remain 0 units or only one\nterritory, system automatically\nallocates units!\n";

  public static String INVALIDINPUT = "Please enter a number!\n";


  public GamePrompt() {
  }

}
