package online.fadai.buytickets.exception;

public class BuyTicketSaleException extends RuntimeException{
    public BuyTicketSaleException() {
        super("购票失败");
    }
}
