package karat.resturanant;

/*
We are developing a restaurant order management system that tracks orders, menu items, and calculates statistics.

The program includes three classes: `MenuItem`, `Order`, and `OrderHistory`.

Classes:
* The `MenuItem` class represents an item from the restaurant menu.
* The `Order` class holds information about a single customer order.
* The `OrderHistory` class manages all orders and provides analytical methods.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Please take as much time as necessary, and feel free to run the code.
1-2) The test for OrderHistory is not passing due to a bug in the code. Make the necessary changes to OrderHistory to fix the bug.
*/

/*
2) We want to add a new function called "getPeakHour" to the OrderHistory class.
This function analyzes all orders and returns the hour of the day (0-23) when the most orders
were placed, along with the count of orders during that hour.

For example, if orders were placed at:
- 12:30 (hour 12)
- 13:15 (hour 13)
- 12:45 (hour 12)
- 18:20 (hour 18)
- 12:10 (hour 12)

The function should return [12, 3] because hour 12 had the most orders (3 orders).

Time format is "HH:MM" in 24-hour format.

To assist you in testing this new function, we have provided the testGetPeakHour function.
*/

import java.util.*;
import java.util.stream.Collectors;

import org.junit.*;

class MenuItem {
    /** Data about a menu item. */
    String itemId;
    String name;
    String category;    // e.g., "Appetizer", "Main Course", "Dessert", "Beverage"
    double price;

    MenuItem(String itemId, String name, String category, double price) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        MenuItem item = (MenuItem) other;
        return itemId.equals(item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}

class Order {
    /** Data about a single customer order. */
    String orderId;
    List<MenuItem> items;
    String orderTime;   // Format: "HH:MM"
    String orderDate;   // Format: "YYYY-MM-DD"

    Order(String orderId, List<MenuItem> items, String orderTime, String orderDate) {
        this.orderId = orderId;
        this.items = items;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
    }

    double getTotalAmount() {
        return items.stream().mapToDouble(item -> item.price).sum();
    }
}

class OrderHistory {
    /**
     * Manages all restaurant orders and provides analytical methods.
     */
    ArrayList<Order> orders = new ArrayList<>();

    OrderHistory() {
    }

    void addOrder(Order order) {
        orders.add(order);
    }

    int getTotalOrders() {
        return orders.size();
    }

    double getTotalRevenue() {
        /** Returns total revenue from all orders. */
        return orders.stream().mapToDouble(order -> order.getTotalAmount()).sum();
    }

    double getAverageOrderValue() {
        /**
         * Returns the average value of all orders.
         * BUG: This method has a bug - fix it! (Division by Zero)
         */
	
        return getTotalRevenue() / getTotalOrders();
    }

    int getOrderCountByCategory(String category) {
        /** Returns the number of orders that contain at least one item from the specified category. */
        return (int) orders.stream()
                .filter(order -> order.items.stream()
                        .anyMatch(item -> item.category.equals(category)))
                .count();
    }

    public Object[] getPeakHour() {
        // Write your code here to solve this problem
	//if there is no order or orderhistory list is empty
	
	if (orders == null || orders.isEmpty()){
        return null;
    }

    // Create a map to store the hour numbers from orders
        Map<Integer,Integer> hoursMap= new HashMap<>();
        for(Order order: orders){
            String time = order.orderTime; // "12:30"
            int hour = Integer.parseInt(time.split(":")[0]);

            hoursMap.put(hour, hoursMap.getOrDefault(hour,0)+1);

        }
        int peakHour =-1;
        int maxCount= 0;

        for (Map.Entry<Integer,Integer> entry: hoursMap.entrySet()){
            if(entry.getValue()>maxCount){
                peakHour = entry.getKey();
                maxCount= entry.getValue();
            }

        }

      // return new Object[]{peakHour,maxCount};


        Optional<Map.Entry<String, Long>> entry = orders.stream().
                map(o -> o.orderTime.split(":")[0]).collect(Collectors.groupingBy(o -> o,
                Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue());


        return entry.map(en -> new Object[]{Integer.parseInt(en.getKey()),en.getValue().intValue()}).orElse(null);
    }
}


public class Solution {
    public static void main(String[] args) {
        testOrder();
        testOrderHistory();
        testGetPeakHour();
    }

@Test
    public static void testOrder() {
        System.out.println("Running testOrder");
        MenuItem item1 = new MenuItem("M001", "Burger", "Main Course", 12.99);
        MenuItem item2 = new MenuItem("M002", "Fries", "Appetizer", 4.99);

        List<MenuItem> items = Arrays.asList(item1, item2);
        Order order = new Order("O001", items, "12:30", "2024-02-01");

        Assert.assertEquals("O001", order.orderId);
        Assert.assertEquals(17.98, order.getTotalAmount(), 0.01);
    }


@Test
    public static void testOrderHistory() {
        System.out.println("Running testOrderHistory");
        OrderHistory history = new OrderHistory();

        Assert.assertEquals(0, history.getTotalOrders());
        Assert.assertEquals(0.0, history.getTotalRevenue(), 0.01);

        MenuItem item1 = new MenuItem("M001", "Burger", "Main Course", 10.00);
        MenuItem item2 = new MenuItem("M002", "Fries", "Appetizer", 5.00);
        MenuItem item3 = new MenuItem("M003", "Soda", "Beverage", 2.00);
//
        Order order1 = new Order("O001", Arrays.asList(item1, item2), "12:30", "2024-02-01");
        Order order2 = new Order("O002", Arrays.asList(item3), "13:15", "2024-02-01");
        Order order3 = new Order("O003", Arrays.asList(item1, item3), "14:00", "2024-02-01");
//
         history.addOrder(order1);
         history.addOrder(order2);
         history.addOrder(order3);
	
	// This will fail since we have order in history
	//Assert.assertNull(history.getTotalOrders());
//
        Assert.assertEquals(3, history.getTotalOrders());
        Assert.assertEquals(29.00, history.getTotalRevenue(), 0.01);
        Assert.assertEquals(9.67, history.getAverageOrderValue(), 0.01);
        Assert.assertEquals(2, history.getOrderCountByCategory("Main Course"));
    }

@Test
    public static void testGetPeakHour() {
        System.out.println("Running testGetPeakHour");
        OrderHistory history = new OrderHistory();
       	//Checking if there is no any orders
//	 Assert.assertNull(history.getPeakHour());

        MenuItem item1 = new MenuItem("M001", "Item1", "Main Course", 10.00);

        Order order1 = new Order("O001", Arrays.asList(item1), "12:30", "2024-02-01");
        Order order2 = new Order("O002", Arrays.asList(item1), "13:15", "2024-02-01");
        Order order3 = new Order("O003", Arrays.asList(item1), "12:45", "2024-02-01");
        Order order4 = new Order("O004", Arrays.asList(item1), "18:20", "2024-02-01");
        Order order5 = new Order("O005", Arrays.asList(item1), "12:10", "2024-02-01");

        history.addOrder(order1);
        history.addOrder(order2);
        history.addOrder(order3);
        history.addOrder(order4);
        history.addOrder(order5);

        Assert.assertArrayEquals(history.getPeakHour(),new Object[]{12,3});
//        Assert.assertArrayEquals(new Object[]{13,1},history.getPeakHour());
    }
}
