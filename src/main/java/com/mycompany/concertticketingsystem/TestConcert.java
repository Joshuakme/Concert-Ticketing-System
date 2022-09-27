package com.mycompany.concertticketingsystem;

import static com.mycompany.concertticketingsystem.ConcertTicketingSystem.countFileLineNumber;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh
 */
public class TestConcert {
    public static void main(String[] args) {
        // // Object Initialization
        // Artist[] artistList = initializeArtists();
        Venue[] venueList = initializeVenues();
        // Concert[] concertList = initializeConcerts(artistList, venueList);
        // Person[][] userList = initializePerson();
        List<ShowSeatCat>[] showSeatCatList = initializeSeatCategory(venueList);

        int count = 0;
        for (int i = 0; i < showSeatCatList.length; i++) {
            for (int j = 0; j < showSeatCatList[i].size(); j++) {
                System.out
                        .println(showSeatCatList[i].get(j).getDescription() + " : "
                                + showSeatCatList[i].get(j).getSeatPrice());
                count++;
                System.out.println(count);
            }

        }

        //
        //
        // // Create catalog
        // Catalog catalog = createCatalog(artistList, venueList, concertList);

        // System.out.print(userList[1][1].getFirstName());
        // for(int i = 0; i < userList.length; i++){
        // for(int j=0; j < userList[i].length;j++){
        // System.out.print(userList[i][j]);}
        //
        // }

    }

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

    public static List<ShowSeatCat>[] initializeSeatCategory(Venue[] venueList) { // return ShowSeatCat
        int fileLineNumber = (int) countFileLineNumber("category_seat.txt");

        List<ShowSeatCat>[] venueSeatCategory = new List[venueList.length];
        String[] categorySeatList = new String[fileLineNumber];
        String nameVenue = null;
        String catDesc = null;
        int catCapacity = 0;
        double catPrice = 0;

        for (int i = 0; i < venueSeatCategory.length; i++) {
            venueSeatCategory[i] = new ArrayList<>();
        }

        // Try-Catch get data from artist.txt
        try {
            File concertCategoryFile = new File("category_seat.txt");
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
                        venueSeatCategory[i].add(new ShowSeatCat(catDesc, catCapacity, catPrice));
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
                    venueSeatCategory[i].add(new ShowSeatCat(catDesc, catCapacity, catPrice));
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }

        return venueSeatCategory;
    }
}
