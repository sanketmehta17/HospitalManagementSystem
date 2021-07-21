/*
 *  Name of file: Appointments.java
 *  Author:  Sanket Ushangbhai Mehta
 *  Purpose: This class contains different menus and it will redirect based on their input
 *  Description: This class basically take input from user and validate them and
 *  call different Services which are created
 * */

import Service.AppointmentService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;


public class Appointments {

    public void Menu() throws SQLException, ParseException {

        System.out.println("*************************************");
        System.out.println("Press b to Book an appointment\n" +
                "Press c to Cancel an existing appointment\n" +
                "Press r to Reschedule an existing appointment\n" +
                "Press v to View your appointments");
        System.out.println("*************************************");

        Scanner scanner = new Scanner(System.in);
        String inputFromUser = scanner.nextLine();

        if (inputFromUser.equals("b") || inputFromUser.equals("B")){
            book_appointment();
        }else if (inputFromUser.equals("c") || inputFromUser.equals("C")){
            cancel_appointment();
        }else if (inputFromUser.equals("r") || inputFromUser.equals("R")){
            reschedule_appointment();
        }else if (inputFromUser.equals("v") || inputFromUser.equals("V")){
            view_appointments();
        }else {
            invalid();
        }
    }

    public void book_appointment() throws SQLException, ParseException {

        AppointmentService appointmentService = new AppointmentService();
        if (appointmentService.book_appointment()) {
            System.out.println("Appointment booked successfully!");
            Menu();
        } else {
            System.out.println("Booking failed!");
            Menu();
        }
    }

    public void cancel_appointment() throws SQLException, ParseException {

        AppointmentService appointmentService = new AppointmentService();
        if (appointmentService.cancel_appointment()){
            System.out.println("Appointment cancelled successfully!");
            Menu();
        }else {
            System.out.println("Cancellation failed!");
            Menu();
        }
    }

    public void reschedule_appointment() throws SQLException, ParseException {
        AppointmentService appointmentService = new AppointmentService();
        if (appointmentService.reschedule_appointment()){
            System.out.println("Appointment rescheduled successfully!");
            Menu();
        }else {
            System.out.println("Cancellation failed!");
            Menu();
        }
    }

    public void view_appointments() throws SQLException, ParseException {

        AppointmentService appointmentService = new AppointmentService();
        appointmentService.view_appointment();
        Menu();
    }

    public void invalid(){
        System.out.println("Invalid input!");
    }

}
