package sise.cch.mapper;

import sise.cch.pojo.User;

import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 10:54
 * @description
 */
public interface UserMapper {

    List<User> findAll();

    User findByCondition(User user);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Integer id);

    void deleteUserByName(String name);

    void deleteUser(User user);
}
