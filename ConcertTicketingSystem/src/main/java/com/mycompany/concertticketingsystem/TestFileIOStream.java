package com.mycompany.concertticketingsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Joshua Koh
 */
public class TestFileIOStream {
    public static void main(String[] args) {
        String results = "";
        String[] wordsArray;
        int counter = 0;
        String[] concertList = new String[100];
        String[] artistList = new String[100];
        String[] dateList = new String[100];
        String[] venueList = new String[100];
                       
        try {
            File concertFile = new File("concert.txt");
            Scanner fileScanner = new Scanner(concertFile);
            String currentLine = fileScanner.nextLine();
            
                    
            while (fileScanner.hasNextLine()) {
                results += currentLine + "\n";
                wordsArray = currentLine.split("\t");
            
                concertList[counter] = wordsArray[0];
                artistList[counter] = wordsArray[1];
                dateList[counter] = wordsArray[2];
                venueList[counter] = wordsArray[3];
                
                currentLine = fileScanner.nextLine();        
                counter++;                
            }
            
            wordsArray = currentLine.split("\t");
            results += currentLine + "\n";
            
            concertList[counter] = wordsArray[0];
            artistList[counter] = wordsArray[1];
            dateList[counter] = wordsArray[2];
            venueList[counter] = wordsArray[3];

            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!\n");
        }
               
        System.out.printf("%-3s%-50s%-15s%-13s%-30s\n", "#", "Concert", "Artist", "Date", "Venue");
        System.out.printf("%-3s%-50s%-15s%-13s%-30s\n", "--", "-------------------------------------------------", "--------------", "------------", "-----------------------------");
        for(int i=0; i<=counter; i++) {
            System.out.printf("%-3s%-50s%-15s%-13s%-20s\n", (i + 1) + ".", concertList[i], artistList[i], dateList[i], venueList[i]);
        }
//        System.out.print(results);

//        Concert con = new Concert();
        
        
    }
}
