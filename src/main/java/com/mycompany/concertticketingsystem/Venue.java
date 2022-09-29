package com.mycompany.concertticketingsystem;

import java.util.Objects;

/**
 *
 * @author Joshua Koh
 */
public class Venue {
    private static int count = 1;
    private String id;
    private String name;
    private String address;
    private String type;
    private int capacity;

    // Constructor
    public Venue() {
        this("", "", "", 0);
    }

    public Venue(String name, String location, String type, int capacity) {
        this.id = formatId(count);
        this.name = name;
        this.address = location;
        this.type = type;
        this.capacity = capacity;

        count++;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters

    // Methods
    private String formatId(int count) {
        if (count < 10)
            return "V00" + count;
        else if (count >= 10 && count < 100)
            return "V0" + count;
        else if (count >= 100 && count < 1000)
            return "V" + count;
        return "";
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /*
         * Check if o is an instance of Complex or not
         * "null instanceof [type]" also returns false
         */
        if (!(o instanceof Venue)) {
            return false;
        }

        // typecast o to Venue so that we can compare data members
        Venue v = (Venue) o;

        // Compare the data members and return accordingly
        return this.id.equals(v.id) &&
                this.name.equals(v.name) &&
                this.address.equals(v.address) &&
                this.type.equals(v.type) &&
                this.capacity == v.capacity;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.address);
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + this.capacity;
        return hash;
    }

}
