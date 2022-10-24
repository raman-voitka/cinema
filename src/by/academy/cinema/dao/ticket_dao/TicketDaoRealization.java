package by.academy.cinema.dao.ticket_dao;

import by.academy.cinema.util.AbstractConnection;
import by.academy.cinema.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoRealization implements TicketDao {

    @Override
    public void createTicketDB(int filmID, int seatNumber, int price) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.CREATE_TICKET_SQL);
            stmt.setInt(1, 0);
            stmt.setInt(2, filmID);
            stmt.setInt(3, seatNumber);
            stmt.setInt(4, price);
            stmt.setInt(5, 0);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public List<Ticket> readAllTicketsDB() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tickets");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user = resultSet.getInt("user_id");
                int filmID = resultSet.getInt("film_id");
                int seatNumber = resultSet.getInt("seatNumber");
                int price = resultSet.getInt("price");
                boolean flag = resultSet.getBoolean("flag");
                tickets.add(new Ticket(id, user, filmID, seatNumber, price, flag));
            }
            return tickets;
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public Ticket readTicketDB(int film_id, int seatNum) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tickets WHERE film_id = ? AND seatNumber = ?");
            stmt.setInt(1, film_id);
            stmt.setInt(2, seatNum);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user = resultSet.getInt("user_id");
                int filmID = resultSet.getInt("film_id");
                int seatNumber = resultSet.getInt("seatNumber");
                int price = resultSet.getInt("price");
                boolean flag = resultSet.getBoolean("flag");
                return new Ticket(id, user, filmID, seatNumber, price, flag);
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
        return null;
    }

    @Override
    public boolean updateTicketDB(String updating, int user, int filmID, int seatNum) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(updating);
            stmt.setInt(1, user);
            stmt.setInt(2, filmID);
            stmt.setInt(3, seatNum);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public void resetTicketsDB(String reset, int id) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(reset);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public boolean deleteTicketsDB(int filmID) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.DELETE_TICKET_SQL);
            stmt.setInt(1, filmID);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }
}