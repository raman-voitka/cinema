package by.academy.cinema.util;

public class Constant {

    public static final int CHOICE_ONE = 1;
    public static final int CHOICE_TWO = 2;

    public static final String CREATE_FILM_SQL = "INSERT INTO films (title, date, time) VALUES (?,?,?)";
    public static final String READ_FILM_SQL = "SELECT * FROM films WHERE title = ?";
    public static final String READ_ALL_FILMS_SQL = "SELECT * FROM films";
    public static final String UPDATE_DATE_SQL = "UPDATE films SET date = ? WHERE title = ?";
    public static final String UPDATE_TIME_SQL = "UPDATE films SET time = ? WHERE title = ?";
    public static final String UPDATE_TITLE_SQL = "UPDATE films SET title = ? WHERE title = ?";
    public static final String DELETE_FILM_SQL = "DELETE FROM films WHERE title = ?";


    public static final String CREATE_TICKET_SQL = "INSERT INTO tickets (user_id, film_id, seatNumber, price, flag) VALUES (?,?,?,?,?)";
    public static final String BUYING_TICKET_SQL = "UPDATE tickets SET user_id = ?, flag = true WHERE film_id = ? AND seatNumber = ?";
    public static final String RETURN_TICKET_SQL = "UPDATE tickets SET user_id = ?, flag = false WHERE film_id = ? AND seatNumber = ?";
    public static final String RESET_TICKET_BY_USER_SQL = "UPDATE tickets SET user_id = 0, flag = false WHERE user_id = ?";
    public static final String RESET_TICKET_BY_FILM_SQL = "UPDATE tickets SET user_id = 0, flag = false WHERE film_id = ?";
    public static final String DELETE_TICKET_SQL = "DELETE FROM tickets WHERE film_id = ?";


    public static final String CREATE_USER_SQL = "INSERT INTO users (login, password, salt, status) VALUES (?,?,?,?)";
    public static final String READ_USER_SQL = "SELECT * FROM users WHERE login = ?";
    public static final String UPDATE_USER_LOGIN_SQL = "UPDATE users SET login = ?, salt = ? WHERE login = ?";
    public static final String UPDATE_USER_PASSWORD_SQL = "UPDATE users SET password = ?, salt = ? WHERE login = ?";
    public static final String DELETE_USER_SQL = "DELETE FROM users WHERE login = ?";

}