import Model.Accounts;
import View.AccountsMenu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountsMenuTest {

  Accounts account = new Accounts(" ", 0.00, " ", 0);
  AccountsMenu menu = new AccountsMenu(account);

  @Test
  public void validateDateTest_True(){
    String dateFormat ="30/09/2019";
    assertTrue(menu.validateDate(dateFormat ));
    dateFormat ="6/9/2019";
    assertTrue(menu.validateDate(dateFormat ));
  }

  @Test
  public void validateDateTest_False(){
    String dateFormat ="06/30/2019";
    assertFalse(menu.validateDate(dateFormat ));
  }
}
