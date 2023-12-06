package online.fadai.buytickets.exception;

public class CreateAccountFailedException extends RuntimeException{
    public CreateAccountFailedException() {
        super("创建账户失败，请联系客服");
    }
}
