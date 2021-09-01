package sise.cch;

import org.dom4j.DocumentException;
import sise.cch.io.Resources;
import sise.cch.mapper.UserMapper;
import sise.cch.pojo.User;
import sise.cch.sqlSession.SqlSession;
import sise.cch.sqlSession.SqlSessionFactory;
import sise.cch.sqlSession.SqlSessionFactoryBuilder;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Chench
 * @date 2021/8/31 11:29
 * @description
 */
public class Test {

    @org.junit.Test
    public void test1() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().bulid(resourceAsSteam);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        /*user.setId(1);
        user.setName("zhangsan");
        User user1 = sqlSession.selectOne("sise.cch.mapper.UserMapper.findByCondition", user);
        System.out.println(user1);*/

//        List<User> users = sqlSession.selectList("sise.cch.mapper.UserMapper.findAll", null);
        /*UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAll();
        users.forEach(i -> {
            System.out.println(i);
        });*/

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        User user = new User();
//        user.setName("lisi");
//        user.setId(4);
//        userMapper.saveUser(user);
//        sqlSession.insert("sise.cch.mapper.UserMapper.saveUser",user);
//        userMapper.updateUser(user);
//        userMapper.deleteUser(user);
//        userMapper.deleteUserById(5);
        userMapper.deleteUserByName("lisi");
    }
}
