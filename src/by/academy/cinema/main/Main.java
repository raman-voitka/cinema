package by.academy.cinema.main;

import by.academy.cinema.controller.Controller;
import by.academy.cinema.controller.ControllerRealization;
import by.academy.cinema.dao.film_dao.Film;
import by.academy.cinema.dao.film_dao.FilmDao;
import by.academy.cinema.dao.film_dao.FilmDaoRealization;
import by.academy.cinema.dao.ticket_dao.Ticket;
import by.academy.cinema.dao.ticket_dao.TicketDao;
import by.academy.cinema.dao.ticket_dao.TicketDaoRealization;
import by.academy.cinema.dao.user_dao.User;
import by.academy.cinema.dao.user_dao.UserDao;
import by.academy.cinema.dao.user_dao.UserDaoRealization;
import by.academy.cinema.service.Service;
import by.academy.cinema.service.ServiceRealization;
import by.academy.cinema.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger.logMain.info("----------The Start Of The Application----------");

        User user = new User();
        UserDao userDB = new UserDaoRealization();
        FilmDao filmDB = new FilmDaoRealization();
        List<Film> allFilms = new ArrayList<>();
        List<Film> myFilms = new ArrayList<>();
        TicketDao ticketDB = new TicketDaoRealization();
        List<Ticket> ticket1 = new ArrayList<>();
        Service service = new ServiceRealization(user, userDB, filmDB, allFilms, myFilms, ticketDB, ticket1);

        Controller cinema = new ControllerRealization(service, user);
        cinema.start();

        Logger.logMain.info("------------------THE END-----------------------");

    }

}