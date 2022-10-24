package by.academy.cinema.dao.user_dao;

public class User {

    private int id;
    private String login;
    private String password;
    private String salt;
    private String status;

    public User() {
    }

    public String getSalt() {
        return salt;
    }

    public User(int id, String login, String password, String salt, String status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}