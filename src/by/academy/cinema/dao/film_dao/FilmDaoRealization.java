package by.academy.cinema.dao.film_dao;

import by.academy.cinema.util.AbstractConnection;
import by.academy.cinema.util.Constant;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FilmDaoRealization implements FilmDao {

    @Override
    public boolean createFilmDB(String title) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.CREATE_FILM_SQL);
            stmt.setString(1, title);
            stmt.setDate(2, Date.valueOf(LocalDate.now().plusDays(1)));
            stmt.setTime(3, Time.valueOf(LocalTime.of(19, 0, 0)));
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public List<Film> readAllFilmsDB() throws SQLException {
        List<Film> films = new ArrayList<>();
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.READ_ALL_FILMS_SQL);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                films.add(new Film(id, title, date, time));
            }
            return films;
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public Film readFilmDB(String title) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.READ_FILM_SQL);
            stmt.setString(1, title);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titleDB = resultSet.getString("title");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                return new Film(id, titleDB, date, time);
            }
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
        return null;
    }

    @Override
    public boolean updateFilmTimeDB(LocalTime time, String title) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.UPDATE_TIME_SQL);
            stmt.setTime(1, Time.valueOf(time));
            stmt.setString(2, title);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public boolean updateFilmDateDB(LocalDate date, String title) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.UPDATE_DATE_SQL);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, title);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public boolean updateFilmTitleDB(String oldTitle, String newTitle) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.UPDATE_TITLE_SQL);
            stmt.setString(1, newTitle);
            stmt.setString(2, oldTitle);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

    @Override
    public boolean deleteFilmDB(String movieTitle) throws SQLException {
        try {
            Connection connection = AbstractConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(Constant.DELETE_FILM_SQL);
            stmt.setString(1, movieTitle);
            return !stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            AbstractConnection.close();
        }
    }

}