package org.example.dao;

import com.minis.batis.SqlSession;
import com.minis.batis.SqlSessionFactory;
import com.minis.beans.factory.annotation.Autowired;
import org.example.domain.UserClass;

import java.sql.PreparedStatement;

public class UserClassDao {
    @Autowired
    SqlSessionFactory sqlSessionFactory;


    public int updateClass(UserClass userClass) {
        String sqlId = "org.example.domain.UserClass.updateClass";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.update(sqlId, new Object[]{userClass}, PreparedStatement::executeUpdate);
    }
}
