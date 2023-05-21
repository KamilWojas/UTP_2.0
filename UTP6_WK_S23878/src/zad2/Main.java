/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad2;


import java.beans.PropertyVetoException;

public class Main {
  public static void main(String[] args) {

    Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
    System.out.println(purch);

    purch.addPropertyChangeListener(evt -> {
      System.out.println("Change value of: " + evt.getPropertyName() +
              " from: " + evt.getOldValue() + " to: " + evt.getNewValue());
    });

    purch.addVetoableChangeListener(evt -> {
      if (evt.getPropertyName().equals("price")) {
        Double newPrice = (Double) evt.getNewValue();
        if (newPrice < 1000.0) {
          throw new PropertyVetoException("Price change to: " + newPrice + " not allowed", evt);
        }
      }
    });


    try {
      purch.setData("w promocji");
      purch.setPrice(2000.00);
      System.out.println(purch);

      purch.setPrice(500.00);

    } catch (PropertyVetoException exc) {
      System.out.println(exc.getMessage());
    }
    System.out.println(purch);

  }
}
