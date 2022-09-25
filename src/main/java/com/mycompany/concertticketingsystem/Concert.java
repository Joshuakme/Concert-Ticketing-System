/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concertticketingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Joshua Koh
 */
public class Concert {
    private static int count = 1;
    private String id;
    private String name;
    private Artist artist;
    private LocalDateTime datetime;
    private String language;
    private Venue venue;
    private boolean trending;

    // Constructor
    public Concert(String name, Artist artist, LocalDateTime datetime, String language, Venue venue,
            boolean isTrending) {
        this.id = formatId(count);
        this.name = name;
        this.artist = artist;
        this.datetime = datetime;
        this.language = language;
        this.venue = venue;
        this.trending = isTrending;

        count++;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getLanguage() {
        return language;
    }

    public Venue getVenue() {
        return venue;
    }

    public boolean isTrending() {
        return trending;
    }

    // Setters

    // Methods
    private String formatId(int count) {
        if (count < 10)
            return "C000" + count;
        else if (count >= 10 && count < 100)
            return "C00" + count;
        else if (count >= 100 && count < 1000)
            return "C0" + count;
        else if (count >= 1000 && count < 10000)
            return "C" + count;
        return "";
    }

    public void getShow() {
        // Codes Here
    }

    public void displayAllDetail() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm a");
        // DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println("Name: " + this.name);
        System.out.println("Artist: " + this.artist.getName());
        System.out.println("Language: " + this.language);
        System.out.println("Venue: " + this.venue.getName());
        System.out.println("Date: " + this.datetime.format(dateFormat));
        System.out.println();
    }

}
