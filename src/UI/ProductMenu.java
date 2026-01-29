package UI;

import java.util.Scanner;

public class ProductMenu {
    private final Scanner sc;
    private final ProductMenuHandler handler;

    public ProductMenu() {
        this.sc = new Scanner(System.in);
        this.handler = new ProductMenuHandler(sc);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 0:
                    System.out.println("Good Bye!");
                    return;
                case 1:
                    handler.addProduct();
                    break;
                case 2:
                    handler.showAllMenu();
                    break;
                case 3:
                    handler.searchMenu();
                    break;
                case 4:
                    handler.sortMenu();
                    break;
                case 5:
                    handler.saveToFile();
                    break;
                case 6:
                    handler.loadFromFile();
                    break;
                case 7:
                    handler.updateProduct();
                    break;
                case 8:
                    handler.deleteProduct();
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== PRODUCT MANAGEMENT ===");
        System.out.println("0. Exit");
        System.out.println("1. Add product");
        System.out.println("2. Show all products");
        System.out.println("3. Search");
        System.out.println("4. Sort");
        System.out.println("5. Save to file");
        System.out.println("6. Load from file");
        System.out.println("7. Update product");
        System.out.println("8. Delete product");
    }

    private int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }
}
