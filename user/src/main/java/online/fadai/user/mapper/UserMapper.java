package online.fadai.user.mapper;

import online.fadai.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("")
    @Results(id = "userRes", value = {
            @Result(id = true, column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "auth", property = "auth"),
            @Result(column = "photo", property = "photo"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "card_id", property = "cardId")
    })
    User userRes();

    @Select("""
            select username,password,auth from user_t where username = #{username}
            """)
    @ResultMap("userRes")
    User selectUsernameAndPasswordAuth(String username);

    @Select("""
            select username,password,phone,auth,photo,sex,age,card_id from user_t where username = #{username}
            """)
    @ResultMap("userRes")
    User selectOne(String username);

    @Insert("""
            insert into user_t(username,password,phone,auth)values(#{username},#{password},#{phone},#{auth})
            """)
    int insert(String username, String password, String phone, String auth);

    @Update("""
            update user_t set photo = #{photo} where username = #{username}
            """)
    int updatePhoto(String username, String photo);

    @Update("""
            update user_t set sex = #{sex} where username = #{username}
            """)
    int updateSex(String username, int sex);

    @Update("""
            update user_t set age = #{age} where username = #{username}
            """)
    int updateAge(String username, int age);

    @Update("""
            update user_t set card_id = #{cardId} where username = #{username}
            """)
    int updateCardId(String username, long cardId);

    @Update("""
            update user_t set password = #{password} where phone = #{phone} and username = #{username}
            """)
    int reReg(String phone, String password, String username);
}
