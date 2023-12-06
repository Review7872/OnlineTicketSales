package online.fadai.buytickets.service;


public interface TicketSalesService {
    int buyTicketSale(String carId, String beginRoute, String endRoute, String seatId, String cardId,String phone);

    int buyTicketSaleBack(long orderId);

    Object selectOrder(String username);

    int payOrder(long orderId);

    int payWay(long orderId, int payWay);
}
