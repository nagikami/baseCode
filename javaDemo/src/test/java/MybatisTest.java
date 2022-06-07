import nagi.mybatis.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    @Test
    public void userInsert() {
        try {
            InputStream configStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(configStream);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            // 通过代理模式创建UserMapper接口的代理实现类对象
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int result = userMapper.insertUser();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
