package company.citi.oder;

/*
We are building a program to manage a food delivery platform. The platform has multiple restaurants,
customers place orders, and those orders move through statuses:
PLACED → PREPARING → OUT_FOR_DELIVERY → DELIVERED, or CANCELED.

Definitions:
* An "order" has: orderId, restaurantId, customerId, orderValue, distanceKm, status.
* "OrderManager" manages orders and provides order statistics.

To begin with, we present you with two tasks:
1-1) Read through and understand the code below. Feel free to run it.
1-2) The test for OrderManager is not passing due to a bug in the code.
     Make the necessary changes to OrderManager to fix the bug.
*/

/*
We are updating our system to include delivery session information for orders.

We introduce a Delivery class:
- Each Delivery has a unique deliveryId
- startMinute and endMinute represent minutes from the start of the day (same day)
- duration = endMinute - startMinute

Add two functions to OrderManager:

2.1) addDelivery(orderId, delivery):
     Associate a delivery with an order. One order could have multiple deliveries. If the order does not exist, ignore.


2.2) getAverageDeliveryTimeByRestaurant():
     Compute the average delivery duration (minutes) per restaurantId.
     Count ALL deliveries for that restaurant (across orders).
     Return: Map<Integer, Double> restaurantId -> averageDuration.

To assist you in testing these new functions, we have provided the `testGetAverageDeliveryTimeByRestaurant` and `assertAlmost` functions.

*/

import java.util.*;
import org.junit.*;

/**
 * Orders are active until delivered or cancelled
 */
enum OrderStatus {
  PLACED,
  PREPARING,
  OUT_FOR_DELIVERY,
  DELIVERED,
  CANCELED
}

class Order {
  int orderId;
  int restaurantId;
  int customerId;
  double orderValue;
  double distanceKm;
  OrderStatus status;

  Order(int orderId, int restaurantId, int customerId, double orderValue, double distanceKm, OrderStatus status) {
    this.orderId = orderId;
    this.restaurantId = restaurantId;
    this.customerId = customerId;
    this.orderValue = orderValue;
    this.distanceKm = distanceKm;
    this.status = status;
  }
}

class Delivery {
  int deliveryId;
  int startMinute;
  int endMinute;

  Delivery(int deliveryId, int startMinute, int endMinute) {
    this.deliveryId = deliveryId;
    this.startMinute = startMinute;
    this.endMinute = endMinute;
  }

  int getDurationMinutes() {
    return endMinute - startMinute;
  }
}

class OrderStats {
  int totalOrders;
  int activeOrders;
  int closedOrders;

  OrderStats(int totalOrders, int activeOrders, int closedOrders) {
    this.totalOrders = totalOrders;
    this.activeOrders = activeOrders;
    this.closedOrders = closedOrders;
  }
}

class OrderManager {
  List<Order> orders;
  Map<Integer, List<Delivery>> deliveriesByOrderId;

  OrderManager() {
    this.orders = new ArrayList<>();
    this.deliveriesByOrderId = new HashMap<>();
  }


    public Map<Integer, Double> getAverageDeliveryTimeByRestaurant() {

        Map<Integer, Integer> totalDuration = new HashMap<>();
        Map<Integer, Integer> totalCount = new HashMap<>();

        for (Order order : orders) {

            List<Delivery> deliveries = deliveriesByOrderId.get(order.orderId);

            if (deliveries == null) {
                continue;
            }

            for (Delivery delivery : deliveries) {

                totalDuration.put(
                        order.restaurantId,
                        totalDuration.getOrDefault(order.restaurantId, 0)
                                + delivery.getDurationMinutes());

                totalCount.put(
                        order.restaurantId,
                        totalCount.getOrDefault(order.restaurantId, 0) + 1);
            }
        }

        Map<Integer, Double> result = new HashMap<>();

        for (Integer restaurantId : totalDuration.keySet()) {
            result.put(
                    restaurantId,
                    (double) totalDuration.get(restaurantId)
                            / totalCount.get(restaurantId));
        }

        return result;
    }

  public void addDelivery(int orderId, Delivery delivery) {
        for (Order order : orders) {
            if (order.orderId == orderId) {
                deliveriesByOrderId
                        .computeIfAbsent(orderId, k -> new ArrayList<>())
                        .add(delivery);
                return;
            }
        }
  }
  

  void addOrder(Order order) {
    orders.add(order);
  }

  void updateOrderStatus(int orderId, OrderStatus newStatus) {
    for (Order o : orders) {
      if (o.orderId == orderId) {
        o.status = newStatus;
        return;
      }
    }
  }

  OrderStats getOrderStatistics() {
    int total = orders.size();

    int active = 0;
    for (Order o : orders) {
      if (o.status == OrderStatus.PLACED || o.status == OrderStatus.OUT_FOR_DELIVERY || o.status == OrderStatus.PREPARING) {
        active++;
      }
    }

    int closed = 0;
    for (Order o : orders) {
      if (o.status == OrderStatus.DELIVERED || o.status == OrderStatus.CANCELED) {
        closed++;
      }
    }

    return new OrderStats(total, active, closed);
  }
}

public class Solution {
  public static void main(String[] args) {
    testOrderManager();
    testGetAverageDeliveryTimeByRestaurant();
    System.out.println("All tests passed.");
  }

  public static void testOrderManager() {
    System.out.println("Running testOrderManager");
    OrderManager om = new OrderManager();

    om.addOrder(new Order(1, 10, 100, 25.0, 3.2, OrderStatus.PLACED));
    om.addOrder(new Order(2, 10, 101, 55.0, 1.4, OrderStatus.PREPARING));
    om.addOrder(new Order(3, 11, 102, 15.0, 6.0, OrderStatus.OUT_FOR_DELIVERY));
    om.addOrder(new Order(4, 11, 103, 40.0, 2.0, OrderStatus.DELIVERED));
    om.addOrder(new Order(5, 12, 104, 18.0, 4.5, OrderStatus.CANCELED));

    OrderStats stats = om.getOrderStatistics();
    Assert.assertEquals(5, stats.totalOrders);
    Assert.assertEquals(3, stats.activeOrders);
    Assert.assertEquals(2, stats.closedOrders);
  }
  
  private static void assertAlmost(double expected, double actual, double eps) {
    Assert.assertTrue(Math.abs(expected - actual) <= eps);
  }

  public static void testGetAverageDeliveryTimeByRestaurant() {
    System.out.println("Running testGetAverageDeliveryTimeByRestaurant");
    OrderManager om = new OrderManager();

    om.addOrder(new Order(1, 10, 100, 25.0, 3.2, OrderStatus.DELIVERED));
    om.addOrder(new Order(2, 10, 101, 55.0, 1.4, OrderStatus.DELIVERED));
    om.addOrder(new Order(3, 11, 102, 15.0, 6.0, OrderStatus.DELIVERED));

    om.addDelivery(1, new Delivery(101, 10, 40));
    om.addDelivery(2, new Delivery(102, 50, 80));
    om.addDelivery(2, new Delivery(103, 90, 150));
    om.addDelivery(3, new Delivery(104, 20, 50));

    // Ignore unknown order
    om.addDelivery(999, new Delivery(105, 0, 10));

    Map<Integer, Double> avg = om.getAverageDeliveryTimeByRestaurant();

    // restaurant 10: durations [30, 30, 60] => avg 40
    assertAlmost(40.0, avg.get(10), 0.0001);
    // restaurant 11: durations [30] => avg 30
    assertAlmost(30.0, avg.get(11), 0.0001);
  }
}