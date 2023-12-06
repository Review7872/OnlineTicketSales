package online.fadai.buytickets.exception;

public class AuthErrorException extends RuntimeException{
    public AuthErrorException() {
        super("身份信息异常");
    }
}
