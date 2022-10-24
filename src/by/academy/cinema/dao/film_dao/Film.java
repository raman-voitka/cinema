package by.academy.cinema.dao.film_dao;

import by.academy.cinema.dao.ticket_dao.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Film {

    private int id;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private List<Ticket> tickets;

    public Film(int id, String title, LocalDate date, LocalTime time) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public Film(int id, String title, LocalDate date, LocalTime time, List<Ticket> tickets) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Film - '" +
                title + '\'' +
                ", date - " + date +
                ", time - " + time +
                ", tickets: " + tickets;
    }
}