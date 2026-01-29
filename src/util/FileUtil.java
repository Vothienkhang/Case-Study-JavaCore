package util;

import model.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void writeProductsToCsv(String path, List<Product> products) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {

            //write header
            bw.write("id, name, price, quantity");
            bw.newLine();

            //write data
            for (Product p : products) {
                String id = safe(p.getId());
                String name = safe(p.getName()).replace(", ", "_"); // avoid breaking CSV split
                String price = String.valueOf(p.getPrice());
                String qty = String.valueOf(p.getQuantity());

                bw.write(id + "," + name + "," + price + "," + qty);
                bw.newLine();
            }
        }
    }

    public static List<Product> readProductsFromCsv(String path) throws IOException {
        List<Product> list = new ArrayList<>();
        File file = new File(path);

        // If file does not exist --> return empty list (no crash)
        if (!file.exists()) { return list; }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line = br.readLine(); // could be header or first row
            if (line == null) { return list; }

            // if first line is header --> skip it
            if (isHeader(line)) {
                line = br.readLine();
            }

            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Product p = parseProductLine(line);
                    if (p != null) {
                        list.add(p);
                    }
                }
                line = br.readLine();
            }
        }
        return list;
    }

    // ------------------------------------------------------

    private static Product parseProductLine(String line) {
        // split into 4 parts only
        String[] parts = line.split(",", -1);
        if (parts.length < 4) { return null; }

        String id = parts[0].trim();
        String name = parts[1].trim();

        double price;
        int qty;
        try {
            price = Double.parseDouble(parts[2].trim());
            qty = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        return new Product(id, name, price, qty);
    }

    private static boolean isHeader(String line) {
        String s = line.trim().toLowerCase();
        return s.startsWith("id") && s.contains("name") && s.contains("price") && s.contains("quantity");
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }
}
