package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import exception.DuplicateIdException;
import exception.NotFoundException;

import model.Product;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    // Check product existing
    public boolean existsByID(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // Add product
    public void add(Product p) throws DuplicateIdException {
        if (existsByID(p.getId())) {
            throw new DuplicateIdException("Duplicate ID: " + p.getId());
        }
        products.add(p);
    }

    // Find product
    public Product findById(String id) throws NotFoundException{
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new NotFoundException("Product not found: " + id);
    }

    // Search product
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        if (name == null || name.isEmpty()) {
            return result;
        }
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    // sort product by name
    public void sortByNameAsc() {
        products.sort(Comparator.comparing(p -> p.getName().toLowerCase()));
    }

    // sort product by price
    public void sortByPriceAsc() {
        products.sort(Comparator.comparingDouble(p -> p.getPrice()));
    }

    // Update product
    public void update(Product updated) throws NotFoundException {
        Product current = findById(updated.getId());
        current.setName(updated.getName());
        current.setPrice(updated.getPrice());
        current.setQuantity(updated.getQuantity());
    }

    // Delete product by ID
    public void delete(String id) throws NotFoundException {
        Product p = findById(id);
        products.remove(p);
    }

    //replace all product
    public void replaceAll(List<Product> newList) {
        products.clear();
        products.addAll(newList);
    }

    public List<Product> getAll() {
        return new ArrayList<>(products);
    }
}
