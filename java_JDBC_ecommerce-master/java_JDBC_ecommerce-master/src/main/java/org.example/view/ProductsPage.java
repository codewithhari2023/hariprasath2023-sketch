package org.example.view;

import org.example.models.Product;
import org.example.util.StringUtils;

import java.util.ArrayList;

import static org.example.util.Utils.println;

public class ProductsPage{
    public void printProducts(ArrayList<Product> catProducts) {
        for(Product product:catProducts){
            System.out.println(product.getId() + ". " + product.getTitle() + " - Rs." + product.getPrice());
        }

    }

    public void printAllProducts(ArrayList<Product> allProducts) {

        for(Product product:allProducts){
            System.out.println(product.getId() + ". " + product.getTitle() + " - Rs." + product.getPrice());
        }
        println(StringUtils.BACK_OPTION);
        println(StringUtils.BACK_MESS);
    }
    public void printSuccess() {
        try {
          Thread.sleep(1000);
            println(StringUtils.CART_SUCCESS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
