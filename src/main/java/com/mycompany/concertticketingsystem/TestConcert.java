package com.mycompany.concertticketingsystem;


import java.io.File;
import java.io.FileNotFoundException;
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
        
//        for(int i=0; i<concertList.length; i++) {
//            if(concertList[i] != null)
//                System.out.println(concertList[i].getArtist()); 
//        }

//        for(int i=0; i<artistList.length; i++) {
//            if(artistList[i] != null)
//                System.out.println(artistList[i].getName());
//        }


        Map<String, Concert[]> concertArtists = new HashMap();
        Map<String, Concert[]> concertLanguages = new HashMap();
        
        int count = 0;
        for(int i=0; i<concertList.length; i++) {
            if(concertList[i] != null) {
                count++;
            }
        }
        
//        Concert[] concertByArtist = new Concert[count];
//        
//        System.out.println(artistList[3].getName().toUpperCase());
//        System.out.println(concertList[5].getArtist().getName().toUpperCase());
//                
//        if(artistList[3].getName().toUpperCase().equals(concertList[5].getArtist().getName().toUpperCase()))
//            concertByArtist[0] = concertList[5];
//
//        concertArtists.put(artistList[3].getName(), concertByArtist);
//        
//        System.out.print(concertArtists.get("JJ Lin")[0].getName());
        

        
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
        
//        for(Map.Entry pairEntry: concertLanguages.entrySet()) {
//            System.out.println(pairEntry.getKey() + " : " + pairEntry.getValue());
//        }
        for(int i=0; i<concertLanguages.get("Mandarin").length; i++) {
            if(concertLanguages.get("Mandarin")[i] != null)
                System.out.println(concertLanguages.get("Mandarin")[i].getName());
        }

        System.out.println(AccountStatus.values()[1]);

        
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
}
