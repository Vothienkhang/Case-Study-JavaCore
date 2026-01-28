package UI;

import model.Product;
import service.ProductService;

import java.util.Scanner;

public class ProductMenuHandler {
    private final Scanner sc;
    private final ProductService service = new ProductService();

    public ProductMenuHandler(Scanner sc) {
        this.sc = sc;
    }

    public void addProduct() {
        System.out.println("\n------Add Product------");

        String id;
        while (true) {
            id = readLine()
        }
    }
}
