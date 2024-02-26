import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface ProductOperation {
    void buy(double amount);

    void sell(double amount);

    double getQuantity();

    String getName();
}

abstract class Inventory implements ProductOperation {
    protected double quantity;

    public Inventory(double initialQuantity) {
        this.quantity = initialQuantity;
    }

    @Override
    public void buy(double amount) {
        quantity += amount;
        System.out.println("Bought: " + amount + " units of " + getName());
        displayQuantity();
    }

    @Override
    public void sell(double amount) {
        if (amount <= quantity) {
            quantity -= amount;
            System.out.println("Sold: " + amount + " units of " + getName());
        } else {
            System.out.println("Insufficient quantity of " + getName());
        }
        displayQuantity();
    }

    @Override
    public double getQuantity() {
        return quantity;
    }

    abstract void displayQuantity();
}

class Product extends Inventory {
    private String productName;
    private double price;

    public Product(String productName, double price, double initialQuantity) {
        super(initialQuantity);
        this.productName = productName;
        this.price = price;
    }

    @Override
    void displayQuantity() {
        System.out.println(getName() + " Quantity: " + getQuantity());
    }

    @Override
    public String getName() {
        return productName;
    }

    public double getTotalValue() {
        return quantity * price;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<ProductOperation> products = new ArrayList<>();

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Buy/Sell Product");
            System.out.println("3. Search by Keyword");
            System.out.println("4. Exit");
            System.out.print("Choose operation (1/2/3/4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.next();
                    System.out.print("Enter price per unit: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter initial quantity: ");
                    double quantity = scanner.nextDouble();
                    products.add(new Product(name, price, quantity));
                    break;
                case 2:
                    System.out.println("Available Products:");
                    for (ProductOperation product : products) {
                        System.out.println(product.getName());
                    }
                    System.out.print("Choose product: ");
                    String selectedProduct = scanner.next();
                    ProductOperation chosenProduct = null;
                    for (ProductOperation product : products) {
                        if (product.getName().equalsIgnoreCase(selectedProduct)) {
                            chosenProduct = product;
                            break;
                        }
                    }
                    if (chosenProduct != null) {
                        System.out.println("Product Quantity: " + chosenProduct.getQuantity());
                        System.out.println("1. Buy");
                        System.out.println("2. Sell");
                        System.out.print("Choose operation (1/2): ");
                        int operation = scanner.nextInt();
                        System.out.print("Enter quantity: ");
                        double amount = scanner.nextDouble();
                        if (operation == 1) {
                            chosenProduct.buy(amount);
                        } else if (operation == 2) {
                            chosenProduct.sell(amount);
                        } else {
                            System.out.println("Invalid choice");
                        }
                    } else {
                        System.out.println("Product not found");
                    }
                    break;
                case 3:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.next().toLowerCase();
                    System.out.println("Search results:");
                    for (ProductOperation product : products) {
                        if (product.getName().toLowerCase().contains(keyword)) {
                            System.out.println(product.getName());
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
