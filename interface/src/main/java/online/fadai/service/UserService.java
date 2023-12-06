package online.fadai.service;

import online.fadai.pojo.User;

public interface UserService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 秘密啊
     * @return jwt
     */
    String getJWT(String username, String password);

    /**
     * 长登录
     *
     * @param username 用户名
     * @param password 密码
     * @return jwt
     */
    String getJWTLong(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    手机号
     * @param auth     身份
     * @return 成功/失败
     */
    int insert(String username, String password, String phone, String auth);

    /**
     * 查询单个用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User selectOne(String username);

    /**
     * 更新手机号
     *
     * @param username 用户名
     * @param phone    手机号
     * @return 成功/失败
     */
    int updatePhoto(String username, String phone);

    /**
     * 更新性别
     *
     * @param username 用户名
     * @param sex      性别
     * @return 成功/失败
     */
    int updateSex(String username, int sex);

    /**
     * 更新年龄
     *
     * @param username 用户名
     * @param age      年龄
     * @return 成功/失败
     */
    int updateAge(String username, int age);

    /**
     * 更新证件
     *
     * @param username 用户名
     * @param cardId   证件
     * @return 成功/失败
     */
    int updateCardId(String username, long cardId);

    /**
     * 修改密码
     *
     * @param phone    手机号
     * @param password 新密码
     * @param username 用户名
     * @return 成功/失败
     */
    int reReg(String phone, String password, String username);
}
