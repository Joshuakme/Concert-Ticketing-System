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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh Min En, Shia Chai Fen, Wong Wei Hao
 */
public class ConcertTicketingSystem {
    static Scanner sc = new Scanner(System.in);

    /*
     * Description:
     * The Concert Ticketing System is focus on music concert ticket selling which
     * will be held in Malaysia, so it is basically divided into 3 main parts which
     * are "Concerts", "Tickets", "Customers and orders".
     */
    public static void main(String[] args) {

        // Global States
        boolean isLoggedIn = false;

        // Object Initialization
        Guest guest = new Guest();
        Artist[] artistList = initializeArtists();
        Venue[] venueList = initializeVenues();
        Concert[] concertList = initializeConcerts(artistList, venueList);
        Person[][] userList = initializePerson(); // userList[0][] is Admin list, userList[1][] is Customer list

        // Welcome User
        startScreen();

        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int menuChoice = Character.getNumericValue(sc.next().charAt(0));
            System.out.println("");

            switch (menuChoice) {
                case 1: // Search Concert
                    searchConcert(artistList, venueList, concertList);
                    break;
                case 2: // View Trending

                    viewTrending(concertList);
                    break;
                case 3: // Buy Ticket
                    boolean isConfirmed = false;
                    // Display Concert details in list table
                    displayConcertList(concertList);

                    while (!isConfirmed) {
                        System.out
                                .print("Please Enter Your Preference Concert Show (1 - " + concertList.length + "): ");
                        int choice = Character.getNumericValue(sc.next().charAt(0));

                        if (choice > concertList.length || choice < 0) {
                            System.out.println("************************");
                            System.out.println("PLEASE ENTER 1 - " + concertList.length + " ONLY! ");
                            System.out.println("************************");
                        }

                        else if (choice > 0 && choice <= concertList.length) {
                            isConfirmed = true;
                            System.out.println("Are you sure? (Y for yes, N for no): ");
                            char confirmation = sc.next().toUpperCase().charAt(0);
                            if (confirmation == 'Y') {
                                concertList[choice - 1].getVenue().getName();

                                for (int i = 0; i < venueList.length; i++) {
                                    if (concertList[choice - 1].getVenue().getName().equals(venueList[i].getName())) {
                                        displayVenue(venueList, venueList[i].getName());
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    // Check Login Status
                    if (!isLoggedIn) {
                        System.out.println("You are not signed in. Please sign in before buy ticket.");

                        if (Login())
                            isLoggedIn = true;

                        // Get user detail (username, password, accStatus)
                        // guest.registerAccount("username", "password", "accStatus");
                    }

                    // Display seat status(booked / empty) [table maybe]
                    // Ask for detail (no, ticketCat, etc.)

                case 4: // Login/Register
                    System.out.println("---------");
                    System.out.println("| Login |");
                    System.out.println("---------\n");
                    Person user = null;

                    System.out.println("Are you a new user?(Y/N)");
                    char isNewUser = Character.valueOf(sc.nextLine().charAt(0));

                    if (isNewUser == 'n' || isNewUser == 'N') {
                        System.out.println("Login\n");
                        System.out.print("Enter your username: ");
                        String username = sc.nextLine();
                        System.out.print("Enter your password: ");
                        String password = sc.nextLine();

                        for (int i = 0; i < userList.length; i++)
                            for (int j = 0; j < userList[i].length; j++) {
                                System.out.print(userList[i][j]);
                                if (username.equals(userList[i][j].getAccount().getUsername()))
                                    if (password.equals(userList[i][j].getAccount().getPassword()))
                                        if (i == 0) {
                                            user = (Admin) userList[i][j];
                                            System.out.println("Login succesfully as an admin");
                                            isLoggedIn = true;
                                        } else if (i == 1) {
                                            user = (Customer) userList[i][j];
                                            System.out.println("Login succesfully as a customer");
                                            isLoggedIn = true;
                                        }
                                break;
                            }
                    }

                    else if (isNewUser == 'y' || isNewUser == 'Y') {
                        System.out.println("-----------");
                        System.out.println("| Register |");
                        System.out.println("-----------\n");
                        System.out.print("Enter your first name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter your last name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter your prefered username: ");
                        String newUsername = sc.nextLine();
                        System.out.print("Enter your prefered password: ");
                        String newPassword = sc.nextLine();
                        System.out.print("Enter your phone number: ");
                        String phone = sc.nextLine();
                        System.out.print("Enter your email: ");
                        String email = sc.nextLine();

                        Customer newUser = new Customer(new Account(newUsername, newPassword, AccountStatus.ACTIVE),
                                firstName, lastName, "", phone, email);
                        isLoggedIn = true;

                        System.out.println("Welcome: " + newUser.getAccount().getUsername());
                    }

                    else
                        System.out.println("Invalid character, only Y/N is acceptable\n");
                    break;

                // Remember to use userList[][] (Line 37) to check credentials

                // Login/Register

                // Menu

                // User input username & password

                //
                case 5: // Other
                    Order order = new Order("O001", 2, LocalDate.now(), OrderStatus.PENDING,
                            new Ticket("T001", "C001", "S001", LocalDate.now()));

                    System.out.println("---------");
                    System.out.println("| Other |");
                    System.out.println("---------");
                    boolean quit = false;
                    while (!quit) {
                        System.out.println("1.Change Order");
                        System.out.println("2.View Order");
                        System.out.println("3.Cancel Order");
                        System.out.println("4.Exit");
                        System.out.println("Select your option:(1/2/3/4)");

                        int otherChoice = sc.nextInt();
                        System.out.println("");

                        if (otherChoice == 1) {
                            // Change Order
                        } else if (otherChoice == 2) {
                            // View Order
                            order.displayOrder();
                        } else if (otherChoice == 3) {
                            // Cancel order
                            order.cancelOrder();
                        } else if (otherChoice == 4) {
                            // exit
                            quit = true;
                        } else
                            System.out.println("Invalid option");
                    }

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

    public static void displayMainMenu() {
        String[] custMenu = { "Search Concert", "View Trending", "Buy Ticket", "Login/Register", "Other", "Exit" };
        System.out.println("Menu: ");

        for (int i = 0; i < custMenu.length; i++) {
            System.out.printf("%-3s%-20s\n", (i + 1) + ".", custMenu[i]);
        }
        System.out.print("Select the menu (num): ");
    }

    // Data Initialization Methods (from txt files)
    public static Catalog createCatalog(Artist[] artistList, Venue[] venueList, Concert[] concertList) {
        Catalog catalog;
        final Map<String, List<Concert>> concertTitles = new HashMap<>();
        final Map<String, List<Concert>> concertArtists = new HashMap<>();
        final Map<String, List<Concert>> concertLanguages = new HashMap<>();
        final Map<String, List<Concert>> concertDates = new HashMap<>();
        final Map<String, List<Concert>> concertVenues = new HashMap<>();

        // Get Current Date
        LocalDate now = LocalDate.now();

        // Map for Concert Titles
        for (int j = 0; j < concertList.length; j++) {
            List<Concert> concertByTitle = new ArrayList<>();

            for (int i = 0; i < concertList.length; i++) {
                if (concertList[j].getName().toUpperCase().equals(concertList[i].getName().toUpperCase())) {
                    concertByTitle.add(concertList[i]);
                }
            }
            concertTitles.put(concertList[j].getName(), concertByTitle);
        }

        // Map for Concert Artists
        for (int j = 0; j < artistList.length; j++) {
            List<Concert> concertByArtist = new ArrayList<>();

            for (int i = 0; i < concertList.length; i++) {
                if (artistList[j].getName().toUpperCase().equals(concertList[i].getArtist().getName().toUpperCase())) {
                    concertByArtist.add(concertList[i]);
                }
            }
            concertArtists.put(artistList[j].getName(), concertByArtist);
        }

        // Map for Concert Language
        String[] languageList = { "Cantonese", "English", "Mandarin", "Korean" };

        for (int j = 0; j < languageList.length; j++) {
            List<Concert> concertByLanguage = new ArrayList<>();

            for (int i = 0; i < concertList.length; i++) {
                if (concertList[i] != null) {
                    if (languageList[j].toUpperCase().equals(concertList[i].getLanguage().toUpperCase())) {
                        concertByLanguage.add(concertList[i]);
                    }
                }
            }
            concertLanguages.put(languageList[j], concertByLanguage);
        }

        // Map for Concert Date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> rawDateList = new ArrayList<>(); // Use List instead of Array because the size is unknown
        List<String> uniqueDateList = new ArrayList<>(); // Same here

        // Get rawDateList
        for (int i = 0; i < concertList.length; i++) {
            rawDateList.add(concertList[i].getDatetime().format(dateFormat));
        }

        // Filter out duplicate date
        for (int i = 0; i < rawDateList.size(); i++) {
            boolean isDuplicated = false; // flag to determine the value is duplicate or not

            for (int j = 0; j < i; j++) {
                if (rawDateList.get(i).equals(rawDateList.get(j))) { // Skip those duplicated date
                    isDuplicated = true;
                    break; // skip to next outer loop if there is ANY ONE duplicate
                }
            }
            // copy date to uniqueList
            if (!isDuplicated) {
                uniqueDateList.add(rawDateList.get(i));
            }
        }

        // Insert concert[i] object into MAP according to the date
        for (int j = 0; j < uniqueDateList.size(); j++) {
            List<Concert> concertByDate = new ArrayList<>();

            for (int i = 0; i < concertList.length; i++) {
                String formattedDateStr = concertList[i].getDatetime().format(dateFormat);

                if (uniqueDateList.get(j).equals(formattedDateStr)) {
                    concertByDate.add(concertList[i]);
                }
            }
            concertDates.put(uniqueDateList.get(j), concertByDate);
        }

        // Map for Concert Venue
        String[] venueNameList = new String[venueList.length];

        for (int i = 0; i < venueList.length; i++) {
            venueNameList[i] = venueList[i].getName();
        }

        List<Concert> concertByVenue = new ArrayList<>();
        for (int j = 0; j < concertList.length; j++) {
            if (concertList[j] != null) {
                for (int i = 0; i < venueNameList.length; i++) {
                    if (concertList[j].getVenue().getName().toUpperCase().equals(venueNameList[i].toUpperCase())) {
                        concertByVenue.add(concertList[i]);
                    }
                    concertVenues.put(venueNameList[i], concertByVenue);
                }

            }
        }

        // Create Catalog Object
        catalog = new Catalog(now, concertTitles, concertArtists, concertLanguages, concertDates, concertVenues);

        return catalog;
    }

    public static Artist[] initializeArtists() {
        int fileLineNumber = (int) countFileLineNumber("artist.txt");

        // Variables
        String[] artistDetails;
        int counter = 0;
        String[] artistNameList = new String[fileLineNumber];
        String[] artistLanguageList = new String[fileLineNumber];
        String[] artistGenreList = new String[fileLineNumber];

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
        Artist[] artistList = new Artist[fileLineNumber];
        for (int i = 0; i <= counter; i++) {
            artistList[i] = new Artist(artistNameList[i], artistLanguageList[i], artistGenreList[i]);
        }

        return artistList;
    }

    public static Venue[] initializeVenues() {
        int fileLineNumber = (int) countFileLineNumber("venue.txt");

        String[] venueDetails;
        int counter = 0;
        String[] venueNameList = new String[fileLineNumber];
        String[] venueLocationList = new String[fileLineNumber];
        String[] venueTypeList = new String[fileLineNumber];
        int[] venueCapacityList = new int[fileLineNumber];

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
        Venue[] venueList = new Venue[fileLineNumber];
        for (int i = 0; i <= counter; i++) {
            venueList[i] = new Venue(venueNameList[i], venueLocationList[i], venueTypeList[i], venueCapacityList[i]);
        }

        return venueList;
    }

    public static Concert[] initializeConcerts(Artist[] artistList, Venue[] venueList) {
        int fileLineNumber = (int) countFileLineNumber("concert.txt");

        String[] concertDetails;
        int counter = 0;
        String[] concertNameList = new String[fileLineNumber];
        Artist[] concertArtistList = new Artist[fileLineNumber];
        LocalDateTime[] concertDatetimeList = new LocalDateTime[fileLineNumber];
        String[] concertLanguageList = new String[fileLineNumber];
        Venue[] concertVenueList = new Venue[fileLineNumber];
        boolean[] concertIsTrendingList = new boolean[fileLineNumber];

        // Try-Catch get data from concert.txt
        try {
            File concertFile = new File("concert.txt");
            Scanner fileScanner = new Scanner(concertFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                concertDetails = currentLine.split(";");

                // Concert Name
                concertNameList[counter] = concertDetails[0];
                // Concert Artist
                for (int i = 0; i < artistList.length; i++) {
                    if (artistList[i].getId().equals(concertDetails[1])) {
                        concertArtistList[counter] = artistList[i];
                        break;
                    }
                }
                // Concert Datetime
                concertDatetimeList[counter] = LocalDateTime.parse(concertDetails[2]);
                // Concert Language
                concertLanguageList[counter] = concertDetails[3];
                // Concert Venue
                for (int i = 0; i < venueList.length; i++) {
                    if (venueList[i].getId().equals(concertDetails[4])) {
                        concertVenueList[counter] = venueList[i];
                        break;
                    }
                }
                // Concert isTrending
                concertIsTrendingList[counter] = Boolean.parseBoolean(concertDetails[5]);

                currentLine = fileScanner.nextLine();
                counter++;
            }

            concertDetails = currentLine.split(";");

            // Concert Name
            concertNameList[counter] = concertDetails[0];
            // Concert Artist
            for (int i = 0; i < artistList.length; i++) {
                if (artistList[i].getId().equals(concertDetails[1])) {
                    concertArtistList[counter] = artistList[i];
                    break;
                }
            }
            // Concert Datetime
            concertDatetimeList[counter] = LocalDateTime.parse(concertDetails[2]);
            // Concert Language
            concertLanguageList[counter] = concertDetails[3];
            // Concert Venue
            for (int i = 0; i < venueList.length; i++) {
                if (venueList[i].getId().equals(concertDetails[4])) {
                    concertVenueList[counter] = venueList[i];
                    break;
                }
            }
            // Concert isTrending
            concertIsTrendingList[counter] = Boolean.parseBoolean(concertDetails[5]);

            fileScanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }

        // Create Concert[] Object
        Concert[] concertList = new Concert[fileLineNumber];
        for (int i = 0; i <= counter; i++) {
            concertList[i] = new Concert(concertNameList[i], concertArtistList[i], concertDatetimeList[i],
                    concertLanguageList[i], concertVenueList[i], concertIsTrendingList[i]);
        }

        return concertList;
    }

    public static Person[][] initializePerson() {
        int fileLineNumber = (int) countFileLineNumber("user.txt");
        Person[][] usersList = new Person[2][]; // Person[0][] is Admin users, Person[1][] is Customer users
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
        LocalDate[] userJoinedDate = new LocalDate[fileLineNumber];

        // Try-Catch get data from venue.txt
        try {
            File userFile = new File("user.txt");
            Scanner fileScanner = new Scanner(userFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                userDetails = currentLine.split(";");

                userType[counter] = userDetails[0];
                username[counter] = userDetails[1];
                password[counter] = userDetails[2];
                accStatus[counter] = userDetails[3];
                userFirstName[counter] = userDetails[4];
                userLastName[counter] = userDetails[5];
                userAddress[counter] = userDetails[6];
                userEmail[counter] = userDetails[7];
                userPhoneNum[counter] = userDetails[8];
                userJoinedDate[counter] = LocalDate.parse(userDetails[9]);

                currentLine = fileScanner.nextLine();
                counter++;
            }

            userDetails = currentLine.split(";");
            userType[counter] = userDetails[0];
            username[counter] = userDetails[1];
            password[counter] = userDetails[2];
            accStatus[counter] = userDetails[3];
            userFirstName[counter] = userDetails[4];
            userLastName[counter] = userDetails[5];
            userAddress[counter] = userDetails[6];
            userEmail[counter] = userDetails[7];
            userPhoneNum[counter] = userDetails[8];
            userJoinedDate[counter] = LocalDate.parse(userDetails[9]);

            fileScanner.close();

            // Create Admin object & Customer object
            int accStatusLength = AccountStatus.values().length;
            AccountStatus accountStatus;
            for (int i = 0; i < counter; i++) {

                if (userType[counter].equals("admin")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            adminList[i] = new Admin(new Account(username[i], password[i], accountStatus),
                                    userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i]);
                        }
                    }
                } else if (userType[counter].equals("customer")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            customerList[i] = new Customer(new Account(username[i], password[i], accountStatus),
                                    userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i]);
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

    // 1. Search Concert Methods (Joshua)
    public static void searchConcert(Artist[] artistList, Venue[] venueList, Concert[] concertList) {
        // Create catalog
        Catalog catalog = createCatalog(artistList, venueList, concertList);

        // Flag
        boolean exit = false;
        boolean isEqual = false;

        // Variable
        List<Concert> searchResult = new ArrayList<>();

        // Title / Heading
        System.out.println("");
        System.out.println("  Search Concert  ");
        System.out.println("==================");
        System.out.println("");

        // Get searching choice
        while (!exit) {
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
            System.out.println("");

            switch (searchChoice) {
                case 1: // Get Search Name (Concert)
                    searchConcertName(catalog);
                    break;
                case 2: // Get Search Language (Concert)
                    searchConcertLang(catalog);
                    break;
                case 3: // Get Search Date (Concert)
                    searchConcertDate(catalog);
                    break;
                case 4: // Get Search Artist (Concert)

                    System.out.print("Enter Concert Artist: ");
                    String searchConcertArtist = sc.nextLine();

                    // searchResult.add(catalog.searchByArtist(searchConcertArtist));

                    // Display the Concerts
                    displayConcert(searchResult);

                    isEqual = true;

                    // Press anything to continue
                    blankInput();
                    break;
                case 5: // Get Search Venue (Concert)
                    System.out.print("Enter Concert Venue: ");
                    String searchConcertVenue = sc.nextLine();

                    // searchResult = catalog.searchByVenue(searchConcertVenue);

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

        if (!isEqual)
            System.err.println("Concert Not Found");
    }

    public static void searchConcertName(Catalog catalog) {
        List<Concert> searchResult = new ArrayList<>();

        // Get Search Name (Concert)
        System.out.print("Enter Concert Name: ");
        String searchConcertName = sc.nextLine();
        System.out.println("");

        searchResult = catalog.searchByTitle(searchConcertName);

        // Display the Concerts
        if (searchResult != null) {
            System.out.println("Search Result");
            displayConcertTitle(searchResult);

            // Ask user to display detail or not
            System.out.print("Do you want to display detail of the concert?(Y/N): ");
            char searchDisplayAllChoice = sc.next().toUpperCase().charAt(0);
            System.out.println("");

            switch (searchDisplayAllChoice) {
                case 'Y':
                    boolean isValidNo = false;
                    while (!isValidNo) {
                        System.out.print("Enter Concert No.: ");
                        int concertDetailChoice = Character.getNumericValue(sc.next().charAt(0));
                        // Display selected concert
                        if (concertDetailChoice > 0 && concertDetailChoice <= searchResult.size()) {
                            System.out.println("");
                            System.out.println("*Concert Detail*");
                            searchResult.get(concertDetailChoice - 1).displayAllDetail();

                            isValidNo = true;
                        } else {
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
        } else {
            System.err.println(
                    "We couldn't find a match for \"" + searchConcertName + "\", Do you want to try another search?");
            System.out.println(
                    "(Double check your search for any typo or spelling errors - or try different search term)");
            System.out.println("");
        }

        // Press anything to continue
        blankInput();
        System.out.println("");
    }

    public static void searchConcertLang(Catalog catalog) {
        List<Concert> searchResult = null;

        // Get Search Language (Concert)
        String[] languageTitleList = catalog.getlanguageTitleList();
        System.out.println("Language Available");
        for (int i = 0; i < languageTitleList.length; i++) {
            if (languageTitleList[i] != null)
                System.out.println((i + 1) + ". " + languageTitleList[i]);
        }
        System.out.println("");

        // Get user input for search language
        boolean isValidNo = false;
        String searchConcertLanguage = null;

        // Input Validation
        while (!isValidNo) {
            System.out.print("Select Concert Language: ");
            int searchConcertLanguageChoice = Character.getNumericValue(sc.next().charAt(0));

            if (searchConcertLanguageChoice > 0 && searchConcertLanguageChoice <= languageTitleList.length) {
                for (int i = 0; i < languageTitleList.length; i++) {
                    if (searchConcertLanguageChoice == (i + 1)) {
                        searchConcertLanguage = languageTitleList[i];
                    }
                }

                isValidNo = true;
            } else {
                System.out.println("");
                System.err.println("Invalid number");
                System.out.println("");
            }
        }

        searchResult = catalog.searchByLanguage(searchConcertLanguage);

        // Display the Concerts
        displayConcert(searchResult);

        // Press anything to continue
        blankInput();
    }

    public static void searchConcertDate(Catalog catalog) {
        List<Concert> searchResult = new ArrayList<>();

        // Get user input for search language
        boolean isValidDate = false;
        String searchConcertDate = null;

        // Input Validation
        while (!isValidDate) {
            // Get search Date
            System.out.print("Enter Concert Date(yyyy-mm-dd): ");
            searchConcertDate = sc.nextLine();

            if (checkValidDate(searchConcertDate)) {
                // Get search result from Catalog
                searchResult = catalog.searchByDate(searchConcertDate);

                if (searchResult != null) {
                    // Display the Concerts
                    displayConcert(searchResult);

                    // Press anything to continue
                    blankInput();

                    isValidDate = true;
                } else {
                    System.out.println("");
                    System.err.println("We couldn't find a match for \"" + searchConcertDate
                            + "\", Do you want to try another search?");
                    System.out.println("");
                }
            } else {
                System.out.println("");
                System.err.println("Invalid date. Please check the date format is in (yyyy-mm-dd).");
                System.out.println("");
            }
        }
    }

    public static void searchConcertArtist(Catalog catalog) {

    }

    public static void displayConcert(Concert[] concertList) {
        int count = 1;
        for (int i = 0; i < concertList.length; i++) {
            if (concertList[i] != null) {
                System.out.println("");
                System.out.println("**Concert " + (count) + "**");
                concertList[i].displayAllDetail();

                count++;
            }
        }
    }

    public static void displayConcert(List<Concert> concertList) {
        int count = 1;
        for (int i = 0; i < concertList.size(); i++) {
            if (concertList.get(i) != null) {
                System.out.println("");
                System.out.println("**Concert " + (count) + "**");
                concertList.get(i).displayAllDetail();

                count++;
            }
        }
    }

    public static void displayConcertTitle(Concert[] concertList) {
        for (int i = 0; i < concertList.length; i++) {
            if (concertList[i] != null) {
                System.out.print((i + 1) + ". ");
                System.out.println(concertList[i].getName());
                System.out.println("");
            }
        }
    }

    public static void displayConcertTitle(List<Concert> concertList) {
        for (int i = 0; i < concertList.size(); i++) {
            System.out.print((i + 1) + ". ");
            System.out.println(concertList.get(i).getName());
        }
        System.out.println("");
    }

    // 2. View Trending Methods (Joshua)
    public static void viewTrending(Concert[] concertList) {
        System.out.println("View Trending");
        List<Concert> featuredConcerts = getFeaturedConcert(concertList); // Load Featured Concerts
        displayConcertTitle(featuredConcerts);

        // Ask user to display detail or not
        boolean isValidChoice = false;

        if (featuredConcerts.size() != 0) {
            while (!isValidChoice) {
                System.out.print("Do you want to display detail of the concert?(Y/N): ");
                char searchDisplayAllChoice = sc.next().toUpperCase().charAt(0);
                // System.out.println("");

                switch (searchDisplayAllChoice) {
                    case 'Y':
                        isValidChoice = true;

                        boolean isValidNo = false;
                        while (!isValidNo) {
                            System.out.print("Enter Concert No.: ");
                            int concertDetailChoice = Character.getNumericValue(sc.next().charAt(0));

                            // Display selected concert
                            if (concertDetailChoice > 0 && concertDetailChoice <= featuredConcerts.size()) {
                                System.out.println("");
                                System.out.println("*Concert Detail*");
                                featuredConcerts.get(concertDetailChoice - 1).displayAllDetail();

                                isValidNo = true;
                            } else {
                                System.err.println("Invalid number");
                                System.out.println("");
                            }
                        }
                        break;
                    case 'N':
                        isValidChoice = true;
                        break;
                    default:
                        System.out.println("Invalid character!");
                }
            }
        } else {
            System.err.println("Sorry, no featured concerts found.");
            System.out.println("");
        }
    }

    public static List<Concert> getFeaturedConcert(Concert[] concertList) {
        List<Concert> featuredConcerts = new ArrayList<>();

        // Get Featured Concerts List
        for (int i = 0; i < concertList.length; i++) {
            if (concertList[i].isTrending()) {
                featuredConcerts.add(concertList[i]);
            }
        }
        return featuredConcerts;
    }

    // 3. Buy Ticket Methods (Tiffany)
    public static void displayConcertList(Concert[] concertList) {
        // Display Concert List
        System.out.println(
                "------------------------------------------------------------------------------------|----------------------------------|");
        System.out.println(
                "| NO |       DATE & TIME       |                     CONCERT TITLE                  |              VENUE               |");
        System.out.println(
                "|----|-------------------------|----------------------------------------------------|----------------------------------|");
        for (int i = 0; i < concertList.length; i++) {
            System.out.printf("| %2d | %18s | %50s | %30s |\n", (i + 1),
                    centerString(23, formatDateTime(concertList[i].getDatetime(), "yyyy-MM-dd kk:mm")),
                    centerString(50, concertList[i].getName()), centerString(32, concertList[i].getVenue().getName()));
            System.out.println(
                    "|----|-------------------------|----------------------------------------------------|----------------------------------|");
        }
    }

    public static void displayVenue(Venue[] venueList, String venueName) {
        String mapFileName = null;

        for (int i = 0; i < venueList.length; i++) {
            if (venueName.equals(venueList[i].getName())) {
                mapFileName = "Map(" + venueList[i].getName() + ").txt";
            }
        }

        // Try-Catch get data from artist.txt
        try {
            File mapFile = new File(mapFileName);
            Scanner fileScanner = new Scanner(mapFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                System.out.println(currentLine);

                currentLine = fileScanner.nextLine();
            }

            System.out.println(currentLine);

            fileScanner.close(); // Close file
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
    }

    // 4. Login Methods (Wei Hao)
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

        if (isLogged) {
            System.out.println("Welcome! You are logged in!\n");
            sc.close(); // Close scanner
            return true;
        } else {
            System.out.println("Incorrect credentials, please try again!\n");
            sc.close(); // Close scanner
            return false;
        }
    }

    public static boolean checkCredential(String inputUsername, String inputPwd) {
        // Login Credentials
        String[] usernameList = { "taruc", "admin" };
        String[] pwdList = { "taruc", "0000" };
        boolean isEqual = false;

        // Check Empty inputs
        if (inputUsername.equals("") || inputPwd.equals(""))
            return false;

        for (int i = 0; i < usernameList.length; i++) {
            if (inputUsername.equals(usernameList[i]) && inputPwd.equals(pwdList[i]))
                isEqual = true;
        }

        return isEqual;
    }

    // General Methods
    public static String formatDateTime(LocalDateTime datetime, String datetimeFormat) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        return datetime.format(format);
    }

    public static String formatDate(LocalDate date, String dateFormat) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = today.format(format);

        return formattedDate;
    }

    public static String formatTime(LocalTime time, String timeFormat) {
        LocalTime now = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");

        String formattedTime = now.format(format);

        return formattedTime;
    }

    public static void clearScreen() {
        System.out.println("\n\n");
        System.out.println("Console cleared...");
    }

    public static void blankInput() {
        System.out.print("Press any key to continue...");
        sc.nextLine();
    }

    public static long countFileLineNumber(String fileName) {
        Path path = Paths.get(fileName);

        long lines = 0;
        try {
            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static boolean checkValidDate(String dateStr) {
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(dateStr, datetimeFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
