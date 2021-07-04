import Model.CartItem;
import Model.Order;
import Model.OrderItem;
import Model.Pharmacy;
import Services.OrderService;
import Services.PharmacyService;

import java.util.Map;
import java.util.Scanner;

public class BillingPage {

    /*
     * This is the Main checkout function which will call another methods like paidByCash
     * */
    public void CheckOut(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press (s for cash): ");
        String paymentType = scanner.nextLine();
        String paymentMode = "";

        System.out.println("Enter user Id: ");
        String user_id = scanner.nextLine();

        if(paymentType.equals("c") || paymentType.equals("C")) {
            paymentMode = "Card";
        } else if (paymentType.equals("s") || paymentType.equals("S")) {
            paymentMode = "Cash";
            paidByCash();
        } else {
            System.out.println("Please select correct payment mode");
        }
    }

    /*
     * This is the paid by cash function it will update the stock and clear the cart
     * */
    public void paidByCash(){
        OrderService orderService = new OrderService();
        orderService.addOrder(new Order(123));
        Integer order_id = orderService.getLastOrderId();

        for(Map.Entry me: PharmacyPage.cart.entrySet()) {
            CartItem cartItem = (CartItem) me.getValue();

            PharmacyService pharmacyService = new PharmacyService();
            pharmacyService.updateStock(me.getKey().toString(), cartItem.getQty());

            OrderItem orderItem = new OrderItem(Integer.parseInt(me.getKey().toString()),cartItem.getName(),cartItem.getQty(),cartItem.getPrice(),cartItem.getTotalPrice(),order_id);
            orderService.addOrderItems(orderItem);
        }



        PharmacyPage.cart.clear();
        System.out.println("You have been successfully checkout");
        PharmacyPage pharmacyPage = new PharmacyPage();
        pharmacyPage.PharmacyMenu();
    }


}