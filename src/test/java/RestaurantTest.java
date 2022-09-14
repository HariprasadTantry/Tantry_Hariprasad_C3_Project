import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    private void initializeRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }
    private void addingMenu() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        initializeRestaurant();
        LocalTime localTime = LocalTime.parse("11:00:00");
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(localTime).when(restaurant1).getCurrentTime();
        assertTrue(restaurant1.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        initializeRestaurant();
        LocalTime localTime = LocalTime.parse("06:00:00");
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(localTime).when(restaurant1).getCurrentTime();
        assertFalse(restaurant1.isRestaurantOpen());
    }

    @Test
    public void order_value_positive_test(){
        initializeRestaurant();
        addingMenu();
        restaurant.addToMenu("Fried Rice",105);
        restaurant.addToMenu("Vegetable Salad", 230);
        String expectedResult = "454";
        List<String> listOfItemNames = new ArrayList<>();
        listOfItemNames.add("Fried Rice");
        listOfItemNames.add("Sweet corn soup");
        listOfItemNames.add("Vegetable Salad");
        String actualResult = restaurant.getOrderValue(listOfItemNames);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void order_value_test_no_items(){
        initializeRestaurant();
        addingMenu();
        String expectedResult = "0";
        List<String> listOfItemNames = new ArrayList<>();
        String actualResult = restaurant.getOrderValue(listOfItemNames);
        assertEquals(expectedResult,actualResult);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        initializeRestaurant();
        addingMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        initializeRestaurant();
        addingMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        initializeRestaurant();
        addingMenu();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}