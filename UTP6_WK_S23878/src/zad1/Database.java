package zad1;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String url;
    private TravelData travelData;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Tworzenie tabeli w bazie danych
            String createTableQuery = "CREATE TABLE IF NOT EXISTS offers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "location VARCHAR(255)," +
                    "departure_date DATE," +
                    "return_date DATE," +
                    "place VARCHAR(255)," +
                    "price DECIMAL(10,2)," +
                    "currency VARCHAR(10)" +
                    ")";
            statement.executeUpdate(createTableQuery);

            // Wczytywanie ofert z plików i zapisywanie do bazy danych
            List<String> offerDescriptions = travelData.getOffersDescriptionsList("pl_PL", "yyyy-MM-dd");
            for (String offer : offerDescriptions) {
                String[] fields = offer.split(" ");
                if (fields.length == 6) {
                    String location = fields[0];
                    String departureDate = fields[1];
                    String returnDate = fields[2];
                    String place = fields[3];
                    String price = fields[4].replaceAll(",", "");
                    String currency = fields[5];

                    String insertQuery = String.format("INSERT INTO offers (location, departure_date, return_date, place, price, currency) " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", location, departureDate, returnDate, place, price, currency);
                    statement.executeUpdate(insertQuery);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showGui() {
        JFrame frame = new JFrame("Travel Offers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pobranie ofert z bazy danych
        List<String> offers = getOffersFromDb();

        // Konwersja listy ofert na tablicę dwuwymiarową
        String[][] data = new String[offers.size()][6];
        for (int i = 0; i < offers.size(); i++) {
            String[] fields = offers.get(i).split(" ");
            if (fields.length == 6) {
                data[i] = fields;
            }
        }

        // Tworzenie tabeli JTable
        String[] columnNames = {"Location", "Departure Date", "Return Date", "Place", "Price", "Currency"};
        JTable table = new JTable(data, columnNames);

        // Dodanie tabeli do panelu z paskiem przewijania
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    private List<String> getOffersFromDb() {
        List<String> offers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            String selectQuery = "SELECT location, departure_date, return_date, place, price, currency FROM offers";
            var resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String location = resultSet.getString("location");
                String departureDate = resultSet.getDate("departure_date").toString();
                String returnDate = resultSet.getDate("return_date").toString();
                String place = resultSet.getString("place");
                String price = resultSet.getBigDecimal("price").toString();
                String currency = resultSet.getString("currency");

                String offer = String.format("%s %s %s %s %s %s",
                        location, departureDate, returnDate, place, price, currency);
                offers.add(offer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return offers;
    }
}
