package de.htwsaar.nytSearchEngine;

import Testclasses.TestForDAOImpl;
import Testclasses.TestForImporter;
import Testclasses.TestForParser;
import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Accumulator;
import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.model.Posting;
import de.htwsaar.nytSearchEngine.util.*;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Main main = new Main();

        //UNCOMMENT init TO START CREATING THE TABLE, PARSING, AND IMPORTING DATA TO THE TABLE (ONLY INITIALIZATION)
        //main.init();

        //UNCOMMENT start TO TEST THE COMMAND LINE OPTION
        //main.start();

        //UNCOMMENT manualSearch TO CALCULATE THE SCORE OF 3 QUERIES IN 4.3
        //main.manualSearch();

    }

    private void init(){
        //-----------CREATING TABLES FOR DATABASE (ONLY INITIALIZATION)
        DAOImpl.createTable();
        String nytPath = "E:\\Downloads\\Compressed\\nyt\\nyt\\data\\2000";

        //------------INSERT DATA TO tfs TABLE (calculate tfs)
        //UNCOMMENT THESE TO START INDEXING INTO TABLE tfs and docs, CHANGE TO YOUR OWN DIRECTORY
        Importer importer = new Importer();
        importer.importDirectory(nytPath);
    }

    private void start(){
        DAOImpl dao = new DAOImpl();
        boolean whileCase = true;
        Scanner scanner = new Scanner(System.in);
        String input;



        while (whileCase) {

            System.out.println("Welcome");
            System.out.println("Please enter your query");
            System.out.println("Input 0 for exit");

            input =scanner.nextLine();

            if(input.trim().isEmpty()){
                System.out.println("Please enter your query:");
            }else {
                if(input.equals("0")){
                    whileCase = false;
                }else {
                    System.out.println("Query: " + input);
                    System.out.println("Please wait system processing....");
                    QueryProcessor queryProcessor = new QueryProcessor();
                    List<Accumulator> accumulatorList1 = queryProcessor.process(input, 10);


                    int i = 0;
                    for (Accumulator a : accumulatorList1) {
                        Document document = dao.getDocumentByDid(a.getDid());

                        System.out.println("Rank: " + i + " "+ "Title: " + document.getTitle() + " " +"URL: " + document.getURL() + " score: " + a.getScore());
                        i++;
                    }
                }


            }





        }

        scanner.close();
    }


    private void manualSearch(){

        QueryProcessor queryProcessor = new QueryProcessor();
        List<Accumulator> accumulatorList1 = queryProcessor.process("summer olympics opening ceremony", 5);
        System.out.println("summer olympics opening ceremony");
        for (Accumulator a : accumulatorList1)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());

        List<Accumulator> accumulatorList2 = queryProcessor.process("alpine disaster kaprun", 5);
        System.out.println("alpine disaster kaprun");
        for (Accumulator a : accumulatorList2)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());

        List<Accumulator> accumulatorList3 = queryProcessor.process("concorde crash paris", 5);
        System.out.println("concorde crash paris");
        for (Accumulator a : accumulatorList3)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());

    }
}