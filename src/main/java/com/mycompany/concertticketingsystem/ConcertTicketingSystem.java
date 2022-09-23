package com.mycompany.concertticketingsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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
        
        // Global States
        boolean isLoggedIn = false;
        
        // Object Initialization
        Artist[] artistList = initializeArtists();
        Venue[] venueList = initializeVenues();
        Concert[] concertList = initializeConcerts(artistList, venueList);
        Person[][] userList = initializePerson();   // userList[0][] is Admin list, serList[1][] is Customer list
        
               
        // Welcome User
        startScreen();

        boolean exit = false;
        while(!exit) {
            displayMenu();
            int menuChoice = Character.getNumericValue(sc.next().charAt(0));
            
            switch(menuChoice) {
                case 1: // Search Concert
                    searchConcert(artistList, concertList);
                    break;
                case 2: // View Trending
                    System.out.println("View Trending");
                    break;
                case 3: // Buy Ticket
                    System.out.println("Buy Ticket\n");
                    
                    // Check Login Status
                    if(!isLoggedIn) {
                        System.out.println("You are not signed in. Please sign in before buy ticket.");
                        
                        if(Login())
                            isLoggedIn = true;
                        
                            Guest guest = new Guest();
                            // Get user detail (username, password, accStatus)
                            guest.registerAccount("username", "password", "accStatus");
                    } else 
                        // Display Concert List
                        System.out.println("-----------------------------------------------------------------------------------------------------------------");
                        System.out.println("| NO |       DATE & TIME       |                CONCERT TITLE                |              VENUE               |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  1 |  2022-04-30  T20:00:00  |    Justin Lo Fresh Start Live - Malaysia    |          Arena of Stars          |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  2 |  2022-08-18  T20:30:00  |    Happier Than Ever, The World Tour 2022   |   Bukit Jalil National Stadium   |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  3 |  2022-09-09  T20:30:00  |    (G)-IDLE 'Just Me ()I-DLE' World Tour    |         Zepp Kuala Lumpur        |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  4 |  2022-10-22  T20:30:00  |             Justice World Tour              |   Bukit Jalil National Stadium   |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  5 |  2022-12-18  T19:00:00  |           JJ Lin \"JJ20\" World Tour          |   Bukit Jalil National Stadium   |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  6 |  2023-01-15  T19:00:00  |   Jay Chou Carnival World Tour - Malaysia   |   Bukit Jalil National Stadium   |");
                        System.out.println("|----|-------------------------|---------------------------------------------|----------------------------------|");
                        System.out.println("|  7 |  2023-03-04  T20:00:00  |             Born Pink World Tour            |   Bukit Jalil National Stadium   |");
                        System.out.println("-----------------------------------------------------------------------------|----------------------------------|");

                        // Get choice
                        do{
                            System.out.println("Please Enter Your Preference Concert Show (1-8): ");
                            int choice = sc.nextInt();
                            
                            if(choice > 10 && choice < 0){
                                System.out.println("\t\t\t*****************************************");
                                System.out.println("\t\t\t\tPLEASE ENTER 1 - 10 ONLY! ");
                                System.out.println("\t\t\t*****************************************");
                            }
                            
                            else if(choice > 0 && choice <11){
                                
                            }
                        
                        
                        System.out.println("Are you sure? (Y for yes, N for no): ") ;
                        char confirmation = sc.next().charAt(0);
                            if (confirmation == 'Y' || confirmation == 'y'){
                                
                                
                            }
                        // Display seat status(booked / empty) [table maybe]
                        // Ask for detail (no, ticketCat, etc.)
                        // 
                        } while(true));
                        break;
                   
                
                case 4: // Login/Register
                    System.out.println("Login\n");
                    
                    // Remember to use userList[][] (Line 37) to check credentials
                    
                    // Login/Register
                    
                    // Menu
                    
                    // User input username & password
                    
                    //            
                case 5: // Other
                    System.out.println("Other");
                    
                    // Code Here
                    

                case 6: // Exit
                    exit = true;
                    System.out.println("Successfully Exited");
                    break;
                default:
                    System.out.println("\nError!\n");
            }
        }
        
        // Select 
        

    }
    

    
    // Methods & Functions  
    public static void startScreen() {
        System.out.println("Welcome to Concert Ticketing System!\n");
    }
    
    public static void displayMenu() {
        String[] custMenu = {"Search Concert", "View Trending", "Buy Ticket", "Login/Register", "Other", "Exit"};
        System.out.println("Menu: ");

        for (int i = 0; i < custMenu.length; i++) {
            System.out.printf("%-3s%-20s\n", (i + 1) + ".", custMenu[i]);
        }
        System.out.print("Select the menu (num): ");
    }
    
    // Data Initialization Methods
    public static Catalog createCatalog(Artist[] artistList, Concert[] concertList) {
        Catalog catalog;
        Map<String, Concert> concertTitles = new HashMap();
        Map<String, Concert[]> concertArtists = new HashMap();
        Map<String, Concert[]> concertLanguages = new HashMap();
        Map<String, Concert[]> concertDates = new HashMap();
        Map<String, Concert[]> concertVenues = new HashMap();
        
        // Get Current Date
        LocalDate now = LocalDate.now();
        
        int concertValidCount = 0;
        int languageValidCount = 0;
        for(int i=0; i<concertList.length; i++) {
            if(concertList[i] != null) {
                concertValidCount++;
                if(concertList[i].getLanguage() != null) {
                    languageValidCount++;
                }
            }
        }
        
        // Map for Concert Titles
        Concert concertByTitle = null;
        int titleCount = 0;
        for(int j=0; j<concertList.length; j++) {
            if(concertList[j] != null) {
                for(int i=0; i<concertList.length; i++) {
                    if(concertList[i] != null) {
                        if(concertList[j].getName().toUpperCase().equals(concertList[i].getName().toUpperCase())) {
                            concertByTitle = concertList[i];

                            titleCount++;
                        }
                    }
                }
                concertTitles.put(concertList[j].getName(), concertByTitle);
            } 
        }
        
        // Map for Concert Artists
        for(int j=0; j<artistList.length; j++) {
            Concert[] concertByArtist = new Concert[concertValidCount];
            int artistCount = 0;
            
            if(artistList[j] != null) {
                for(int i=0; i<concertList.length; i++) {
                    if(concertList[i] != null) {
                        if(artistList[j].getName().toUpperCase().equals(concertList[i].getArtist().getName().toUpperCase())) {
                            concertByArtist[artistCount] = concertList[i];
                            
                            artistCount++;
                        }
                    }
                }
                concertArtists.put(artistList[j].getName(), concertByArtist);
            }
        }   
        
        // Map for Concert Language
        String[] languageList = {"Cantonese", "English", "Mandarin", "Korean"};
        int languageCount = 0;
        
        for(int j=0; j<languageList.length; j++) {
            Concert[] concertByLanguage = new Concert[1000];
            
            if(concertList[j] != null) {
                for(int i=0; i<concertList.length; i++) {
                    if(concertList[i] != null) {
                        if(languageList[j].toUpperCase().equals(concertList[i].getLanguage().toUpperCase())) {
                            concertByLanguage[languageCount] = concertList[i];
                    
                            languageCount++;
                        }
                    }
                }
                concertLanguages.put(languageList[j], concertByLanguage);
            }
        }
        
        // Map for Concert Date
        Concert[] concertByDate = new Concert[concertValidCount];
        int dateCount = 0;
        for(int j=0; j<concertList.length; j++) {
            if(concertList[j] != null) {
                for(int i=0; i<concertList.length; i++) {
                    if(concertList[i] != null) {
                        if(concertList[j].getDatetime().equals(concertList[i].getDatetime())) {
                            concertByDate[dateCount] = concertList[i];
                            
                            dateCount++;
                        }
                    }
                }
                concertDates.put(concertList[j].getDatetime().toString(), concertByDate);
            }
            
        }
        
        // Map for Concert Venue
        Concert[] concertByVenue = new Concert[1000];
        int venueCount = 0;
        for(int j=0; j<concertList.length; j++) {
            if(concertList[j] != null) {
                for(int i=0; i<concertList.length; i++) {
                    if(concertList[i] != null) {
                        if(concertList[j].getVenue().getName().toUpperCase().equals(concertList[i].getVenue().getName().toUpperCase())) {
                            concertByVenue[venueCount] = concertList[i];
                            
                                    
                            venueCount++;
                        }
                    }
                }
                concertVenues.put(concertList[j].getVenue().getName(), concertByVenue);
            }
        }
        
        // Create Catalog Object
        catalog = new Catalog(now, concertTitles, concertArtists, concertLanguages, concertDates, concertVenues);
        
        return catalog;
    }
      
    public static Artist[] initializeArtists() {
        // Variables
        String[] artistDetails;
        int counter = 0;
        String[] artistNameList = new String[1000];
        String[] artistLanguageList = new String[1000];
        String[] artistGenreList = new String[1000];
        
        // Try-Catch get data from artist.txt
        try {
            File artistFile = new File("artist.txt");
            Scanner fileScanner = new Scanner(artistFile);
            String currentLine = fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                artistDetails = currentLine.split("\t");
            
                artistNameList[counter] = artistDetails[0];
                artistLanguageList[counter] = artistDetails[1];
                artistGenreList[counter] = artistDetails[2];
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            artistDetails = currentLine.split("\t");
            
            artistNameList[counter] = artistDetails[0];
            artistLanguageList[counter] = artistDetails[1];
            artistGenreList[counter] = artistDetails[2];
            
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }

        // Create Artist[] Object
        Artist[] artistList = new Artist[counter + 1];
        for(int i=0; i<=counter; i++) {
            artistList[i] = new Artist(artistNameList[i], artistLanguageList[i], artistGenreList[i]);
        }
        
        return artistList;
    }
            
    public static Venue[] initializeVenues() {
        String[] venueDetails;
        int counter = 0;
        String[] venueNameList = new String[1000];
        String[] venueLocationList = new String[1000];
        String[] venueTypeList = new String[1000];
        int[] venueCapacityList = new int[1000];
        
        // Try-Catch get data from venue.txt
        try {
            File venueFile = new File("venue.txt");
            Scanner fileScanner = new Scanner(venueFile);
            String currentLine = fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                venueDetails = currentLine.split("\t");
            
                venueNameList[counter] = venueDetails[0];
                venueLocationList[counter] = venueDetails[1];
                venueTypeList[counter] = venueDetails[2];
                venueCapacityList[counter] = Integer.parseInt(venueDetails[3]);
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            venueDetails = currentLine.split("\t");
            
            venueNameList[counter] = venueDetails[0];
            venueLocationList[counter] = venueDetails[1];
            venueTypeList[counter] = venueDetails[2];
            venueCapacityList[counter] = Integer.parseInt(venueDetails[3]);

            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
        
        // Create Venue[] Object
        Venue[] venueList = new Venue[counter + 1];
        for(int i=0; i<=counter; i++) {
            venueList[i] = new Venue(venueNameList[i], venueLocationList[i], venueTypeList[i], venueCapacityList[i]);
        }

        return venueList;
    }
    
    public static Concert[] initializeConcerts(Artist[] artistList, Venue[] venueList) {
        String[] concertDetails;
        int counter = 0;
        int concertMaxId = 10000;
        String[] concertNameList = new String[concertMaxId];
        Artist[] concertArtistList = new Artist[concertMaxId];
        LocalDateTime[] concertDatetimeList = new LocalDateTime[concertMaxId];
        String[] concertLanguageList = new String[concertMaxId];
        Venue[] concertVenueList = new Venue[concertMaxId];
        boolean[] concertIsTrendingList = new boolean[concertMaxId];
        
        // Try-Catch get data from concert.txt
        try {
            File concertFile = new File("concert.txt");
            Scanner fileScanner = new Scanner(concertFile);
            String currentLine = fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                concertDetails = currentLine.split("\t");
            
                // Concert Name
                concertNameList[counter] = concertDetails[0];
                // Concert Artist
                for(int i=0; i<artistList.length; i++) {
                    if(artistList[i].getId().equals(concertDetails[1])) {
                        concertArtistList[counter] = artistList[i];
                        break;
                    }
                }
                // Concert Datetime
                concertDatetimeList[counter] = LocalDateTime.parse(concertDetails[2]);
                // Concert Language
                concertLanguageList[counter] = concertDetails[3];
                // Concert Venue
                for(int i=0; i<venueList.length; i++) {
                    if(venueList[i].getId().equals(concertDetails[4])) {
                        concertVenueList[counter] = venueList[i];
                        break;
                    }
                }
                // Concert isTrending
                concertIsTrendingList[counter] = Boolean.getBoolean(concertDetails[5]);
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            concertDetails = currentLine.split("\t");
            
            // Concert Name
            concertNameList[counter] = concertDetails[0];
            // Concert Artist
            for(int i=0; i<artistList.length; i++) {
                if(artistList[i].getId().equals(concertDetails[1])) {
                    concertArtistList[counter] = artistList[i];
                    break;
                }
            }
            // Concert Datetime
            concertDatetimeList[counter] = LocalDateTime.parse(concertDetails[2]);
            // Concert Language
            concertLanguageList[counter] = concertDetails[3];
            // Concert Venue
            for(int i=0; i<venueList.length; i++) {
                if(venueList[i].getId().equals(concertDetails[4])) {
                    concertVenueList[counter] = venueList[i];
                    break;
                }
            }
            // Concert isTrending
            concertIsTrendingList[counter] = Boolean.getBoolean(concertDetails[5]);

            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
        
        // Create Concert[] Object
        Concert[] concertList = new Concert[10000];
        for(int i=0; i<=counter; i++) {
            concertList[i] = new Concert(concertNameList[i], concertArtistList[i], concertDatetimeList[i], concertLanguageList[i], concertVenueList[i], concertIsTrendingList[i]);
        }
        
        return concertList;
    }
    
    public static Person[][] initializePerson() {
        int fileLineNumber = (int)countFileLineNumber("user.txt");
        Person[][] usersList = null;   // Person[0][] is Admin users, Person[1][] is Customer users
        Admin[] adminList = new Admin[fileLineNumber];
        Customer[] customerList = new Customer[fileLineNumber];
        
        String[] userDetails;
        int counter = 0;
        String[] userType = new String[fileLineNumber];
        String[] username = new String[fileLineNumber];
        String[] password = new String[fileLineNumber];
        String[] accStatus = new String[fileLineNumber];
        String[] userFirstName = new String[fileLineNumber];
        String[] userLastName = new String[fileLineNumber];
        String[] userAddress = new String[fileLineNumber];
        String[] userEmail = new String[fileLineNumber];
        String[] userPhoneNum = new String[fileLineNumber];
        String[] userJoinedDate = new String[fileLineNumber];
        
        
        // Try-Catch get data from venue.txt
        try {
            File userFile = new File("user.txt");
            Scanner fileScanner = new Scanner(userFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                userDetails = currentLine.split("\t");
                
                userType[counter] = userDetails[0];
                username[counter] = userDetails[1];
                password[counter] = userDetails[2];
                accStatus[counter] = userDetails[3];
                userFirstName[counter] = userDetails[4];
                userLastName[counter] = userDetails[5];
                userAddress[counter] = userDetails[6];
                userEmail[counter] = userDetails[7];
                userPhoneNum[counter] = userDetails[8];
                userJoinedDate[counter] = userDetails[9];

                currentLine = fileScanner.nextLine();        
                counter++;                
            }

            userDetails = currentLine.split("\t");

            userType[counter] = userDetails[0];
            username[counter] = userDetails[1];
            password[counter] = userDetails[2];
            accStatus[counter] = userDetails[3];
            userFirstName[counter] = userDetails[4];
            userLastName[counter] = userDetails[5];
            userAddress[counter] = userDetails[6];
            userEmail[counter] = userDetails[7];
            userPhoneNum[counter] = userDetails[8];
            userJoinedDate[counter] = userDetails[9];

            fileScanner.close();
            
            // Create Admin object & Customer object
            int accStatusLength = AccountStatus.values().length;
            AccountStatus accountStatus;
            for(int i=0; i<counter; i++) {            
                
                if (userType[counter].equals("admin")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            adminList[i] = new Admin(new Account(username[i], password[i], accountStatus), userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i], userJoinedDate[i]);
                        }
                    }                
                } 
                else if (userType[counter].equals("customer")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            customerList[i] = new Customer(new Account(username[i], password[i], accountStatus), userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i], userJoinedDate[i]);
                        }
                    }
                }
            }


        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
        
        usersList[0] = adminList;
        usersList[1] = customerList;
        
        return usersList;
    }

    // Login Methods
    public static boolean Login() {
        Scanner sc = new Scanner(System.in);
        
        // Ask user Login Information
        System.out.println("");
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
    
    // Search Methods
    public static void searchConcert(Artist[] artistList, Concert[] concertList) {
        Scanner sc = new Scanner(System.in);
        
        // Create catalog
        Catalog catalog = createCatalog(artistList, concertList);
        
        // Flag
        boolean exit = false;
        boolean isEqual = false;
        
        // Variable
        Concert[] searchResult = null;
        
        // Title / Heading
        System.out.println("");
        System.out.println("  Search Concert  ");
        System.out.println("==================");
        System.out.println("");
        

        
        // Get searching choice
        while(!exit) {
            // Search Menu
            System.out.println("Search Concert By: ");
            System.out.println("1. Title");
            System.out.println("2. Language");
            System.out.println("3. Date");
            System.out.println("4. Artist");
            System.out.println("5. Venue");
            System.out.println("6. Exit");
            System.out.println("");
        
            System.out.print("Choice: ");
            int searchChoice = Character.getNumericValue(sc.next().charAt(0));
            sc.nextLine();
            System.out.println("");
            
            switch(searchChoice) {
                case 1:
                    // Get Search Name (Concert)
                    System.out.print("Enter Concert Name: ");
                    String searchConcertName = sc.nextLine();
                    System.out.println("");
                    
                    searchResult = catalog.searchByTitle(searchConcertName);
                    
                    // Display the Concerts
                    if(searchResult != null && searchResult[0] != null) {
                        System.out.println("Search Result");
                        listConcertTitle(searchResult);
                    
                        // Ask user to display detail or not
                        System.out.print("Do you want to display detail of the concert?(Y/N): ");
                        char searchDisplayAll = sc.next().toUpperCase().charAt(0);
                        System.out.println("");
                        
                        switch(searchDisplayAll) {
                            case 'Y':
                                boolean isValidNo = false;
                                while(!isValidNo) {
                                    System.out.print("Enter Concert No.: ");
                                    int concertDetailChoice = Character.getNumericValue(sc.next().charAt(0));
                                    // Display selected concert
                                    System.out.println(countValidConcert(searchResult));
                                    if(concertDetailChoice <= countValidConcert(searchResult) && concertDetailChoice != 0) {
                                        System.out.println("");
                                        System.out.println("*Concert Detail*");
                                        searchResult[concertDetailChoice - 1].displayAllDetail();
                                        
                                        isValidNo = true;
                                    } 
                                    else {
                                        System.err.println("Invalid number");
                                        System.out.println("");
                                    }
                                }
                                break;
                            case 'N':
                                break;
                            default:
                                System.out.println("Invalid character!");
                        }
                    }
                    else {
                        System.err.println("We couldn't find a match for \"" + searchConcertName + "\", Do you want to try another search?");
                        System.out.println("(Double check your search for any typo or spelling errors - or try different search term)");
                        System.out.println("");
                    }

                    isEqual = true;
                    
                    // Press anything to continue
                    blankInput();
                    System.out.println("");
                    break;
                case 2:
                    // Get Search Language (Concert)
                    String[] languageList = catalog.getlanguageList();
                    System.out.println("Language Available");
                    for(int i=0; i<catalog.getlanguageList().length; i++) {
                        if(languageList[i] != null)
                            System.out.println(languageList[i] + "\n");
                    }
                    
                    System.out.print("Enter Concert Language: ");
                    String searchConcertLanguage = sc.nextLine();
                    
                    searchResult = catalog.searchByLanguage(searchConcertLanguage);
                    
                    // Display the Concerts
                    displayConcert(searchResult);                    
                    
                    isEqual = true;
                    
                    // Press anything to continue
                    blankInput();
                    break;
                case 3:
                    // Get Search Date (Concert)
                    System.out.print("Enter Concert Date: ");
                    String searchConcertDate = sc.nextLine();
                    
                    searchResult = catalog.searchByDate(searchConcertDate);
                    
                    // Display the Concerts
                    displayConcert(searchResult);                   
                    
                    isEqual = true;
                    
                    // Press anything to continue
                    blankInput();
                    break;
                case 4:
                    // Get Search Artist (Concert)
                    System.out.print("Enter Concert Artist: ");
                    String searchConcertArtist = sc.nextLine();
                    
                    searchResult = catalog.searchByArtist(searchConcertArtist);
                    
                    // Display the Concerts
                    displayConcert(searchResult);                    
                    
                    isEqual = true;
                    
                    // Press anything to continue
                    blankInput();
                    break;
                case 5:
                    // Get Search Venue (Concert)
                    System.out.print("Enter Concert Venue: ");
                    String searchConcertVenue = sc.nextLine();
                    
                    searchResult = catalog.searchByVenue(searchConcertVenue);
                    
                    // Display the Concerts
                    displayConcert(searchResult);                   
                    
                    isEqual = true;
                    
                    // Press anything to continue
                    blankInput();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Error! Please try again.\n");
                    

            }
        }

        if(!isEqual)
            System.err.println("Concert Not Found");

//        for(int i=0; i<concertList.length; i++) {
//            // Check if the concert list contains the search query(concert name)
//            if(concertList[i].getName().toUpperCase().equals(searchConcertName.toUpperCase()) || 
//               concertList[i].getName().toUpperCase().contains(searchConcertName.toUpperCase())) {
//                // Display Concert Detail
//                concertList[i].displayAllDetail();
//                isEqual = true;
//                
//                System.out.print("Do you want to purchase ticket for this concert? (Y/N): ");
//                char isPurchaseTicket = sc.next().toUpperCase().charAt(0);
//                
//                if(isPurchaseTicket == 'Y') {
//                    System.out.println("Purchase Ticket");
//                    // Codes Here
//                }
//                else
//                    break;
//            }
//            else {
//                isEqual = false;
//            }
//        }
//        
//        if(!isEqual)
//            System.err.println("Concert Not Found");
    }
    
    public static int countValidConcert(Concert[] concertList) {
        int count = 0;
        
        for(int i=0; i<concertList.length; i++) {
            if(concertList[i] != null) {
                count++;
            } 
        }
        
        return count;
    }
    
    public static void displayConcertName(int noOfConcert) {
        
    }
    
    public static void displayConcert(Concert[] concertList) {
        for(int i=0; i<concertList.length; i++) {
            if(concertList[i] != null) {
                System.out.println("");
                System.out.println("**Concert " + (i + 1) + "**");
                concertList[i].displayAllDetail();
            }
            
        }
    }
    
    public static void listConcertTitle(Concert[] concertList) {
        for(int i=0; i<concertList.length; i++) {
            if(concertList[i] != null) {
                System.out.print((i+1) + ". ");
                System.out.println(concertList[i].getName());
                System.out.println("");
            }
        }
    }
    
    // Buy Ticket Methods
    
    // General Methods
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
    
    public static void blankInput() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Press any key to continue...");
        sc.nextLine();
    }
    
    public static long countFileLineNumber(String fileName) {
        Path path = Paths.get(fileName);

        long lines = 0;
        try {

            // much slower, this task better with sequence access
            //lines = Files.lines(path).parallel().count();

            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
