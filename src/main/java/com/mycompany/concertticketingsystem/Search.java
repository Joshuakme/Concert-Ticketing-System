package com.mycompany.concertticketingsystem;

import java.util.List;

/**
 *
 * @author Joshua Koh
 */
public interface Search {
   public List<Concert> searchByTitle(String title);

   public List<Concert> searchByLanguage(String language);

   public List<Concert> searchByDate(String date);

   public List<Concert> searchByArtist(String artist);

   public List<Concert> searchByVenue(String venue);
}
