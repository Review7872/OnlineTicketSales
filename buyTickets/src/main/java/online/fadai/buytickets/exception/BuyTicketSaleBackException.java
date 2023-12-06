package online.fadai.buytickets.exception;

public class BuyTicketSaleBackException extends RuntimeException{
    public BuyTicketSaleBackException() {
        super("退票失败");
    }
}
