package pac;

import pac.Product;

import java.util.List;

public interface ProductDAO {


    Product findById(int id);


    Product findByName(String name);


    List<Product> findAll();


    List<Product> findCheaperThan(int lowprice);


    boolean isInStock(int id);



    boolean delete(int id);


    void raiseAllPrices(double percent);
}
