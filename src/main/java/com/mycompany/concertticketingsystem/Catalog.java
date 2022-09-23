package com.mycompany.concertticketingsystem;


import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author Joshua Koh
 */
public class Catalog implements Search {
    // Map<key, value> variableName;
    private LocalDate lastUpdated;
    private Map<String, Concert> concertTitles;
    private Map<String, Concert[]> concertArtists;
    private Map<String, Concert[]> concertLanguages;
    private Map<String, Concert[]> concertDates;
    private Map<String, Concert[]> concertVenues;

    // Constructor
    public Catalog(LocalDate lastUpdated, Map<String, Concert> concertTitles, Map<String, Concert[]> concertArtists, Map<String, Concert[]> concertLanguages, Map<String, Concert[]> concertDates, Map<String, Concert[]> concertVenues) {
        this.lastUpdated = lastUpdated;
        this.concertTitles = concertTitles;
        this.concertArtists = concertArtists;
        this.concertLanguages = concertLanguages;
        this.concertDates = concertDates;
        this.concertVenues = concertVenues;
    }


    // Getters
    public Map<String, Concert[]> getConcertLanguages() {
        return concertLanguages;
    }

    // Setters
    // Methods
    @Override
    public Concert[] searchByTitle(String title) {
        Concert[] matchedConcertList = new Concert[concertTitles.entrySet().toArray().length];
        int count = 0;
        
        for (Map.Entry pairEntry : concertTitles.entrySet()) {
            if(pairEntry.getKey().toString().toUpperCase().contains(title.toUpperCase())) {
                matchedConcertList[count] = (Concert)pairEntry.getValue();
                
                count++;
            }
        }
        
        return matchedConcertList;
    }
    
    public String[] getlanguageTitleList() {        
        String[] languageTitleList = new String[concertLanguages.entrySet().toArray().length];
        int count = 0;
        
        for (Map.Entry pairEntry : concertLanguages.entrySet()) {
            languageTitleList[count] = (String)pairEntry.getKey();

            count++;
        }
        
        return languageTitleList;
    }

    @Override
    public Concert[] searchByLanguage(String language) {
        
        
       return concertLanguages.get(language);
    }
    
    @Override
    public Concert[] searchByDate(String date) {
       return concertDates.get(date);
    }

    @Override
    public Concert[] searchByArtist(String artist) {
        return concertArtists.get(artist);
    }

    @Override
    public Concert[] searchByVenue(String venue) {
        return concertVenues.get(venue);
    }
    
    @Override
    public String toString() {
        return this.concertDates + "\n" +
               this.concertLanguages + "\n" + 
               this.concertTitles + "\n" + 
               this.concertVenues;
    }
}
