package com.mycompany.concertticketingsystem;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Joshua Koh
 */
public class Catalog implements Search {
    private Date lastUpdated;
    private Map<String, List<Concert>> concertTitles;
    private Map<String, List<Concert>> concertArtists;
    private Map<String, List<Concert>> concertLanguages;
    private Map<String, List<Concert>> concertGenres;
    private Map<String, List<Concert>> concertDates;
    private Map<String, List<Concert>> concertVenues;

    // Constructor
    public Catalog(Date lastUpdated, Map<String, List<Concert>> concertTitles, Map<String, List<Concert>> concertLanguages, Map<String, List<Concert>> concertGenres, Map<String, List<Concert>> concertDates, Map<String, List<Concert>> concertVenues) {
        this.lastUpdated = lastUpdated;
        this.concertTitles = concertTitles;
        this.concertLanguages = concertLanguages;
        this.concertGenres = concertGenres;
        this.concertDates = concertDates;
        this.concertVenues = concertVenues;
    }
    
    // Getters
    
    // Setters
    
    // Methods

    @Override
    public List<Concert> searchByTitle(String title) {
        return this.concertTitles.get(title);
    }

    @Override
    public List<Concert> searchByLanguage(String language) {
        return this.concertLanguages.get(language);
    }

    @Override
    public List<Concert> searchByArtist(String artist) {
        return this.concertArtists.get(artist);
    }

    @Override
    public List<Concert> searchByGenre(String genre) {
        return this.concertGenres.get(genre);
    }

    @Override
    public List<Concert> searchByVenue(String venue) {
        return this.concertVenues.get(venue);
    }

    @Override
    public String searchQuery(String inputQuery) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String toString() {
        return this.concertDates + "\n" +
               this.concertGenres + "\n" +
               this.concertLanguages + "\n" + 
               this.concertTitles + "\n" + 
               this.concertVenues;
    }
}
