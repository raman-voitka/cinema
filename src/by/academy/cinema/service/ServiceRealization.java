package by.academy.cinema.service;

import by.academy.cinema.dao.film_dao.Film;
import by.academy.cinema.dao.film_dao.FilmDao;
import by.academy.cinema.dao.ticket_dao.Ticket;
import by.academy.cinema.dao.ticket_dao.TicketDao;
import by.academy.cinema.dao.user_dao.User;
import by.academy.cinema.dao.user_dao.UserDao;
import by.academy.cinema.enumUserStatus.UserStatus;
import by.academy.cinema.exception.CinemaException;
import by.academy.cinema.util.Constant;
import by.academy.cinema.util.HashPassword;
import by.academy.cinema.util.Logger;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class ServiceRealization implements Service {

    User userService;
    UserDao userDB;
    FilmDao filmDB;
    List<Film> allFilms;
    List<Film> myFilms;
    TicketDao ticketDB;
    List<Ticket> ticket1;

    public ServiceRealization(User userService, UserDao userDB, FilmDao filmDB, List<Film> allFilms, List<Film> myFilms, TicketDao ticketDB, List<Ticket> ticket1) {
        this.userService = userService;
        this.userDB = userDB;
        this.filmDB = filmDB;
        this.allFilms = allFilms;
        this.myFilms = myFilms;
        this.ticketDB = ticketDB;
        this.ticket1 = ticket1;
    }

    @Override
    public boolean createUser(String login, String password) {
        try {
            User creatingUser = userDB.readUserDB(login);
            byte[] userSaltArray = HashPassword.getSalt();
            String userSalt = HashPassword.codeSalt(userSaltArray);
            String passwordSaltHash = HashPassword.getSaltHash(password, userSaltArray);
            if (creatingUser == null && passwordSaltHash != null) {
                return userDB.createUserDB(login, passwordSaltHash, userSalt);
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD createUser(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public User getUserFromDB(String login, String password) {
        try {
            userService = userDB.readUserDB(login);
            if (userService != null) {
                String userSaltDB = userService.getSalt();
                byte[] arrayUserSaltDB = HashPassword.decodeSalt(userSaltDB);
                String userHash = HashPassword.getSaltHash(password, arrayUserSaltDB);
                if (userService.getPassword().equals(userHash)) {
                    return userService;
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD getUserFromDB(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return null;
    }

    @Override
    public boolean changeUser(String oldLogin, String newLoginOrPassword, int choice) {
        try {
            User updatingUser = userDB.readUserDB(oldLogin);
            User newUser = userDB.readUserDB(newLoginOrPassword);
            if (updatingUser != null && !updatingUser.getStatus().equals(UserStatus.Admin.toString())) {
                if (choice == Constant.CHOICE_ONE && newUser == null) {
                    return userDB.updateUserDB(oldLogin, newLoginOrPassword, updatingUser.getSalt(), Constant.UPDATE_USER_LOGIN_SQL);
                }
                if (choice == Constant.CHOICE_TWO) {
                    byte[] userSaltArray = HashPassword.getSalt();
                    String userSalt = HashPassword.codeSalt(userSaltArray);
                    String passwordSaltHash = HashPassword.getSaltHash(newLoginOrPassword, userSaltArray);
                    return userDB.updateUserDB(oldLogin, passwordSaltHash, userSalt, Constant.UPDATE_USER_PASSWORD_SQL);
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD changeUser(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean deleteUser(String login) {
        try {
            User deletingUser = userDB.readUserDB(login);
            if (deletingUser != null && !deletingUser.getStatus().equals(UserStatus.Admin.toString())) {
                if (userDB.deleteUserDB(login)) {
                    ticketDB.resetTicketsDB(Constant.RESET_TICKET_BY_USER_SQL, deletingUser.getId());
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD deleteUser(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean addFilm(String title) {
        try {
            Film newFilm = filmDB.readFilmDB(title);
            if (newFilm == null) {
                if (filmDB.createFilmDB(title)) {
                    int newFilmID = filmDB.readFilmDB(title).getId();
                    for (int i = 1; i <= 5; i++) {
                        ticketDB.createTicketDB(newFilmID, i, 10);
                    }
                    for (int i = 6; i <= 10; i++) {
                        ticketDB.createTicketDB(newFilmID, i, 20);
                    }
                    for (int i = 11; i <= 15; i++) {
                        ticketDB.createTicketDB(newFilmID, i, 30);
                    }
                    for (int i = 16; i <= 20; i++) {
                        ticketDB.createTicketDB(newFilmID, i, 40);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD addFilm(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean updateFilm(String oldTitle, String newTitle) {
        try {
            Film updatingFilm = filmDB.readFilmDB(oldTitle);
            if (updatingFilm != null) {
                if (filmDB.updateFilmTitleDB(oldTitle, newTitle)) {
                    ticketDB.resetTicketsDB(Constant.RESET_TICKET_BY_FILM_SQL, updatingFilm.getId());
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD updateFilm(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean deleteMovie(String movieTitle) {
        try {
            Film deletingFilm = filmDB.readFilmDB(movieTitle);
            if (deletingFilm != null) {
                if (filmDB.deleteFilmDB(movieTitle)) {
                    return ticketDB.deleteTicketsDB(deletingFilm.getId());
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD deleteMovie(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public List<Film> getAllFilms() {
        try {
            allFilms.clear();
            List<Film> filmsDB = filmDB.readAllFilmsDB();
            List<Ticket> ticketsDB = ticketDB.readAllTicketsDB();
            for (Film film : filmsDB) {
                List<Ticket> tickets = new ArrayList<>();
                for (Ticket ticket : ticketsDB) {
                    if (film.getId() == ticket.getFilmID()) {
                        tickets.add(ticket);
                    }
                }
                allFilms.add(new Film(film.getId(), film.getTitle(), film.getDate(), film.getTime(), tickets));
            }
            return allFilms;
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD getAllFilms(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
            return null;
        }
    }

    @Override
    public List<Film> viewMyTickets() {
        try {
            myFilms.clear();
            List<Film> movies = getAllFilms();
            for (Film film : movies) {
                List<Ticket> ticketList = film.getTickets();
                List<Ticket> myTicket = ticketList
                        .stream()
                        .filter(n -> n.getUser() == userService.getId())
                        .collect(Collectors.toList());
                myFilms.add(new Film(film.getId(), film.getTitle(), film.getDate(), film.getTime(), myTicket));
            }
            movies.clear();
            return myFilms.stream().filter(n -> n.getTickets().size() != 0).collect(Collectors.toList());
        } catch (NullPointerException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD viewMyTickets()");
            return null;
        }
    }

    @Override
    public boolean manageTicketsUser(String title, int seat, int constant) {
        try {
            Film film = filmDB.readFilmDB(title);
            if (film != null) {
                Ticket ticket = ticketDB.readTicketDB(film.getId(), seat);
                if (ticket != null) {
                    if (!ticket.isFlag() && constant == Constant.CHOICE_ONE) {
                        return ticketDB.updateTicketDB(Constant.BUYING_TICKET_SQL, userService.getId(), film.getId(), ticket.getSeatNumber());
                    }
                    if (ticket.isFlag() && ticket.getUser() == (userService.getId()) && constant == Constant.CHOICE_TWO) {
                        return ticketDB.updateTicketDB(Constant.RETURN_TICKET_SQL, 0, film.getId(), ticket.getSeatNumber());
                    }
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD manageTicketsUser(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean manageTicketsManager(String login, String title, int seat, int cons) {
        try {
            User userFromDB = userDB.readUserDB(login);
            if (userFromDB != null) {
                Film film = filmDB.readFilmDB(title);
                if (film != null) {
                    Ticket ticket = ticketDB.readTicketDB(film.getId(), seat);
                    if (ticket != null && ticket.getUser() == userFromDB.getId() && userFromDB.getStatus().equals(UserStatus.User.toString())) {
                        if (cons == Constant.CHOICE_ONE) {
                            return ticketDB.updateTicketDB(Constant.BUYING_TICKET_SQL, userService.getId(), film.getId(), ticket.getSeatNumber());
                        }
                        if (cons == Constant.CHOICE_TWO) {
                            return ticketDB.updateTicketDB(Constant.RETURN_TICKET_SQL, 0, film.getId(), ticket.getSeatNumber());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD manageTicketsManager(). THIS METHOD DOESN'T WORK WITH DATABASE CORRECT");
        }
        return false;
    }

    @Override
    public boolean updateDate(String title, String dateInput) {
        try {
            Film film = filmDB.readFilmDB(title);
            LocalDate date = splitDate(dateInput);
            if (film != null && date != null) {
                return filmDB.updateFilmDateDB(date, title);
            }
        } catch (SQLException | CinemaException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD updateDate()");
        }
        return false;
    }

    private LocalDate splitDate(String dateInput) throws CinemaException {
        try {
            String[] arrays = dateInput.split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(arrays[0]), Integer.parseInt(arrays[1]), Integer.parseInt(arrays[2]));
            LocalDate dateInterval = LocalDate.now().plusMonths(3);
            if (date.isAfter(LocalDate.now()) && date.isBefore(dateInterval) && arrays.length == 3) {
                return date;
            }
        } catch (NumberFormatException | DateTimeException | PatternSyntaxException | ArrayIndexOutOfBoundsException e) {
            throw new CinemaException(e);
        }
        return null;
    }

    @Override
    public boolean updateTime(String title, String timeInput) {
        try {
            Film film = filmDB.readFilmDB(title);
            LocalTime time = splitTime(timeInput);
            if (film != null && time != null) {

                return filmDB.updateFilmTimeDB(time, title);
            }
        } catch (SQLException | CinemaException e) {
            Logger.logService.warn("ERROR WAS OCCURRED IN METHOD updateTime()");
        }
        return false;
    }

    private LocalTime splitTime(String dateInput) throws CinemaException {
        try {
            String[] arrays = dateInput.split(":");
            LocalTime time = LocalTime.of(Integer.parseInt(arrays[0]), Integer.parseInt(arrays[1]), Integer.parseInt(arrays[2]));
            if (time.getHour() >= 10 && time.getHour() <= 22 && arrays.length == 3) {
                return time;
            }
        } catch (NumberFormatException | DateTimeException | PatternSyntaxException | ArrayIndexOutOfBoundsException e) {
            throw new CinemaException(e);
        }
        return null;
    }

}