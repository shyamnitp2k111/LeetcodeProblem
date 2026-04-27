package code;

import java.util.ArrayList;
import java.util.List;

public class Tesco {

    /*Your goal today is to implement an MVP of the new system - apply the relevant discounts to customer's cart.
    You will be given a cart with products and their prices, as well as a list of discounts that might apply to the cart.
    Please calculate the discounted prices for items in the cart. Keep in mind that other kinds of discounts will be added to the system in future.

    Given a cart like this:
            -
    o name: Ham Sandwich
    o category: sandwich
    o count: 1
    o price: 3 pounds
-
    o name: Cheese Sandwich
    o category: sandwich
    o count: 2
    o price: 3 pounds
-
    o name: Orange Juice
    o category: drink
    o count: 1
    o price: 1.85 pound
-
    o name: Apple Juice
    o category: drink
    o count: 2
    o price: 5.2 pounds


    And those discounts:
            - MostExpensiveItemPercentageDiscount: 30% off for the most expensive cart item
- SetAmountVoucherPerCategory: voucher for a maximum 5 pounds off the overall spend in the sandwich category

    Please return a discounted shopping cart:

            -
    o name: Ham Sandwich
    o category: sandwich
    o count: 1
    o price: 3 pounds
    o discounted price: 0 pounds
-
    o name: Cheese Sandwich
    o category: sandwich
    o count: 2
    o price: 3 pounds
    o discounted price: 1 pound
-
    o name: Orange Juice
    o category: drink
    o count: 1
    o price: 1.85 pounds
    o discounted price: 1.85 pound
-
    o name: Apple Juice
    o category: drink
    o count: 2
    o price: 5.2 pounds
    o discounted price: 3.64 pounds*/


    public static void main(String[] args) {
        List<Items> listOfItems = new ArrayList<>();
        listOfItems.add(new Items("Ham Sandwich", "sandwich",1, 3.0 ));
        listOfItems.add(new Items("Cheese Sandwich", "sandwich", 2, 3.0));
        listOfItems.add(new Items("Orange Juice", "drink", 1, 1.85));
        listOfItems.add(new Items("Apple Juice", "drink", 2, 5.2));


        int ind = -1;
        double maxPrice = 0D;

        for(int index = 0 ; index < listOfItems.size(); index++) {

            double pricePerItems = listOfItems.get(index).getPrice() ;
            if(pricePerItems > maxPrice) {
                maxPrice = pricePerItems;
                ind = index;
            }
        }


        double maxSandwickDiscount = 5;

        List<DiscountedItem> discountedItemList = new ArrayList<>();

        for(int index = 0 ; index < listOfItems.size(); index++) {
            if(null != listOfItems.get(index).getCategory()
                    && listOfItems.get(index).getCategory().toLowerCase().equals("sandwich")
                    && maxSandwickDiscount >= 0){

                Items item = listOfItems.get(index);

                if(item.getPrice() > maxSandwickDiscount) {
                    double discountPrice = item.getPrice() - maxSandwickDiscount;
                    discountedItemList.add(new DiscountedItem(item.getName(), item.getCategory(), item.getCount(), item.getPrice(), item.getPrice() - discountPrice));
                    maxSandwickDiscount = 0;
                    continue;
                } else if (item.getPrice() < maxSandwickDiscount) {
                    //double discountPrice =  maxSandwickDiscount - item.getPrice();
                    discountedItemList.add(new DiscountedItem(item.getName(), item.getCategory(), item.getCount(), item.getPrice(),0d));
                    maxSandwickDiscount = maxSandwickDiscount - item.getPrice();
                    continue;
                } else {
                    discountedItemList.add(new DiscountedItem(item.getName(), item.getCategory(), item.getCount(), item.getPrice(), item.getPrice()));
                    continue;
                }
            }


            if(index == ind) {
                Items items = listOfItems.get(index);
                double discountPrice = items.getPrice() - (items.getPrice() * 30)/100;
                discountedItemList.add(new DiscountedItem(items.getName(),items.getCategory(),items.getCount(), items.getPrice(), discountPrice));
                continue;
            }

            discountedItemList.add(new DiscountedItem(listOfItems.get(index).getName(),listOfItems.get(index).getCategory(),listOfItems.get(index).getCount(), listOfItems.get(index).getPrice(),  listOfItems.get(index).getPrice()));
        }


        System.out.println("------------ Results after discount -----------");

        discountedItemList.stream().forEach(System.out::println);
    }

}



class DiscountedItem {
    private String name;
    private String category;
    private Integer count;
    private Double price;
    private Double discountedPrice;

    public DiscountedItem() {
    }

    public DiscountedItem(String name, String category, Integer count, Double price, Double discountedPrice) {
        this.name = name;
        this.category = category;
        this.count = count;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @Override
    public String toString() {
        return "DiscountedItem{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                '}';
    }
}

class Items {

    private String name;
    private String category;
    private Integer count;
    private Double price;

    public Items() {
    }

    public Items(String name, String category, Integer count, Double price) {
        this.name = name;
        this.category = category;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Items{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
