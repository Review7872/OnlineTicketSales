package online.fadai.buytickets.service;

public interface AccountService {
    int createUserAccount(String username, String password, String phone);

    int usernameCanUse(String username) ;

    String getUserAuth(String jwt);
}
