package by.academy.cinema.dao.user_dao;

import by.academy.cinema.enumUserStatus.UserStatus;
import by.academy.cinema.util.AbstractConnection;
import by.academy.cinema.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoRealization implements UserDao {

    @Override
    public boolean createUserDB(String login, String password, String salt) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.CREATE_USER_SQL);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, salt);
            stmt.setString(4, UserStatus.User.toString());
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public User readUserDB(String lgn) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.READ_USER_SQL);
            stmt.setString(1, lgn);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                String status = resultSet.getString("status");
                return new User(id, login, password, salt, status);
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
        return null;
    }

    @Override
    public boolean updateUserDB(String oldLogin, String newLogin, String salt, String updating) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(updating);
            stmt.setString(1, newLogin);
            stmt.setString(2, salt);
            stmt.setString(3, oldLogin);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public boolean deleteUserDB(String userName) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.DELETE_USER_SQL);
            stmt.setString(1, userName);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

}