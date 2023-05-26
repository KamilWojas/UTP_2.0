package zad1;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {
    private File dataDir;

    public TravelData(File dataDir) {
        this.dataDir = dataDir;
    }

    public List<String> getOffersDescriptionsList(String locale, String dateFormat) {
        List<String> offersDescriptions = new ArrayList<>();

        File[] files = dataDir.listFiles();
        if (files != null) {
            for (File file : files) {
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] fields = line.split("\t");
                        if (fields.length == 7) {
                            String location = fields[0];
                            String country = fields[1];
                            String departureDate = formatDate(fields[2], dateFormat);
                            String returnDate = formatDate(fields[3], dateFormat);
                            String place = fields[4];
                            String price = formatPrice(fields[5]);
                            String currency = fields[6];

                            Locale localeObj = getLocaleFromCode(locale);
                            ResourceBundle bundle = ResourceBundle.getBundle("Messages", localeObj);

                            String offerDescription = String.format(bundle.getString("offer.format"),
                                    country, departureDate, returnDate, place, price, currency);

                            offersDescriptions.add(offerDescription);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return offersDescriptions;
    }

    private String formatDate(String date, String dateFormat) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = inputFormat.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat);
            return outputFormat.format(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private String formatPrice(String price) {
        try {
            double amount = Double.parseDouble(price.replace(",", "."));
            NumberFormat format = NumberFormat.getNumberInstance();
            return format.format(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return price;
    }

    private Locale getLocaleFromCode(String code) {
        String[] parts = code.split("_");
        if (parts.length == 2) {
            return new Locale(parts[0], parts[1]);
        }
        return Locale.getDefault();
    }
}
