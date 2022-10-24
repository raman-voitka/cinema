package by.academy.cinema.controller;

import by.academy.cinema.dao.film_dao.Film;
import by.academy.cinema.dao.user_dao.User;
import by.academy.cinema.enumUserStatus.UserStatus;
import by.academy.cinema.service.Service;
import by.academy.cinema.util.Constant;
import by.academy.cinema.util.Logger;

import java.util.List;
import java.util.Scanner;

public class ControllerRealization implements Controller {

    Service service;
    User userController;

    public ControllerRealization(Service service, User userController) {
        this.service = service;
        this.userController = userController;
    }

    @Override
    public void start() {
        Logger.logController.info("Main Menu Is Opened");
        System.out.println("-----------------");
        System.out.println("Welcome To Cinema");
        System.out.println("-----------------");
        while (true) {
            Scanner enter = new Scanner(System.in);
            System.out.println("For Log In Press \"1\"");
            System.out.println("For Sign Up Press \"2\"");
            System.out.print("For Exit Press \"0\"");
            System.out.println("   -> ");
            if (enter.hasNextInt()) {
                int choice = enter.nextInt();
                if (choice == 0) {
                    Logger.logController.info("Main Menu Is Closed");
                    break;
                } else if (choice == Constant.CHOICE_ONE) {
                    Logger.logController.info("The Attempt To Log In");
                    logIn();
                } else if (choice == Constant.CHOICE_TWO) {
                    Logger.logController.info("The Attempt To Sign Up");
                    signUp();
                } else {
                    Logger.logController.error("ENTERED INCORRECT DATA IN MAIN MENU");
                    System.out.println("PLEASE, ENTER CORRECT NUMBER" + '\n');
                }
            } else {
                Logger.logController.error("ENTERED INCORRECT DATA IN MAIN MENU");
                System.out.println("YOU ENTERED NOT A NUMBER. PLEASE, ENTER A NUMBER" + '\n');
            }
        }
    }

    private void logIn() {
        System.out.println("------------------------");
        System.out.println("Please, Enter Your Login");
        System.out.println("------------------------");
        String login = getString();
        System.out.println("---------------------------");
        System.out.println("Please, Enter Your Password");
        System.out.println("---------------------------");
        String password = getString();
        userController = service.getUserFromDB(login, password);
        if (userController != null) {
            String statusUser = userController.getStatus();
            Logger.logController.info("User {} Logged In,   Status - {}", login, statusUser);
            if (statusUser.equals(UserStatus.User.toString())) {
                getUserMenu();
            }
            if (statusUser.equals(UserStatus.Manager.toString())) {
                getManagerMenu();
            }
            if (statusUser.equals(UserStatus.Admin.toString())) {
                getAdminMenu();
            }
        } else {
            Logger.logController.error("THE ATTEMPT TO LOG IN NOT SUCCESSFUL");
            System.out.println("SORRY. THERE IS NO USER WITH ENTERED LOGIN OR ENTERED PASSWORD OR DATABASE ERROR" + '\n');
        }
    }

    private void signUp() {
        System.out.println("---------------------------------------------------");
        System.out.println("Please, Enter the Login Which You Would Like to Use");
        System.out.println("---------------------------------------------------");
        String login = getString();
        System.out.println("------------------------------------------------------");
        System.out.println("Please, Enter the Password Which You Would Like to Use");
        System.out.println("------------------------------------------------------");
        String password = getString();
        if (service.createUser(login, password)) {
            Logger.logController.info("User {} Signed Up Successfully", login);
            System.out.println("Registration Successful. For Further Action You Have To Log In");
        } else {
            Logger.logController.error("THE ATTEMPT TO SIGN UP NOT SUCCESSFUL");
            System.out.println("SORRY. THERE IS ALREADY A USER WITH THE SAME LOGIN OR DATABASE ERROR");
        }
        System.out.println();
    }

