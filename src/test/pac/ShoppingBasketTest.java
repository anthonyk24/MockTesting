package pac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ShoppingBasketTest {

    private ShoppingBasket shoppingBasket;
    private ProductDAO dao;


    @BeforeEach
    void setUp() {
        shoppingBasket = new ShoppingBasket();
        dao = Mockito.mock(ProductDAO.class); // mock implementation of the interface pac.ProductDAO
    }


    @Test
    @DisplayName("should add product by id")
    void testAddById() {

        Mockito.when(dao.findById(1)).thenReturn(new Product(1, "ps5", 1000));
        shoppingBasket.setProductDAO(dao);
        shoppingBasket.addById(1, 1);

        boolean trueStatement = shoppingBasket.getTotalCost() > 0;
        Assertions.assertTrue(trueStatement);
        Assertions.assertTrue(shoppingBasket.toString().contains("ps5"));

    }


    @Test
    @DisplayName("should add product by name")
    void testAddByName() {

        Mockito.when(dao.findByName("ps5")).thenReturn(new Product(1, "ps5", 1000));
        shoppingBasket.setProductDAO(dao);
        shoppingBasket.addByName(1, "ps5");

        boolean trueStatement = shoppingBasket.getTotalCost() > 0;
        Assertions.assertTrue(trueStatement);

    }


    @Test
    @DisplayName("should get the total cost of the added products")
    void testTotalCost() {

        Mockito.when(dao.findByName("ps5")).thenReturn(new Product(1, "ps5", 1000));
        Mockito.when(dao.findByName("xbox")).thenReturn(new Product(2, "xbox", 2000));
        shoppingBasket.setProductDAO(dao);
        shoppingBasket.addByName(1, "xbox");
        shoppingBasket.addByName(3, "ps5");

        int expected = 5000;
        int actual = shoppingBasket.getTotalCost();
        Assertions.assertEquals(expected, actual);

    }


    @Test
    @DisplayName("should get the toString and the added products")
    void testToString() {

        Product p = new Product(1, "nintendo", 2500);
        Mockito.when(dao.findByName("nintendo")).thenReturn(p);
        shoppingBasket.setProductDAO(dao);
        shoppingBasket.addByName(1, "nintendo");

        boolean trueStatement = shoppingBasket.toString().contains("nintendo");
        String expected = "[[1*nintendo]]";
        String actual = shoppingBasket.toString();

        Assertions.assertTrue(trueStatement);
        Assertions.assertEquals(expected, actual);

    }


    @Test
    @DisplayName("should remove all the added products from the shoppingBasket")
    void testClearCart() {

        Product p = new Product(1, "blackbarry", 2500);
        Mockito.when(dao.findByName("blackbarry")).thenReturn(p);
        shoppingBasket.setProductDAO(dao);
        shoppingBasket.addByName(1, "blackbarry");
        shoppingBasket.clear();

        String expected = "[]";
        String actual = shoppingBasket.toString();
        boolean trueStatement = shoppingBasket.getTotalCost() == 0;

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(trueStatement);
    }
}