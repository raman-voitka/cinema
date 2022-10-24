package by.academy.cinema.util;

import by.academy.cinema.controller.ControllerRealization;
import by.academy.cinema.main.Main;
import by.academy.cinema.service.ServiceRealization;
import org.slf4j.LoggerFactory;

public class Logger {

    public static org.slf4j.Logger logController = LoggerFactory.getLogger(ControllerRealization.class);
    public static org.slf4j.Logger logMain = LoggerFactory.getLogger(Main.class);
    public static org.slf4j.Logger logService = LoggerFactory.getLogger(ServiceRealization.class);

}