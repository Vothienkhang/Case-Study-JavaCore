package UI;

import exception.DuplicateIdException;
import exception.NotFoundException;
import model.Product;
import service.ProductService;
import util.FileUtil;
import validation.ProductValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProductMenuHandler {
    private final Scanner sc;
    private final ProductService service = new ProductService();

    public ProductMenuHandler(Scanner sc) {
        this.sc = sc;
    }

    // Add product from Menu
    public void addProduct() {
        System.out.println("\n------Add Product------");

        String id;
        while (true) {
            System.out.println("Enter Product ID: ");
            id = sc.nextLine().trim();

            if (!ProductValidator.isValidId(id)) {
                System.out.println("Invalid ID format!");
                continue;
            }

            if (service.existsByID(id)) {
                System.out.println("Product ID is already in use!");
                continue;
            }
            break;
        }

        String name;
        while (true) {
            System.out.println("Enter Product Name: ");
            name = sc.nextLine().trim();

            if (!ProductValidator.isValidName(name)) {
                System.out.println("Invalid name format!");
                continue;
            }
            break;
        }

        double price;
        while (true) {
            try {
                System.out.println("Enter Product Price: ");
                price = Double.parseDouble(sc.nextLine());

                if (!ProductValidator.isValidPrice(price)) {
                    System.out.println("Price must be a positive number!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        int qty;
        while (true) {
            try {
                System.out.println("Enter Product Quantity: ");
                qty = Integer.parseInt(sc.nextLine());
                if (!ProductValidator.isValidQuantity(qty)) {
                    System.out.println("Quantity must be a positive number!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        try {
            service.add(new Product(id, name, price, qty));
            System.out.println("Product added successfully!");
        } catch (DuplicateIdException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllMenu() {
        List<Product> list = service.getAll();
        if (list.isEmpty()) {
            System.out.println("There is no products in the system!");
            return;
        }
        for (Product p : list) {
            System.out.println(p);
        }
    }

    public void searchMenu() {
        System.out.println("1. Search Product by ID");
        System.out.println("2. Search Product by name");

        int c;
        while (true) {
            try {
                System.out.println("Choose an option: ");
                c = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        if (c == 1) {
            System.out.println("Enter Product ID");
            String id = sc.nextLine().trim();
            System.out.println("\n---------------The result is ----------");

            try {
                System.out.println(service.findById(id));
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (c == 2) {
            System.out.println("Enter Product Name: ");
            String name = sc.nextLine().trim();
            System.out.println("\n---------------The result is ----------");
            List<Product> result = service.searchByName(name);

            if (result.isEmpty()) {
                System.out.println("There is no products in the system!");
            } else {
                for (Product p : result) {
                    System.out.println(p);
                }
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void updateProduct() {
        System.out.println("\n---------Update Product---------");

        // Step 1: enter product ID
        String id;
        while (true) {
            System.out.println("Enter Product ID to update: ");
            id = sc.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Product ID is empty!");
                continue;
            }

            if (!ProductValidator.isValidId(id)) {
                System.out.println("Invalid ID format! For example: PRD001");
                continue;
            }
            break;
        }

        // Step 2: if not exist --> throw error
        Product current;
        try {
            current = service.findById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Current Product: " + current);

        // Step 3: Input new name/price/qty
        String name;
        while (true) {
            System.out.println("Enter New Product Name: ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Product Name is required! ");
                continue;
            }
            if (!ProductValidator.isValidName(name)) {
                System.out.println("Invalid name format! ");
                continue;
            }
            break;
        }

        double price;
        while (true) {
            try {
                System.out.println("Enter New Price: ");
                price = Double.parseDouble(sc.nextLine().trim());

                if (!ProductValidator.isValidPrice(price)) {
                    System.out.println("Price must be a positive number!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        int qty;
        while (true) {
            try {
                System.out.println("Enter New Quantity: ");
                qty = Integer.parseInt(sc.nextLine().trim());

                if (!ProductValidator.isValidQuantity(qty)) {
                    System.out.println("Quantity must be a positive number!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        // Step 4: Call service update product
        try {
            service.update(new Product(id, name, price, qty));
            System.out.println("-----------Product updated successfully-----------!");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Step 5: Print result
        System.out.println("\n----------After Update Product-------------");
        showAllMenu();
    }

    public void deleteProduct() {
        System.out.println("\n-----------Delete Product-----------");

        // Step 1: Input product ID
        String id;
        while (true) {
            System.out.println("Enter Product ID to delete: ");
            id = sc.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Product ID is empty!");
                continue;
            }

            if (!ProductValidator.isValidId(id)) {
                System.out.println("Invalid ID format! For example: PRD001");
                continue;
            }
            break;
        }

        // Step 2: check existing
        Product current;
        try {
            current = service.findById(id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Found Product: " + current);

        // Step 3: confirm delete
        System.out.println("Are you sure to delete this product? (Y/N): ");
        String confirm = sc.nextLine().trim();

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete has been cancelled! ");
            return;
        }

        // Step 4: Gọi service delete product
        try {
            service.delete(id);
            System.out.println("Product has been deleted successfully!");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Step 5: Print result
        System.out.println("\n----------After delete Product-----------");
        showAllMenu();
    }

    public void sortMenu() {
        System.out.println("\n--------- Sort Product-------");
        System.out.println("1. Sort Product by name");
        System.out.println("2. Sort Product by price");

        int c;
        while (true) {
            try {
                System.out.print("Choose an sort option: ");
                c = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }

        if (c == 1) {
            service.sortByNameAsc();
            System.out.println("Sorted by name asc");
            showAllMenu();
        } else if (c == 2) {
            service.sortByPriceAsc();
            System.out.println("Sorted by price asc");
            showAllMenu();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    public void saveToFile() {
        System.out.println("Enter file path");
        String path = sc.nextLine().trim();

        try {
            FileUtil.writeProductsToCsv(path, service.getAll());
            System.out.println("Product saved successfully!");
        } catch (IOException e) {
            System.out.println("Save failed!");
        }
    }

    public void loadFromFile() {
            System.out.print("Enter file path: ");
            String path = sc.nextLine().trim();

            try {
                service.replaceAll(FileUtil.readProductsFromCsv(path));
                System.out.println("Product loaded successfully!");
            } catch (IOException e) {
                System.out.println("Load failed!");
            }
    }
}
