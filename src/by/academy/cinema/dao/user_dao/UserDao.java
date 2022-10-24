package by.academy.cinema.dao.user_dao;

import java.sql.SQLException;

public interface UserDao {

    boolean createUserDB(String login, String password, String salt) throws SQLException;

    User readUserDB(String lgn) throws SQLException;

    boolean updateUserDB(String oldLogin, String newLogin, String salt, String updating) throws SQLException;

    boolean deleteUserDB(String userName) throws SQLException;

}