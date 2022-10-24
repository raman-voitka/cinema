package by.academy.cinema.dao.film_dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FilmDao {

    boolean createFilmDB(String title) throws SQLException;

    List<Film> readAllFilmsDB() throws SQLException;

    Film readFilmDB(String title) throws SQLException;

    boolean updateFilmDateDB(LocalDate date, String title) throws SQLException;

    boolean updateFilmTimeDB(LocalTime time, String title) throws SQLException;

    boolean updateFilmTitleDB(String oldTitle, String newTitle) throws SQLException;

    boolean deleteFilmDB(String movieTitle) throws SQLException;

}