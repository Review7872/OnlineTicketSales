package online.fadai.buytickets.controller.account;

import jakarta.annotation.Resource;
import online.fadai.buytickets.service.AccountService;
import online.fadai.pojo.User;
import online.fadai.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
//@CrossOrigin(origins = "/*")
public class AccountController {
    @Resource
    private AccountService accountService;
    @DubboReference
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public Object login(@RequestBody User user){
        return userService.getJWT(user.getUsername(), user.getPassword());
    }

    /**
     * 长登录
     * @param user
     * @return
     */
    @PostMapping("loginLong")
    public Object loginLong(@RequestBody User user){
        return userService.getJWTLong(user.getUsername(), user.getPassword());
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("reg")
    public Object reg(@RequestBody User user){
        return accountService.createUserAccount(user.getUsername(), user.getPassword(), user.getPhone());
    }

    /**
     * 找回
     * @param user
     * @return
     */
    @PostMapping("reReg")
    public Object reReg(@RequestBody User user){
        return userService.reReg(user.getPhone(), user.getPassword(), user.getUsername());
    }

    /**
     * 查看是否用户名被使用
     * @param username
     * @return
     */
    @GetMapping("useUsername")
    public Object useUsername(String username){
        return accountService.usernameCanUse(username);
    }
}