    private void getUserMenu() {
        Logger.logController.info("User's Menu Is Opened");
        while (true) {
            Scanner userMenu = new Scanner(System.in);
            System.out.println();
            System.out.println("Dear Customer, You Can Choose Next Options:");
            System.out.println("To View the List of Films Press \"1\"");
            System.out.println("To Buy a Ticket Press \"2\"");
            System.out.println("To Return a Ticket Press \"3\"");
            System.out.println("To View \"My Tickets\" Press \"4\"");
            System.out.print("For Exit Press \"0\"");
            System.out.println("   -> ");
            if (userMenu.hasNextInt()) {
                int choice = userMenu.nextInt();
                if (choice == 0) {
                    Logger.logController.info("User's Menu Is Closed");
                    Logger.logController.info("User {} Log Out", userController.getLogin());
                    break;
                } else {
                    switch (choice) {
                        case 1:
                            Logger.logController.info("User {} Viewed the List of Films", userController.getLogin());
                            List<Film> allFilms = service.getAllFilms();
                            if (allFilms != null && allFilms.size() != 0) {
                                for (Film film : allFilms) {
                                    System.out.println(film);
                                }
                            } else {
                                System.out.println("EXCUSE US, PLEASE. THERE ARE NO FILMS IN OUR CINEMA OR DATABASE ERROR");
                            }
                            break;
                        case 2:
                            Logger.logController.info("User {} Is Trying To Buy A Ticket", userController.getLogin());
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film Which Would You Like To Visit");
                            System.out.println("-----------------------------------------------------------------");
                            String titleBuy = getString();
                            Logger.logController.info("Selected Movie For Buying Ticket - \"{}\"", titleBuy);
                            System.out.println("-----------------------------------");
                            System.out.println("Please, Enter Preferred Seat Number");
                            System.out.println("-----------------------------------");
                            int seatBuy = getInt();
                            Logger.logController.info("Selected Place For Buying Ticket - {}", seatBuy);
                            if (service.manageTicketsUser(titleBuy, seatBuy, Constant.CHOICE_ONE)) {
                                Logger.logController.info("User {} Bought A Ticket On Film \"{}\", Place {}", userController.getLogin(), titleBuy, seatBuy);
                                System.out.println("Congratulation! You Just Bought a Ticket");
                            } else {
                                Logger.logController.error("THE ATTEMPT TO BUY A TICKET NOT SUCCESSFUL");
                                System.out.println("SORRY. THE OPERATION IS NOT SUCCESSFUL. CHECK:");
                                System.out.println("     1. ENTERED TITLE OF FILM");
                                System.out.println("     2. MAY BE CHOSEN TICKET HAS ALREADY BEEN SOLD");
                                System.out.println("     3. DATABASE ERROR");
                            }
                            break;
                        case 3:
                            Logger.logController.info("User {} Is Trying To Return A Ticket", userController.getLogin());
                            System.out.println("-----------------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film to Which You Want to Return Tickets");
                            System.out.println("-----------------------------------------------------------------------");
                            String titleReturn = getString();
                            Logger.logController.info("Selected Movie For Return Ticket - \"{}\"", titleReturn);
                            System.out.println("----------------------------------------------------");
                            System.out.println("Please, Enter the Seat Number of the Returned Ticket");
                            System.out.println("----------------------------------------------------");
                            int seatReturn = getInt();
                            Logger.logController.info("Selected Place For Return Ticket - {}", seatReturn);
                            if (service.manageTicketsUser(titleReturn, seatReturn, Constant.CHOICE_TWO)) {
                                Logger.logController.info("User {} Return A Ticket On Film \"{}\", Place {}", userController.getLogin(), titleReturn, seatReturn);
                                System.out.println("The Return Was Successful");
                            } else {
                                Logger.logController.error("THE ATTEMPT TO RETURN A TICKET NOT SUCCESSFUL");
                                System.out.println("SO SORRY. CAN'T RETURN. SOME REASONS:");
                                System.out.println("   1. ENTERED TITLE OF FILM INCORRECT");
                                System.out.println("   2. CHECK \"My Tickets\". MAY BE THE TICKET WAS BOUGHT, BUT NOT BY YOU, " +
                                        "OR YOU DON'T HAVE PURCHASED TICKETS AT ALL");
                                System.out.println("   3. CAN'T RETURN IF TICKET NOT SOLD");
                                System.out.println("   4. DATABASE ERROR");
                            }
                            break;
                        case 4:
                            Logger.logController.info("User {} Is Trying To View His (Her) Purchased Tickets", userController.getLogin());
                            List<Film> myFilms = service.viewMyTickets();
                            if (myFilms != null && myFilms.size() != 0) {
                                Logger.logController.info("User {} Viewed His (Her) Purchased Tickets", userController.getLogin());
                                for (Film film : myFilms) {
                                    System.out.println(film);
                                }
                            } else {
                                Logger.logController.info("User {} Doesn't Have Purchased Tickets", userController.getLogin());
                                System.out.println("YOU DON'T HAVE PURCHASED TICKETS OR DATABASE ERROR");
                            }
                            break;
                        default: {
                            Logger.logController.error("ENTERED INCORRECT DATA IN USER'S MENU");
                            System.out.println("PLEASE, ENTER CORRECT NUMBER");
                        }
                    }
                }
            } else {
                Logger.logController.error("ENTERED INCORRECT DATA IN USER'S MENU");
                System.out.println("YOU ENTERED NOT A NUMBER. PLEASE, ENTER A NUMBER");
            }
        }
    }

