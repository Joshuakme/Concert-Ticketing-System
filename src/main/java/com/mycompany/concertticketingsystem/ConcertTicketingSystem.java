package com.mycompany.concertticketingsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

        // Object Initialization (Database)
        Artist[] artistList = initializeArtists();
        Venue[] venueList = initializeVenues();
        Concert[] concertList = initializeConcerts(artistList, venueList);
        List<Person>[] userList = initializePerson(); // userList[0][] is Admin list, userList[1][] is Customer list
        List<VenueSeatCat>[] venueSeatCatList = initializeVenueSeatCat(venueList);
        List<ShowSeatCat>[] showSeatCatList = initializeSeatCategory(venueList);

        Person currentUser = null;
        Customer currentCustomer = null;
        Admin currentAdmin = null;
        List<Order> currentUserOrderList = new ArrayList<>();

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
                    // Get selected concert
                    Concert selectedConcert = getSelectedConcert(concertList, venueList);

                    // Check Login Status
                    if (!isLoggedIn) {
                        System.out.println("You are not logged in!");
                        System.out.println("\n");
                        currentUser = loginRegister(userList); // Login/Register

                        if (currentUser != null) {
                            isLoggedIn = true;
                        } else {
                            break;
                        }
                    }

                    if (currentUser instanceof Customer) {
                        System.out.println("Your are a Customer!");
                        // Display Venue Map
                        for (int i = 0; i < venueList.length; i++) {
                            if (selectedConcert.getVenue().getName().equals(venueList[i].getName())) {
                                displayVenue(venueList, venueList[i].getName());
                                break;
                            }
                        }

                        currentCustomer
                                .makeNewOrder(orderConcert(currentCustomer, selectedConcert, venueList, showSeatCatList,
                                        venueSeatCatList));

                        // Print ticket

                        // Payment

                        // Display seat status(booked / empty) [table maybe]
                        // Ask for detail (no, ticketCat, etc.)
                    } else if (currentUser instanceof Admin) {
                        System.out.println("You are an Admin!\nYou are not allowed to buy concert tickets.");
                    }

                    break;
                case 4: // Login/Register
                    if (currentUser == null || !isLoggedIn) { // Check if user is loggedIn
                        currentUser = loginRegister(userList); // Login/Register

                        if (currentUser != null) {
                            if (currentUser instanceof Customer) {
                                currentCustomer = (Customer) currentUser;
                            } else if (currentUser instanceof Admin) {
                                currentAdmin = (Admin) currentUser;
                            }
                            isLoggedIn = true;
                        }
                    } else {
                        System.out.println("You are already logged in!");
                        System.out.println("\n");
                    }
                    break;
                case 5: // Other
                    // Order order = new Order("O001", 2, LocalDate.now(), OrderStatus.PENDING,
                    // new Ticket("T001", concertList[0], new ShowSeat(new VenueSeatCat("VIP", 100),
                    // 2, 'A', "B1"),
                    // LocalDate.now()));

                    System.out.println("---------");
                    System.out.println("| Other |");
                    System.out.println("---------");

                    if (currentUser instanceof Customer) {
                        boolean quit = false;
                        while (!quit) {
                            System.out.println("1.View Order");
                            System.out.println("2.Cancel Order");
                            System.out.println("3.Exit");
                            System.out.print("Select your option(1/2/3): ");

                            int otherChoice = sc.nextInt();
                            System.out.println("");

                            if (otherChoice == 1) {
                                // View Order
                                for (int i = 0; i < currentCustomer.getOrderArr().size(); i++) {
                                    if (currentCustomer.getOrderArr().get(i) != null)
                                        currentCustomer.getOrderArr().get(i).displayOrder();
                                }

                            } else if (otherChoice == 2) {
                                // Cancel order
                                for (int i = 0; i < currentCustomer.getOrderArr().size(); i++) {
                                    if (currentCustomer.getOrderArr().get(i) != null)
                                        System.out.print(i + 1 + ". ");
                                    System.out.println(currentCustomer.getOrderArr().get(i).getOrderNumber());
                                    System.out.println(
                                            currentCustomer.getOrderArr().get(i).getTicket().getConcert().getName());
                                }

                                System.out.println("Select the order you want to cancel: ");

                                if (currentCustomer.getOrderArr().size() > 0) {
                                    for (int i = 0; i < currentCustomer.getOrderArr().size(); i++) {
                                        if (currentCustomer.getOrderArr().get(i) != null)
                                            currentCustomer.getOrderArr().get(i).cancelOrder();
                                    }
                                } else {
                                    System.out.println("You have no order yet");
                                }

                            } else if (otherChoice == 3) {
                                // exit
                                quit = true;
                            } else
                                System.out.println("Invalid option");
                        }
                    } else if (currentUser instanceof Admin) {
                        System.out.println("Your are admin...");
                    } else {
                        System.out.println("You are not logged in");
                        break;
                    }

                    break;
                case 6: // Sign Out
                    signOut();
                    currentUser = null;
                    break;
                case 7: // Exit
                    exit = true;
                    System.out.println("Successfully Exited");
                    break;
                default:
                    System.out.println("\nError!\n");
            }
        }
    }

    // Methods & Functions
    public static void startScreen() {
        System.out.println("Welcome to Concert Ticketing System!\n");
    }

    public static void displayMainMenu() {
        String[] custMenu = { "Search Concert", "View Trending", "Buy Ticket", "Login/Register", "Other", "Sign out",
                "Exit" };
        System.out.println("Menu: ");

        for (int i = 0; i < custMenu.length; i++) {
            System.out.printf("%-3s%-20s\n", (i + 1) + ".", custMenu[i]);
        }
        System.out.print("Select the menu (num): ");
    }

    // Data Initialization Methods (from txt files)
    public static Catalog createCatalog(Artist[] artistList, Venue[] venueList, Concert[] concertList) {
        Catalog catalog;
        Map<String, List<Concert>> concertTitles = new HashMap();
        Map<String, List<Concert>> concertArtists = new HashMap();
        Map<String, List<Concert>> concertLanguages = new HashMap();
        Map<String, List<Concert>> concertDates = new HashMap();
        Map<String, List<Concert>> concertVenues = new HashMap();

        // Get Current Date
        LocalDate now = LocalDate.now();

        // Map for Concert Titles
        for (int j = 0; j < concertList.length; j++) {
            List<Concert> concertByTitle = new ArrayList<Concert>();

            for (int i = 0; i < concertList.length; i++) {
                if (concertList[j].getName().toUpperCase().equals(concertList[i].getName().toUpperCase())) {
                    concertByTitle.add(concertList[i]);
                    break;
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
        for (int i = 0; i < venueList.length; i++) {
            List<Concert> concertByVenue = new ArrayList<>();

            for (int j = 0; j < concertList.length; j++) {
                if (venueList[i].equals(concertList[j].getVenue())) {
                    concertByVenue.add(concertList[j]);
                }
            }
            concertVenues.put(venueList[i].getName(), concertByVenue);
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

    public static List<Person>[] initializePerson() {
        int fileLineNumber = (int) countFileLineNumber("user.txt");
        List<Person>[] usersList = new List[2]; // Person[0][] is Admin users, Person[1][] is Customer users
        List<Person> adminList = new ArrayList<>();
        List<Person> customerList = new ArrayList<>();

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

        // Try-Catch get data from user.txt
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
            for (int i = 0; i < fileLineNumber; i++) {
                if (userType[i].equalsIgnoreCase("admin")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            adminList.add(new Admin(new Account(username[i], password[i], accountStatus),
                                    userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i]));
                        }
                    }
                } else if (userType[i].equalsIgnoreCase("customer")) {
                    for (int j = 0; j < accStatusLength; j++) {
                        if (accStatus[i].toUpperCase().equals(AccountStatus.values()[j].toString())) {
                            List<Order> orderList = new ArrayList<>();
                            accountStatus = AccountStatus.valueOf(accStatus[i].toUpperCase());
                            customerList.add(new Customer(orderList,
                                    new BankCard(userLastName[i], "Visa", 10101010, "23/27", 109, 2500,
                                            LocalDate.now()),
                                    new Account(username[i], password[i], accountStatus),
                                    userFirstName[i], userLastName[i], userAddress[i], userEmail[i], userPhoneNum[i]));
                        }
                    }

                }
            }

        } catch (

        FileNotFoundException ex) {
            System.err.println("File does not exist!\n");
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
            sc.nextLine(); // Read new line character "\n"
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
                    searchConcertArtist(catalog);
                    break;
                case 5: // Get Search Venue (Concert)
                    searchConcertVenue(catalog, venueList);

                    break;
                case 6:
                    exit = true;
                    isEqual = true;
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
            sc.nextLine();
            System.out.println("");

            switch (searchDisplayAllChoice) {
                case 'Y':
                    boolean isValidNo = false;
                    while (!isValidNo) {
                        if (searchResult.size() == 1) {
                            System.out.println("");
                            System.out.println("*Concert Detail*");
                            searchResult.get(0).displayAllDetail();

                            isValidNo = true;
                            break;
                        }

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
    }

    public static void searchConcertLang(Catalog catalog) {
        List<Concert> searchResult = new ArrayList<>();

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
            sc.nextLine();

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
        // Get Search Concert
        System.out.print("Enter Concert Artist: ");
        String searchConcertArtist = sc.nextLine();

        List<Concert> searchResult = catalog.searchByArtist(searchConcertArtist);

        // searchResult.add(catalog.searchByArtist(searchConcertArtist));

        // Display the Concerts
        if (searchResult != null) {
            // Display the Concerts
            System.out.println("Search Result");
            displayConcertTitle(searchResult);

            // Ask user to display detail or not
            System.out.print("Do you want to display detail of the concert?(Y/N): ");
            char searchDisplayAllChoice = sc.next().toUpperCase().charAt(0);
            sc.nextLine();
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
                    "We couldn't find a match for \"" + searchConcertArtist + "\", Do you want to try another search?");
            System.out.println(
                    "(Double check your search for any typo or spelling errors - or try different search term)");
            System.out.println("");
        }

        // Press anything to continue
        blankInput();
    }

    public static void searchConcertVenue(Catalog catalog, Venue[] venueList) {
        List<Concert> searchResult = new ArrayList<>();

        // Display Available Venue
        System.out.println(" Venue Available ");
        System.out.println("=================");
        for (int i = 0; i < venueList.length; i++) {
            System.out.println((i + 1) + ". " + venueList[i].getName());
        }
        System.out.println();

        // Get user input for search venue
        boolean isValidVenueNo = false;
        String searchConcertVenue = null;

        // Input Validation
        while (!isValidVenueNo) {
            System.out.print("Select Concert Language: ");
            int searchConcertVenueChoice = Character.getNumericValue(sc.next().charAt(0));
            sc.nextLine();

            if (searchConcertVenueChoice > 0 && searchConcertVenueChoice <= venueList.length) {
                for (int i = 0; i < venueList.length; i++) {
                    if (searchConcertVenueChoice == (i + 1)) {
                        searchConcertVenue = venueList[i].getName();
                    }
                }

                isValidVenueNo = true;
            } else if (searchConcertVenueChoice > venueList.length) {
                System.out.println("");
                System.err.println("It is likely your choice exceed the maximum number of choice. Please try again.");
                System.out.println("");
            } else {
                System.out.println("");
                System.err.println("Invalid number! Please enter number between 1 to " + venueList.length + ".");
                System.out.println("");
            }
        }

        searchResult = catalog.searchByVenue(searchConcertVenue);

        if (searchResult != null) {
            System.out.println("Search Result");
            displayConcertTitle(searchResult);

            // Ask user to display detail or not
            System.out.print("Do you want to display detail of the concert?(Y/N): ");
            char searchDisplayAllChoice = sc.next().toUpperCase().charAt(0);
            sc.nextLine();
            System.out.println("");

            switch (searchDisplayAllChoice) {
                case 'Y':
                    boolean isValidNo = false;
                    while (!isValidNo) {
                        if (searchResult.size() == 1) {
                            System.out.println("");
                            System.out.println("*Concert Detail*");
                            searchResult.get(0).displayAllDetail();

                            isValidNo = true;
                            break;
                        }

                        // Display the Concerts
                        displayConcert(searchResult);
                    }
                    break;
                case 'N':
                    break;
                default:
                    System.out.println("Invalid character!");
            }
        }

        // Press anything to continue
        blankInput();
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
    public static List<VenueSeatCat>[] initializeVenueSeatCat(Venue[] venueList) {
        int fileLineNumber = (int) countFileLineNumber("venueSeatCat.txt");

        // Variables
        List<VenueSeatCat>[] venueSeatCategory = new List[venueList.length];
        String[] seatCatDetails = new String[fileLineNumber];
        String nameVenue = null;
        String catDesc = null;
        int catCapacity = 0;

        for (int i = 0; i < venueSeatCategory.length; i++) {
            venueSeatCategory[i] = new ArrayList<>();
        }

        // Try-Catch get data from artist.txt
        try {
            File concertCategoryFile = new File("venueSeatCat.txt");
            Scanner fileScanner = new Scanner(concertCategoryFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                seatCatDetails = currentLine.split(";");

                nameVenue = seatCatDetails[0];
                catDesc = seatCatDetails[1];
                catCapacity = Integer.valueOf(seatCatDetails[2]);

                for (int i = 0; i < venueList.length; i++) {
                    if (nameVenue.equals(venueList[i].getName())) {
                        venueSeatCategory[i].add(new VenueSeatCat(venueList[i], catDesc, catCapacity));
                    }
                }

                currentLine = fileScanner.nextLine();
            }

            seatCatDetails = currentLine.split(";");

            nameVenue = seatCatDetails[0];
            catDesc = seatCatDetails[1];
            catCapacity = Integer.valueOf(seatCatDetails[2]);

            for (int i = 0; i < venueList.length; i++) {
                if (nameVenue.equals(venueList[i].getName())) {
                    venueSeatCategory[i].add(new VenueSeatCat(venueList[i], catDesc, catCapacity));
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }

        return venueSeatCategory;
    }

    public static List<ShowSeatCat>[] initializeSeatCategory(Venue[] venueList) { // return ShowSeatCat
        int fileLineNumber = (int) countFileLineNumber("showSeatCat.txt");

        List<ShowSeatCat>[] showSeatCategory = new List[venueList.length];
        String[] categorySeatList = new String[fileLineNumber];
        String nameVenue = null;
        String catDesc = null;
        int catCapacity = 0;
        double catPrice = 0;

        for (int i = 0; i < showSeatCategory.length; i++) {
            showSeatCategory[i] = new ArrayList<>();
        }

        // Try-Catch get data from showSeatCat.txt
        try {
            File concertCategoryFile = new File("showSeatCat.txt");
            Scanner fileScanner = new Scanner(concertCategoryFile);
            String currentLine = fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                categorySeatList = currentLine.split(";");

                nameVenue = categorySeatList[0];
                catDesc = categorySeatList[1];
                catCapacity = Integer.valueOf(categorySeatList[2]);
                catPrice = Double.valueOf(categorySeatList[3]);

                for (int i = 0; i < venueList.length; i++) {
                    if (nameVenue.equals(venueList[i].getName())) {
                        showSeatCategory[i].add(new ShowSeatCat(venueList[i], catDesc, catCapacity, catPrice));
                    }
                }

                currentLine = fileScanner.nextLine();
            }

            categorySeatList = currentLine.split(";");

            nameVenue = categorySeatList[0];
            catDesc = categorySeatList[1];
            catCapacity = Integer.valueOf(categorySeatList[2]);
            catPrice = Double.valueOf(categorySeatList[3]);

            for (int i = 0; i < venueList.length; i++) {
                if (nameVenue.equals(venueList[i].getName())) {
                    showSeatCategory[i].add(new ShowSeatCat(venueList[i], catDesc, catCapacity, catPrice));
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }

        return showSeatCategory;
    }

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

        // Spacing
        System.out.println("\n");
    }

    public static Concert getSelectedConcert(Concert[] concertList, Venue[] venueList) {
        Concert selectedConcert = null;

        // Flag
        boolean isConfirmed = false;

        // Display Concert details in list table
        displayConcertList(concertList); // Display Menu

        while (!isConfirmed) {
            System.out
                    .print("Please Enter Your Preference Concert Show (1 - " + concertList.length + "): ");
            int choice = Character.getNumericValue(sc.next().charAt(0));

            if (choice > concertList.length || choice < 0) {
                System.out.println();
                System.out.println("************************");
                System.out.println("PLEASE ENTER 1 - " + concertList.length + " ONLY! ");
                System.out.println("************************");
                System.out.println();
            }

            else if (choice > 0 && choice <= concertList.length) {
                System.out.print("Are you sure? (Y for yes, N for no): ");
                char confirmation = sc.next().toUpperCase().charAt(0);
                sc.nextLine();
                System.out.println();

                if (confirmation == 'Y') {
                    concertList[choice - 1].getVenue().getName();

                    isConfirmed = true;

                    selectedConcert = concertList[choice - 1];
                } else if (confirmation == 'N') {
                } else {
                    System.out.println();
                    System.out.println("Invalid character. Please enter Y or N");
                    System.out.println();
                }
            }
        }

        return selectedConcert;
    }

    public static Order orderConcert(Customer currentCustomer, Concert selectedConcert, Venue[] venueList,
            List<ShowSeatCat>[] showSeatCatList,
            List<VenueSeatCat>[] venueSeatCatList) {
        int seatCatChoice = 0;
        int ticketQtyChoice = 0;

        // Get index of selected venue by comparing with the concertList
        int selectedVenueIndex = 0;

        for (int i = 0; i < venueList.length; i++) {
            if (selectedConcert.getVenue().equals(venueList[i])) {
                selectedVenueIndex = i;
                break;
            }
        }

        // Display seatCat
        displaySeatCat(selectedVenueIndex, selectedConcert, venueList, showSeatCatList);

        // Select seatCat
        boolean validSeatCatChoice = false;
        while (!validSeatCatChoice) {
            System.out.println();
            System.out.print("Please Enter Your Preference Seat Category: ");
            seatCatChoice = Character.getNumericValue(sc.next().charAt(0));
            sc.nextLine();

            if (seatCatChoice > 0 && seatCatChoice <= showSeatCatList[selectedVenueIndex].size()) {
                validSeatCatChoice = true;
            } else {
                System.err.println("Invalid number. Please try again");
                System.out.println();
            }
        }

        // Select seatQty
        boolean exceedTicketQty = true;

        while (exceedTicketQty) {
            System.out.print("Please Enter Your Ticket Quantity(Max 6 tickets): ");
            ticketQtyChoice = Character.getNumericValue(sc.next().charAt(0));
            sc.nextLine();

            if (ticketQtyChoice > 0 && ticketQtyChoice <= 6) {
                exceedTicketQty = false;
            } else if (ticketQtyChoice > 6) {
                System.out.println();
                System.err.println("!!!Number exceeded the maximum ticket!!!");
                System.out.println();
            } else {
                System.err.println("Invalid number! Please try again.");
            }
        }

        // Calculate price
        double subtotal = showSeatCatList[selectedVenueIndex].get(seatCatChoice - 1).getSeatPrice() * ticketQtyChoice;

        // Create Order object
        Order newOrder = new Order(ticketQtyChoice, LocalDate.now(), new Ticket(selectedConcert,
                new ShowSeat(showSeatCatList[selectedVenueIndex].get(seatCatChoice - 1),
                        venueSeatCatList[selectedVenueIndex].get(seatCatChoice - 1)),
                LocalDate.now()), subtotal);

        // Write Into order.txt

        // Payment
        Payment payment = null;
        int paymentMethodChoice = 0;

        displayPaymentMethodMenu();

        boolean validInt = false;

        while (!validInt) {
            System.out.print("Please select one payment method to pay the order: ");
            paymentMethodChoice = Character.getNumericValue(sc.next().charAt(0));
            if (paymentMethodChoice > 0 && paymentMethodChoice <= 2) {
                validInt = true;
            } else if (paymentMethodChoice > 2) {
                System.out.println();
                System.err.println("!!!Number exceeded the maximum ticket!!!");
                System.out.println();
            } else {
                System.err.println("Invalid number! Please try again.");
            }
        }

        switch (paymentMethodChoice) {
            case 1:
                // Vriables
                String cardType = null;
                int cardNum = 0;
                String validDate = null;
                int cvc = 0;

                System.out.println("Please enter your bank card detail: ");

                // Get Card Type
                System.out.print("Card Type (Visa/Mastercard): ");
                boolean validCardType = false;

                while (!validCardType) {
                    cardType = sc.nextLine();

                    if (cardType != "Visa" || cardType != "Mastercard") {
                        validCardType = true;
                    } else {
                        System.err.println("Invalid card type! Please try again.");
                    }
                }

                // Get Card Num
                System.out.print("Card Number (8 digits with no spacing): ");
                boolean validCardNum = false;

                while (!validCardNum) {
                    cardNum = Character.getNumericValue(sc.next().charAt(0));

                    if (cardNum >= 0 || cardNum <= 99999999) {
                        validCardNum = true;
                    } else {
                        System.err.println("Invalid card number! Please enter 8 digit card number.");
                    }
                }

                // Get Card Valid Date
                System.out.print("Card Valid Date (MM/YY): ");
                validDate = sc.nextLine();

                // Get Card CVC
                System.out.print("Card CVC (3 digits): ");
                boolean validCardCVC = false;

                while (!validCardCVC) {
                    cvc = Character.getNumericValue(sc.next().charAt(0));

                    if (cvc < 0 || cvc > 999) {
                        validCardCVC = true;
                    } else {
                        System.err.println("Invalid card number! Please enter a 3 digit card number.");
                    }
                }

                payment = new BankCard(currentCustomer.getLastName(), cardType, cardNum, validDate, cvc, 2500,
                        LocalDate.now());

                if (currentCustomer.payOrder(newOrder)) {
                    System.out.println("Payment Sucessful! Thanks for your purchase.");
                }

            case 2: // Cash

        }

        // Print Ticket(s)
        for (int i = 0; i < ticketQtyChoice; i++) {
            printTicket(newOrder);
        }

        return newOrder;
    }

    public static void displaySeatCat(int selectedVenueIndex, Concert selectedConcert, Venue[] venueList,
            List<ShowSeatCat>[] showSeatCatList) {
        // Header
        System.out.println("---------------------------------------------------");
        System.out.printf("| %2s | %15s | %10s | %11s |\n", centerString(2, "NO"), centerString(15, "CATEGORY"),
                centerString(10, "PRICE (RM)"), centerString(11, "SEAT REMAIN"));
        System.out.println("|----|-----------------|------------|-------------|");

        // Seat Category Content
        for (int i = 0; i < showSeatCatList[selectedVenueIndex].size(); i++) {
            System.out.printf("| %2d | %15s | %10s | %11s |\n", (i + 1),
                    centerString(15, showSeatCatList[selectedVenueIndex].get(i).getDescription()),
                    centerString(10,
                            String.valueOf(String.format("%.2f",
                                    showSeatCatList[selectedVenueIndex].get(i).getSeatPrice()))),
                    centerString(11, String.valueOf(showSeatCatList[selectedVenueIndex].get(i).getRemainingSeat())));
            System.out.println("|----|-----------------|------------|-------------|");
        }
    }

    public static void displayPaymentMethodMenu() {
        System.out.println("==================");
        System.out.println("  Payment Method  ");
        System.out.println("==================");

        System.out.println("1. Bank Card");
        System.out.println("2. Cash");

        System.out.println("");
    }

    public static void printTicket(Order newOrder) {
        System.out.println("Printing Ticket...");
        System.out.println();
        System.out.println("\t\t\t==============================================================");
        System.out.println("\t\t\t||        ======  ==   =====  ==  ==  =====  ======         ||");
        System.out.println("\t\t\t||          ==    ==   ==     == =    =        ==           ||");
        System.out.println("\t\t\t||          ==    ==   ==     == =    =====    ==           ||");
        System.out.println("\t\t\t||          ==    ==   ==     ==  =   =        ==           ||");
        System.out.println("\t\t\t||          ==    ==   =====  ==   == =====    ==           ||");
        System.out.println("\t\t\t||----------------------------------------------------------||");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   CONCERT NAME: ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getConcert().getName(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   DATE & TIME : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getConcert().getDatetime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm a")), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   VENUE       : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getConcert().getVenue().getName(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   CATEGORY    : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getSeat().getCategory().getDescription(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   SECTION     : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getSeat().getSection(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   ROW         : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getSeat().getRow(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.print("\t\t\t||   SEAT.NO     : ");
        System.out.printf("%-40s%3s", newOrder.getTicket().getSeat().getSeatNumber(), " ||\n");
        System.out.println("\t\t\t||                                                          ||");
        System.out.println("\t\t\t==============================================================");
    }

    // 4. Login Methods (Wei Hao)
    public static Person loginRegister(List<Person>[] userList) {
        Person currentUser = null;

        System.out.print("Are you a new user?(Y/N): ");
        char isNewUser = Character.valueOf(sc.next().charAt(0));
        sc.nextLine();

        if (isNewUser == 'n' || isNewUser == 'N') { // Login
            System.out.println("---------"); // Header
            System.out.println("| Login |");
            System.out.println("---------\n");

            System.out.print("Enter your username: "); // User input username
            String username = sc.nextLine();
            System.out.print("Enter your password: "); // User input password
            String password = sc.nextLine();
            System.out.println(); // Blank row

            for (int i = 0; i < userList.length; i++) {
                for (int j = 0; j < userList[i].size(); j++) {
                    if (username.equals(userList[i].get(j).getAccount().getUsername())) { // Check username
                        if (password.equals(userList[i].get(j).getAccount().getPassword())) { // Check password
                            if (i == 0) { // admin
                                currentUser = (Admin) userList[i].get(j);
                                System.out.println("Login succesfully as an Admin");
                                break;
                            } else if (i == 1) { // customer
                                currentUser = (Customer) userList[i].get(j);
                                System.out.println("Login succesfully as a Customer");
                                break;
                            }
                        }
                    }
                }

                if (currentUser != null) {
                    System.out.println("Welcome back! " + currentUser.getFirstName());
                    System.out.println("\n"); // Space 2 rows
                    break;
                }
            }

            if (currentUser == null) {
                System.out.println("Incorrect credentials, please try again!\n");
            }

        }

        else if (isNewUser == 'y' || isNewUser == 'Y') { // Register
            // Register Header
            System.out.println("-----------");
            System.out.println("| Register |");
            System.out.println("-----------\n");

            // Ask user details
            System.out.print("Enter your first name: "); // Check char length >= ? AND <= ?
            String firstName = sc.nextLine();
            System.out.print("Enter your last name: "); // Check char length >= ? AND <= ?
            String lastName = sc.nextLine();
            System.out.print("Enter your prefered username: "); // Check length >= 4 AND <= 20
            String newUsername = sc.nextLine();
            System.out.print("Enter your prefered password: "); // Check if pwd is >= 8 AND < 18
            String newPassword = sc.nextLine();
            System.out.print("Enter your phone number: "); // Check if number of digit = 10/11
            String phone = sc.nextLine();
            System.out.print("Enter your email: "); // Check email format
            String email = sc.nextLine();

            List<Order> orderList = new ArrayList<>();

            Customer newUser = new Customer(orderList,
                    new BankCard(lastName, "Visa", 40102322, "01/24", 899, 5000, LocalDate.now()),
                    new Account(newUsername, newPassword, AccountStatus.ACTIVE),
                    firstName, lastName, "", phone, email);

            // Compare newuser exist in userList(Customer) or not
            // If exist, then reject registration --> retry register

            // Save data to user.txt
            String newUserData = "customer" + ";" +
                    newUser.getAccount().getUsername() + ";" +
                    newUser.getAccount().getPassword() + ";" +
                    "active" + ";" +
                    newUser.getFirstName() + ";" +
                    newUser.getLastName() + ";" +
                    newUser.getAddress() + ";" +
                    newUser.getEmail() + ";" +
                    newUser.getPhone() + ";" +
                    newUser.getJoinedDate().format(DateTimeFormatter.ISO_DATE);

            if (saveData("user.txt", newUserData)) {
                currentUser = newUser;

                System.out.println();
                System.out.println("Registered Successfully!");
                System.out.println("Welcome: " + newUser.getAccount().getUsername());
                System.out.println();
            } else {
                System.err.println("Fail to register");
            }

        }

        else
            System.out.println("Invalid character, only Y/N is acceptable\n");

        return currentUser;

    }

    // Sign Out Methods
    public static void signOut() {
        System.out.println("Sign Out successfully!\n");
        clearScreen(); // Clear Screen (Start a new screen)
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
        System.out.println("");
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

    // File-Related Methods
    public static void loadFile(String fileName) {
        try {
            File fileObj = new File(fileName);
            if (fileObj.createNewFile()) {
            } else {
                // System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static boolean saveData(String fileName, String data) {
        boolean succesful = false;

        loadFile(fileName);

        // Write data to file
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true));
            myWriter.newLine();
            myWriter.write(data);
            myWriter.close();

            succesful = true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return succesful;
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

}