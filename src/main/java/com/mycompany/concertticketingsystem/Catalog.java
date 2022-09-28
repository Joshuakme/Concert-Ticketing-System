package com.mycompany.concertticketingsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Painter;

/**
 *
 * @author Joshua Koh
 */
public class Catalog implements Search {
    // Map<key, value> variableName;
    private LocalDate lastUpdated;
    private Map<String, List<Concert>> concertTitles;
    private Map<String, List<Concert>> concertArtists;
    private Map<String, List<Concert>> concertLanguages;
    private Map<String, List<Concert>> concertDates;
    private Map<String, List<Concert>> concertVenues;

    // Constructor
    public Catalog(LocalDate lastUpdated, Map<String, List<Concert>> concertTitles,
            Map<String, List<Concert>> concertArtists, Map<String, List<Concert>> concertLanguages,
            Map<String, List<Concert>> concertDates, Map<String, List<Concert>> concertVenues) {
        this.lastUpdated = lastUpdated;
        this.concertTitles = concertTitles;
        this.concertArtists = concertArtists;
        this.concertLanguages = concertLanguages;
        this.concertDates = concertDates;
        this.concertVenues = concertVenues;
    }

    // Getters
    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, List<Concert>> getConcertTitles() {
        return concertTitles;
    }

    public Map<String, List<Concert>> getConcertArtists() {
        return concertArtists;
    }

    public Map<String, List<Concert>> getConcertLanguages() {
        return concertLanguages;
    }

    public Map<String, List<Concert>> getConcertDates() {
        return concertDates;
    }

    public Map<String, List<Concert>> getConcertVenues() {
        return concertVenues;
    }

    // Setters
    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setConcertTitles(Map<String, List<Concert>> concertTitles) {
        this.concertTitles = concertTitles;
    }

    public void setConcertArtists(Map<String, List<Concert>> concertArtists) {
        this.concertArtists = concertArtists;
    }

    public void setConcertLanguages(Map<String, List<Concert>> concertLanguages) {
        this.concertLanguages = concertLanguages;
    }

    public void setConcertDates(Map<String, List<Concert>> concertDates) {
        this.concertDates = concertDates;
    }

    public void setConcertVenues(Map<String, List<Concert>> concertVenues) {
        this.concertVenues = concertVenues;
    }

    // Methods
    @Override
    public List<Concert> searchByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }

        List<Concert> matchedConcertList = new ArrayList<>();

        for (Map.Entry<String, List<Concert>> pairEntry : concertTitles.entrySet()) {
            if (pairEntry.getKey().toString().toUpperCase().contains(title.toUpperCase())) {
                matchedConcertList = pairEntry.getValue();
            }
        }

        return matchedConcertList;
    }

    public String[] getlanguageTitleList() {
        String[] languageTitleList = new String[concertLanguages.entrySet().toArray().length];
        int count = 0;

        for (Map.Entry<String, List<Concert>> pairEntry : concertLanguages.entrySet()) {
            languageTitleList[count] = (String) pairEntry.getKey();

            count++;
        }

        return languageTitleList;
    }

    @Override
    public List<Concert> searchByLanguage(String language) {
        return concertLanguages.get(language);
    }

    @Override
    public List<Concert> searchByDate(String date) {
        return concertDates.get(date);
    }

    @Override
    public List<Concert> searchByArtist(String artist) {
        List<Concert> matchedConcertList = new ArrayList<>();

        for (Map.Entry<String, List<Concert>> pairEntry : concertArtists.entrySet()) {
            if (pairEntry.getKey().toString().toUpperCase().contains(artist.toUpperCase())) {
                // System.out.println(pairEntry.getValue().);
                matchedConcertList = pairEntry.getValue();
            }
        }

        return matchedConcertList;
    }

    @Override
    public List<Concert> searchByVenue(String venue) {
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
