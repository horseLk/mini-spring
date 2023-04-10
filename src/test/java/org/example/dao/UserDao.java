package org.example.dao;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.example.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserInfoById(int userId) {
        String sql = "select * from t_user where id=" + userId;
        return (User) jdbcTemplate.query(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            User rtnUser = null;
            try {
                if (rs.next()) {
                    rtnUser = new User();
                    rtnUser.setId(rs.getInt("id"));
                    rtnUser.setName(rs.getString("name"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rtnUser;
        });
    }
}
