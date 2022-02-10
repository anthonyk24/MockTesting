package pac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ShoppingBasketManualTesting {

    @Test
    @DisplayName("supposed to add a ps5 to the shopping cart by name")
    void testAddByIdManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        shoppingBasket.addById(2, 1);

        boolean trueStatement = shoppingBasket.toString().contains("ps5");
        Assertions.assertTrue(trueStatement);

    }


    @Test
    @DisplayName("supposed to add an xbox to the shopping cart by id")
    void testAddByNameManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        shoppingBasket.addByName(2, "xbox");

        boolean checkIfToStringContainsXbox = shoppingBasket.toString().contains("xbox");
        boolean checkIfPriceIsGreaterThan0 = shoppingBasket.getTotalCost() > 0;

        Assertions.assertTrue(checkIfToStringContainsXbox);
        Assertions.assertTrue(checkIfPriceIsGreaterThan0);

    }


    @Test
    @DisplayName("supposed to get the total cost of certain amount of products")
    void testTotalCostManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        shoppingBasket.addByName(10, "xbox");
        shoppingBasket.addById(2, 1);

        int expected = shoppingBasket.getTotalCost();
        int actual = 19000;

        Assertions.assertEquals(expected, actual);

    }


    @Test
    @DisplayName("test is supposed to compare toString to the added product")
    void testToStringManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        shoppingBasket.addByName(1, "xbox");

        String expected = "[[1*xbox]]";
        String actual = shoppingBasket.toString();
        boolean trueStatement = shoppingBasket.getTotalCost() > 0;

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(trueStatement);

    }


    @Test
    @DisplayName("test is supposed to test the price if shopping cart is empty")
    void testIfShoppingCartEmptyManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());

        boolean trueStatement = shoppingBasket.toString().equals("[]");
        int expected = shoppingBasket.getTotalCost();
        int actual = 0;

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(trueStatement);

    }

    @Test
    @DisplayName("test is supposed to remove all items from the cart")
    void testClearManualTesting() {

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());

        // adds
        shoppingBasket.addByName(1, "ps5");
        shoppingBasket.addByName(1, "xbox");

        // clears cart
        shoppingBasket.clear();

        boolean falseStatement = shoppingBasket.toString().contains("ps5");
        boolean trueStatement = shoppingBasket.toString().equals("[]");

        Assertions.assertFalse(falseStatement);
        Assertions.assertTrue(trueStatement);

    }

    @Test
    void testAddByNonExistent() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        Assertions.assertThrows(IllegalArgumentException.class, () -> shoppingBasket.addById(1, 3));
    }


    /** I have noticed that shoppingBasket allows 0 and negatives counts */
    @Test
    void testAddByWrongCóunt() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProductDAO(new MockDAO());
        Assertions.assertThrows(IllegalProductCountException.class, () -> shoppingBasket.addById(0, 1));
    }
}


class MockDAO implements ProductDAO {

    public List<Product> productList = new ArrayList<>();

    // creates two products
    public Product playstationFive = new Product(1, "ps5", 2000);
    public Product xboxOne = new Product(2, "xbox", 1500);


    // adds the products to a List
    public void addToList() {
        productList.add(playstationFive);
        productList.add(xboxOne);
    }

    @Override
    public Product findById(int id) {
        addToList();
        for (Product p : productList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Product findByName(String name) {
        addToList();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getName().equals(name)) {
                return productList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        addToList();
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(productList.get(i).getName());
        }
        return productList;
    }

    @Override
    public List<Product> findCheaperThan(int lowprice) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getPrice() < lowprice) {
                System.out.println(productList.get(i).getName());
                return productList;
            }
        }
        return null;
    }

    @Override
    public boolean isInStock(int id) {
        addToList();
        for (Product p : productList) {
            if (p.getId() == id) {
                System.out.println(p.getName() + " is in stock");
                return true;
            }
        }
        System.out.println("not in stock sorry");
        return false;
    }

    @Override
    public boolean delete(int id) {
        addToList();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == id) {
                productList.remove(i);
                System.out.println(productList);
                return true;
            }
        }
        System.out.println("product could not be found no products will be deleted :)");
        return false;
    }

    @Override
    public void raiseAllPrices(double percent) {
        for (int i = 0; i < productList.size(); i++) {
            int value = (int) Math.round(percent);
            int newPrice = value * productList.get(i).getPrice();
            productList.get(i).setPrice(newPrice);
            System.out.println(productList.get(i).getPrice());
        }
    }
}