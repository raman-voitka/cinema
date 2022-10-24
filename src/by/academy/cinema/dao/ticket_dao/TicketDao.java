package by.academy.cinema.dao.ticket_dao;

import java.sql.SQLException;
import java.util.List;

public interface TicketDao {

    void createTicketDB(int filmID, int seatNumber, int price) throws SQLException;

    List<Ticket> readAllTicketsDB() throws SQLException;

    Ticket readTicketDB(int film_id, int seatNum) throws SQLException;

    boolean updateTicketDB(String string, int user, int filmID, int seatNum) throws SQLException;

    void resetTicketsDB(String reset, int userID) throws SQLException;

    boolean deleteTicketsDB(int filmID) throws SQLException;
}
