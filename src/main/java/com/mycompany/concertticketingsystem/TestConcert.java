package com.mycompany.concertticketingsystem;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh
 */
public class TestConcert {
    public static void main(String[] args) {
        // Object Initialization
        Artist[] artistList = initializeArtists();
        Venue[] venueList = initializeVenues();
        Concert[] concertList = initializeConcerts(artistList, venueList);
        Person[][] userList = initializePerson();   // userList[0][] is Admin list, serList[1][] is Customer list
        
        String[] venueNameList = new String[venueList.length];
        
      for(int i=0; i<venueList.length; i++) {
          venueNameList[i] = venueList[i].getName();
      }
      
      for(int i=0; i<concertList.length; i++) {
          System.out.println(concertList[i].getVenue().getName());
      }
      
        // Create catalog
//        Catalog catalog = createCatalog(artistList, venueList, concertList);


        
    }
    
        // Data Initialization Methods
    public static Catalog createCatalog(Artist[] artistList, Venue[] venueList, Concert[] concertList) {
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
        String[] venueNameList = new String[venueList.length];
        
        for(int i=0; i<venueList.length; i++) {
            venueNameList[i] = venueList[i].getName();
        }
        
        Concert[] concertByVenue = new Concert[concertList.length];
        int venueCount = 0;
        for(int j=0; j<concertList.length; j++) {
            if(concertList[j] != null) {
                for(int i=0; i<venueNameList.length; i++) {
                    if(concertList[j].getVenue().getName().toUpperCase().equals(venueNameList[i].toUpperCase())) {
                        concertByVenue[venueCount] = concertList[i];

                        venueCount++;
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
        int fileLineNumber = (int)countFileLineNumber("artist.txt");
        
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
        for(int i=0; i<=counter; i++) {
            artistList[i] = new Artist(artistNameList[i], artistLanguageList[i], artistGenreList[i]);
        }
        
        return artistList;
    }
            
    public static Venue[] initializeVenues() {
        int fileLineNumber = (int)countFileLineNumber("venue.txt");
                
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
        for(int i=0; i<=counter; i++) {
            venueList[i] = new Venue(venueNameList[i], venueLocationList[i], venueTypeList[i], venueCapacityList[i]);
        }

        return venueList;
    }
    
    public static Concert[] initializeConcerts(Artist[] artistList, Venue[] venueList) {
        int fileLineNumber = (int)countFileLineNumber("concert.txt");
        
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
            
            concertDetails = currentLine.split(";");
            
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
        Concert[] concertList = new Concert[fileLineNumber];
        for(int i=0; i<=counter; i++) {
            concertList[i] = new Concert(concertNameList[i], concertArtistList[i], concertDatetimeList[i], concertLanguageList[i], concertVenueList[i], concertIsTrendingList[i]);
        }
        
        return concertList;
    }
    
    public static Person[][] initializePerson() {
        int fileLineNumber = (int)countFileLineNumber("user.txt");
        Person[][] usersList = new Person[2][];   // Person[0][] is Admin users, Person[1][] is Customer users
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
            userJoinedDate[counter] =  LocalDate.parse(userDetails[9]);

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


