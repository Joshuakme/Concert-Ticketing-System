package com.mycompany.concertticketingsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh Min En, Shia Chai Fen, Wong Wei Hao
 */
public class ConcertTicketingSystem {
    /* Description: 
        The Concert Ticketing System is focus on music concert ticket selling which 
        will be held in Malaysia, so it is basically divided into 3 main parts which 
        are "Concerts", "Tickets", "Customers and orders".
    */ 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
               
        // Welcome User
        System.out.println("Welcome to Concert Ticketing System!\n");
        
        boolean exit = false;
        while(!exit) {
            displayMenu();
            int menuChoice = sc.nextInt();
            
            switch(menuChoice) {
                case 1: 
                    searchConcert();
                    break;
                case 2:
                    System.out.println("View Trending");
                    break;
                case 3:
                    System.out.println("Buy Ticket");
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Error!\n");
            }
        }

        
        // Login
        while(true) {
            if(Login())
                break;
            clearScreen();
        }
        
        // Select 
        

    }
    

    
    // Methods & Functions   
    public static void InitializeVenue() {
        String[] venueDetails;
        int counter = 0;
        String[] venueIdList = new String[100];
        String[] venueNameList = new String[100];
        String[] venueLocationList = new String[100];
        String[] venueTypeList = new String[100];
        String[] venueCapacityList = new String[100];
        
        try {
            File concertFile = new File("concert.txt");
            Scanner fileScanner = new Scanner(concertFile);
            String currentLine = fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                venueDetails = currentLine.split("\t");
            
                venueIdList[counter] = venueDetails[0];
                venueNameList[counter] = venueDetails[1];
                venueLocationList[counter] = venueDetails[2];
                venueTypeList[counter] = venueDetails[3];
                venueCapacityList[counter] = venueDetails[4];
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            venueDetails = currentLine.split("\t");
            
            venueIdList[counter] = venueDetails[0];
            venueNameList[counter] = venueDetails[1];
            venueLocationList[counter] = venueDetails[2];
            venueTypeList[counter] = venueDetails[3];
            venueCapacityList[counter] = venueDetails[4];

            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
    }
    
        public static void InitializeConcert() {
        String[] concertLists;
        int counter = 0;
        String[] concertNameList = new String[100];
        String[] ConcertArtistList = new String[100];
        String[] venueTypeList = new String[100];
        String[] venueCapacityList = new String[100];
        
        try {
            File concertFile = new File("concert.txt");
            Scanner fileScanner = new Scanner(concertFile);
            String currentLine = fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                concertLists = currentLine.split("\t");
            
                concertNameList[counter] = concertLists[1];
                venueLocationList[counter] = concertLists[2];
                venueTypeList[counter] = concertLists[3];
                venueCapacityList[counter] = concertLists[4];
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            concertLists = currentLine.split("\t");
            
            concertNameList[counter] = concertLists[1];
            venueLocationList[counter] = concertLists[2];
            venueTypeList[counter] = concertLists[3];
            venueCapacityList[counter] = concertLists[4];

            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
    }
    
    public static boolean Login() {
        Scanner sc = new Scanner(System.in);
        
        // Ask user Login Information
        System.out.printf("%12s\n%12s\n", " Login Page ", "------------");
        System.out.print("Username: ");
        String inputUsername = sc.nextLine().trim();
        System.out.print("Password: ");
        String inputPwd = sc.nextLine().trim();
        
        // Check Credentials
        boolean isLogged = checkCredential(inputUsername, inputPwd);
        
        if(isLogged) {
            System.out.println("Welcome! You are logged in!\n");
            return true;
        }
        else {
            System.out.println("Incorrect credentials, please try again!\n");
            return false;  
        }                 
    }
    
    public static boolean checkCredential(String inputUsername, String inputPwd) {
        // Login Credentials
        String[] usernameList = {"taruc", "admin"};
        String[] pwdList = {"taruc", "0000"};
        boolean isEqual = false;

        // Check Empty inputs
        if(inputUsername.equals("") || inputPwd.equals("")) 
            return false;
        
        for(int i=0; i<usernameList.length; i++) {
            if(inputUsername.equals(usernameList[i]) && inputPwd.equals(pwdList[i]))
                isEqual = true; 
        }
        
        return isEqual;
    }
     
    public static void displayRootMenu() {
        System.out.println("Select ");
        
        System.out.printf("%-5s%-10s", "#", "Menu");
        
        System.out.println("\n");
    }
    
    public static void displayMenu() {
        String[] custMenu = {"Search Concert", "View Trending", "Buy Ticket", "Exit" };
        System.out.println("Menu: ");
        
        for(int i=0; i<custMenu.length; i++) {
            System.out.printf("%-3s%-20s\n",(i + 1) + ".", custMenu[i]);
        }
        System.out.println("Select the menu (num): ");
        
        
        System.out.println("\n");
    }
    
    public static void searchConcert() {
        System.out.println("Search Concert\n");
    }
    
    public static void printCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        String formattedDate = today.format(format);
        
        System.out.println(formattedDate);
    }
    
    public static void printCurrentTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
        
        String formattedTime = now.format(format);
        
        System.out.println(formattedTime);
    }
    
    public static void clearScreen()  {
        System.out.println("\n\n");
        System.out.println("Console cleared...");
    }
}
