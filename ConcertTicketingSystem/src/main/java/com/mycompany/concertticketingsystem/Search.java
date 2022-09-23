package com.mycompany.concertticketingsystem;

import java.util.List;

/**
 *
 * @author Joshua Koh
 */
public interface Search {
   public List<Concert> searchByTitle(String title);
   public List<Concert> searchByLanguage(String language);
   public List<Concert> searchByArtist(String artist);
   public List<Concert>  searchByGenre(String genre);
   public List<Concert> searchByVenue(String venue);    
   public String searchQuery(String inputQuery);
}