    private void getManagerMenu() {
        Logger.logController.info("Manager's Menu Is Opened");
        while (true) {
            Scanner managerMenu = new Scanner(System.in);
            System.out.println();
            System.out.println("You Are Logged In As MANAGER. You Can Do:");
            System.out.println("To Edit Date of Film Press \"1\"");
            System.out.println("To Edit Time of Film Press \"2\"");
            System.out.println("To Buy Tickets for a Specific User Press \"3\"");
            System.out.println("To Return Tickets for a Specific User Press \"4\"");
            System.out.print("For exit press \"0\"");
            System.out.println("   -> ");
            if (managerMenu.hasNextInt()) {
                int choice = managerMenu.nextInt();
                if (choice == 0) {
                    Logger.logController.info("Manager's Menu Is Closed");
                    Logger.logController.info("User {} Log Out", userController.getLogin());
                    break;
                } else {
                    switch (choice) {
                        case 1:
                            System.out.println("------------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film You Want to Change the Date of");
                            System.out.println("------------------------------------------------------------------");
                            String title1 = getString();
                            Logger.logController.info("Manager {} Is Trying To Change Date Of Film \"{}\"", userController.getLogin(), title1);
                            System.out.println("---------------------------------------------------");
                            System.out.println("Please, Enter New Date in Next Format:   YYYY-MM-DD");
                            System.out.println("---------------------------------------------------");
                            String date = getString();
                            Logger.logController.info("The Attempt To Enter New Date");
                            if (service.updateDate(title1, date)) {
                                Logger.logController.info("The Date Of Film \"{}\" Was Changed. New Date - {}", title1, date);
                                System.out.println("The Date Was Changed");
                            } else {
                                Logger.logController.error("CHANGING DATE NOT SUCCESSFUL");
                                System.out.println("FAIL. SOME REASONS:");
                                System.out.println("   1. THERE IS NO SUCH FILM IN CINEMA");
                                System.out.println("   2. CHECK TITLE. MAY BE AN ERROR OCCURRED IN DATA ENTRY");
                                System.out.println("   3. DATE INPUT ERROR");
                                System.out.println("   4. DATABASE ERROR");
                            }
                            break;
                        case 2:
                            System.out.println("------------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film You Want to Change the Time of");
                            System.out.println("------------------------------------------------------------------");
                            String title2 = getString();
                            Logger.logController.info("Manager {} Is Trying To Change Time Of Film \"{}\"", userController.getLogin(), title2);
                            System.out.println("---------------------------------------------------");
                            System.out.println("Please, Enter New Time in Next Format:   hh:mm:ss");
                            System.out.println("---------------------------------------------------");
                            String time = getString();
                            Logger.logController.info("The Attempt To Enter New Time");
                            if (service.updateTime(title2, time)) {
                                Logger.logController.info("The Time Of Film \"{}\" Was Changed. New Time - {}", title2, time);
                                System.out.println("The Time Was Changed");
                            } else {
                                Logger.logController.error("CHANGING TIME NOT SUCCESSFUL");
                                System.out.println("FAIL. SOME REASONS:");
                                System.out.println("   1. THERE IS NO SUCH FILM IN CINEMA");
                                System.out.println("   2. CHECK TITLE. MAY BE AN ERROR OCCURRED IN DATA ENTRY");
                                System.out.println("   3. TIME INPUT ERROR. HOURS MUST BE FROM 10 TILL 22");
                                System.out.println("   4. DATABASE ERROR");
                            }
                            break;
                        case 3:
                            System.out.println("----------------------------------------");
                            System.out.println("Please, Enter the Login of Specific User");
                            System.out.println("----------------------------------------");
                            String login = getString();
                            Logger.logController.info("Manager {} Is Trying To Buy A Ticket Of User {}", userController.getLogin(), login);
                            System.out.println("-----------------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film Which Would You Like To Buy Tickets");
                            System.out.println("-----------------------------------------------------------------------");
                            String titleBuy = getString();
                            Logger.logController.info("Selected Movie By Manager {} For Buying Ticket - \"{}\"", userController.getLogin(), titleBuy);
                            System.out.println("-----------------------------------");
                            System.out.println("Please, Enter Selected Seat Number");
                            System.out.println("-----------------------------------");
                            int seatBuy = getInt();
                            Logger.logController.info("Selected Place By Manager {} For Buying Ticket - {}", userController.getLogin(), seatBuy);
                            if (service.manageTicketsManager(login, titleBuy, seatBuy, Constant.CHOICE_ONE)) {
                                Logger.logController.info("Manager {} Bought A Ticket Of User {} On Film \"{}\", Place {}", userController.getLogin(), login, titleBuy, seatBuy);
                                System.out.println("The operation is complete. You Just Bought a Ticket of Specific User");
                            } else {
                                Logger.logController.error("MANAGER {} COULD NOT BUY A TICKET OF USER {}", userController.getLogin(), login);
                                System.out.println("SORRY. THE OPERATION IS NOT SUCCESSFUL. CHECK:");
                                System.out.println("     1. ENTERED LOGIN OF SPECIFIC USER");
                                System.out.println("     2. ENTERED TITLE OF FILM");
                                System.out.println("     3. MAY BE SPECIFIC USER DOESN'T HAVE PURCHASED TICKETS");
                                System.out.println("     4. MAY BE YOU ARE TRYING TO BUY MANAGER'S TICKETS");
                                System.out.println("     5. DATABASE ERROR");
                            }
                            break;
                        case 4:
                            System.out.println("----------------------------------------");
                            System.out.println("Please, Enter the Login of Specific User");
                            System.out.println("----------------------------------------");
                            String loginReturn = getString();
                            Logger.logController.info("Manager {} Is Trying To Return A Ticket Of User {}", userController.getLogin(), loginReturn);
                            System.out.println("--------------------------------------------------------------------------");
                            System.out.println("Please, Enter the Title of the Film Which Would You Like To Return Tickets");
                            System.out.println("--------------------------------------------------------------------------");
                            String titleReturn = getString();
                            Logger.logController.info("Selected Movie By Manager {} For Return Ticket - \"{}\"", userController.getLogin(), titleReturn);
                            System.out.println("-----------------------------------");
                            System.out.println("Please, Enter Selected Seat Number");
                            System.out.println("-----------------------------------");
                            int seatReturn = getInt();
                            Logger.logController.info("Selected Place By Manager {} For Return Ticket - {}", userController.getLogin(), seatReturn);
                            if (service.manageTicketsManager(loginReturn, titleReturn, seatReturn, Constant.CHOICE_TWO)) {
                                Logger.logController.info("Manager {} Return A Ticket Of User {} On Film \"{}\", Place {}", userController.getLogin(), loginReturn, titleReturn, seatReturn);
                                System.out.println("The operation done. You Just Return a Ticket of Specific User");
                            } else {
                                Logger.logController.error("MANAGER {} DID NOT RETURN A TICKET OF USER {}", userController.getLogin(), loginReturn);
                                System.out.println("SORRY. THE OPERATION IS NOT SUCCESSFUL. CHECK:");
                                System.out.println("     1. ENTERED LOGIN OF SPECIFIC USER");
                                System.out.println("     2. ENTERED TITLE OF FILM");
                                System.out.println("     3. MAY BE SPECIFIC USER DOESN'T HAVE PURCHASED TICKETS");
                                System.out.println("     4. MAY BE YOU ARE TRYING TO RETURN MANAGER'S TICKETS");
                                System.out.println("     5. DATABASE ERROR");
                            }
                            break;
                        default: {
                            Logger.logController.error("ENTERED INCORRECT DATA IN MANAGER MENU");
                            System.out.println("PLEASE, ENTER CORRECT NUMBER");
                        }
                    }
                }
            } else {
                Logger.logController.error("ENTERED INCORRECT DATA IN MANAGER MENU");
                System.out.println("YOU ENTERED NOT A NUMBER. PLEASE, ENTER A NUMBER");
            }
        }
    }

