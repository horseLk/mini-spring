package org.example.dao;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.RowMapper;
import org.example.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public User getUserInfoById2(int userId) {
        String sql = "select * from t_user where id=?";
        return (User) jdbcTemplate.query(sql, new Object[]{new Integer(userId)}, pstmt -> {
            ResultSet rs = pstmt.executeQuery();
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

    public List<User> getUsersWithMinId(int minId) {
        final String sql = "select * from t_user where id>=?";
        return (List<User>) jdbcTemplate.query(sql, new Object[]{new Integer(minId)}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User rtnUser = new User();
                rtnUser.setId(rs.getInt("id"));
                rtnUser.setName(rs.getString("name"));

                return rtnUser;
            }
        });
    }
}
