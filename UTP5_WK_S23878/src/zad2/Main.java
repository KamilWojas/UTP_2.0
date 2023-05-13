/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad2;


import javax.swing.*;
import java.awt.*;

public class Main {

  public static void main(String[] args) {
    Font font = new Font("Arial", Font.PLAIN, 12);
    UIManager.put("Button.font", font);
    UIManager.put("Label.font", font);
    UIManager.put("TextField.font", font);

    // Utworzenie okna i panelu
    JFrame frame = new JFrame("MyApp");
    JPanel panel = new JPanel();

    // Ustawienie czcionki dla przycisku
    JButton button = new JButton("Click me");

    // Dodanie przycisku do panelu
    panel.add(button);

    // Dodanie panelu do okna
    frame.getContentPane().add(panel);

    // Wyświetlenie okna
    frame.pack();
    frame.setVisible(true);
  }
}