    private void getAdminMenu() {
        Logger.logController.info("Admin's Menu Is Opened");
        while (true) {
            Scanner adminMenu = new Scanner(System.in);
            System.out.println();
            System.out.println("You Are Logged In As ADMIN. List Of Your Action Below:");
            System.out.println("To Change User Login Press \"1\"");
            System.out.println("To Change User Password Press \"2\"");
            System.out.println("To Delete User Press \"3\"");
            System.out.println("To Add New Film Press \"4\"");
            System.out.println("To Change Film Press \"5\"");
            System.out.println("To Delete Film Press \"6\"");
            System.out.print("For exit press \"0\"");
            System.out.println("   -> ");
            if (adminMenu.hasNextInt()) {
                int choice = adminMenu.nextInt();
                if (choice == 0) {
                    Logger.logController.info("Admin's Menu Is Closed");
                    Logger.logController.info("User {} Log Out", userController.getLogin());
                    break;
                } else {
                    switch (choice) {
                        case 1:
                            System.out.println("-----------------------");
                            System.out.println("Please, Enter Old Login");
                            System.out.println("-----------------------");
                            String oldLogin = getString();
                            Logger.logController.info("Admin {} Is Trying To Change The Login Of User {}", userController.getLogin(), oldLogin);
                            System.out.println("-----------------------");
                            System.out.println("Please, Enter New Login");
                            System.out.println("-----------------------");
                            String newLogin = getString();
                            if (service.changeUser(oldLogin, newLogin, Constant.CHOICE_ONE)) {
                                Logger.logController.info("The Login Of User {} Was Changed By Admin {}. New Login Of User - {}", oldLogin, userController.getLogin(), newLogin);
                                System.out.println("User Login Was Changed");
                            } else {
                                Logger.logController.error("THE LOGIN OF USER {} WASN'T CHANGED BY ADMIN {}", oldLogin, userController.getLogin());
                                System.out.println("CHANGE LOGIN NOT COMPLETE");
                                System.out.println("   1. THERE IS NO USER WITH OLD LOGIN WHICH YOU ENTERED");
                                System.out.println("   2. THERE IS USER WITH NEW LOGIN WHICH YOU ENTERED");
                                System.out.println("   3. YOU A TRYING TO CHANGE ADMIN'S LOGIN. THAT'S IMPOSSIBLE");
                                System.out.println("   4. DATABASE ERROR");
                            }
                            break;
                        case 2:
                            System.out.println("-------------------------------");
                            System.out.println("Please, Enter The Login Of User");
                            System.out.println("-------------------------------");
                            String userLogin = getString();
                            Logger.logController.info("Admin {} Is Trying To Change The Password Of User {}", userController.getLogin(), userLogin);
                            System.out.println("--------------------------");
                            System.out.println("Please, Enter New Password");
                            System.out.println("--------------------------");
                            String newPassword = getString();
                            if (service.changeUser(userLogin, newPassword, Constant.CHOICE_TWO)) {
                                Logger.logController.info("The Password Of User {} Was Changed By Admin {}. New Password Of User - {}", userLogin, userController.getLogin(), newPassword);
                                System.out.println("User Password Was Changed");
                            } else {
                                Logger.logController.error("THE PASSWORD OF USER {} WASN'T CHANGED BY ADMIN {}", userLogin, userController.getLogin());
                                System.out.println("CHANGE LOGIN NOT COMPLETE");
                                System.out.println("   1. THERE IS NO USER WITH OLD LOGIN WHICH YOU ENTERED");
                                System.out.println("   2. YOU A TRYING TO CHANGE ADMIN'S LOGIN. THAT'S IMPOSSIBLE");
                                System.out.println("   3. DATABASE ERROR");
                            }
                            break;
                        case 3:
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Please, Enter the Login of User which You Want to Delete");
                            System.out.println("--------------------------------------------------------");
                            String login = getString();
                            Logger.logController.info("Admin {} Is Trying To Delete User {}", userController.getLogin(), login);
                            if (service.deleteUser(login)) {
                                Logger.logController.info("The User {} Was Deleted By Admin {}", login, userController.getLogin());
                                System.out.println("Delete User Is Successful");
                            } else {
                                Logger.logController.error("THE USER {} WASN'T DELETED BY ADMIN {}", login, userController.getLogin());
                                System.out.println("DELETE NOT COMPLETE");
                                System.out.println("   1. THERE IS NO USER WITH LOGIN WHICH YOU ENTERED");
                                System.out.println("   2. YOU A TRYING TO DELETE \"ADMIN\". THAT'S IMPOSSIBLE");
                                System.out.println("   3. DATABASE ERROR");
                            }
                            break;
                        case 4:
                            System.out.println("---------------------------------------------------------");
                            System.out.println("Please, Enter the Title of New Film which You Want to Add");
                            System.out.println("---------------------------------------------------------");
                            String newFilm = getString();
                            Logger.logController.info("Admin {} Is Trying To Add New Film With Title \"{}\"", userController.getLogin(), newFilm);
                            if (service.addFilm(newFilm)) {
                                Logger.logController.info("The Film \"{}\" Was Added To The List By Admin {}", newFilm, userController.getLogin());
                                System.out.println("The Film Was Added");
                            } else {
                                Logger.logController.error("THE FILM \"{}\" WASN'T ADDED TO LIST BY ADMIN {}", newFilm, userController.getLogin());
                                System.out.println("YOU CAN'T ADD THIS FILM BECAUSE IT'S ALREADY ON THE LIST OR DATABASE ERROR");
                            }
                            break;
                        case 5:
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Please, Enter the Title of Film which You Want to Change");
                            System.out.println("--------------------------------------------------------");
                            String oldTitle = getString();
                            Logger.logController.info("Admin {} Is Trying To Change The Film \"{}\"", userController.getLogin(), oldTitle);
                            System.out.println("-------------------------------");
                            System.out.println("Please, Enter New Title of Film");
                            System.out.println("-------------------------------");
                            String newTitle = getString();
                            if (service.updateFilm(oldTitle, newTitle)) {
                                Logger.logController.info("The Film \"{}\" Was Changed By Admin {}. New Title Of Film - {}", oldTitle, userController.getLogin(), newTitle);
                                System.out.println("The Film Was Updated");
                            } else {
                                Logger.logController.error("THE FILM \"{}\" WASN'T CHANGED BY ADMIN {}", oldTitle, userController.getLogin());
                                System.out.println("UPDATE FAIL. THIS FILM IS ALREADY ON THE LIST OR DATABASE ERROR");
                            }
                            break;
                        case 6:
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Please, Enter the Title of Film which You Want to Delete");
                            System.out.println("--------------------------------------------------------");
                            String title = getString();
                            Logger.logController.info("Admin {} Is Trying To Delete The Film \"{}\"", userController.getLogin(), title);
                            if (service.deleteMovie(title)) {
                                Logger.logController.info("The Film \"{}\" Was Deleted From The List By Admin {}", title, userController.getLogin());
                                System.out.println("Delete Film Is Successful");
                            } else {
                                Logger.logController.error("THE FILM \"{}\" WASN'T DELETED BY ADMIN {}", title, userController.getLogin());
                                System.out.println("DELETE NOT DONE. THERE IS NO FILM WITH TITLE WHICH YOU ENTERED OR DATABASE ERROR");
                            }
                            break;
                        default: {
                            Logger.logController.error("ENTERED INCORRECT DATA IN ADMIN MENU");
                            System.out.println("PLEASE, ENTER CORRECT NUMBER");
                        }
                    }
                }
            } else {
                Logger.logController.error("ENTERED INCORRECT DATA IN ADMIN MENU");
                System.out.println("YOU ENTERED NOT A NUMBER. PLEASE, ENTER A NUMBER");
            }
        }
    }

    private String getString() {
        Scanner enter = new Scanner(System.in);
        return enter.nextLine();
    }

    private int getInt() {
        while (true) {
            Scanner enter = new Scanner(System.in);
            if (enter.hasNextInt()) {
                int seat = enter.nextInt();
                if (seat > 0 && seat <= 20) {
                    return seat;
                } else {
                    System.out.println("PLEASE, ENTER CORRECT NUMBER");
                    System.out.println();
                }
            } else {
                System.out.println("YOU ENTERED NOT A NUMBER. PLEASE, ENTER A NUMBER" + '\n');
            }
        }
    }

}