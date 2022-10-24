package by.academy.cinema.dao.ticket_dao;

public class Ticket {

    private int id;
    private int user;
    private int filmID;
    private int seatNumber;
    private int price;
    private boolean flag;

    public Ticket(int id, int user, int filmID, int seatNumber, int price, boolean flag) {
        this.id = id;
        this.user = user;
        this.filmID = filmID;
        this.seatNumber = seatNumber;
        this.price = price;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public int getFilmID() {
        return filmID;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public int getPrice() {
        return price;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "place " + seatNumber +
                ", price " + price +
                ", is Sold? - " + flag;
    }
}