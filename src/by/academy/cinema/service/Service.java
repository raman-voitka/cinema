package by.academy.cinema.service;

import by.academy.cinema.dao.film_dao.Film;
import by.academy.cinema.dao.user_dao.User;

import java.util.List;

public interface Service {

    boolean createUser(String login, String password);

    User getUserFromDB(String login, String password);

    boolean changeUser(String oldLogin, String newLoginOrPassword, int choice);

    boolean deleteUser(String login);

    boolean addFilm(String title);

    boolean updateFilm(String oldTitle, String newTitle);

    boolean deleteMovie(String movieTitle);

    List<Film> getAllFilms();

    List<Film> viewMyTickets();

    boolean manageTicketsUser(String title, int seat, int constant);

    boolean manageTicketsManager(String login, String title, int seat, int cons);

    boolean updateDate(String title, String dateInput);

    boolean updateTime(String title, String timeInput);
}
