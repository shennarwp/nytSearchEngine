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

public class Main {
    //Change the directory pathname
    //private static final File file = new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000\\01\\01\\1165027.xml");

    public static void main(String[] args) {
        //-----------CREATING TABLES FOR DATABASE (ONLY INITIALIZATION)
        DAOImpl.createTable();


        //------------INSERT DATA TO tfs TABLE (calculate tfs)
        //UNCOMMENT THESE TO START INDEXING INTO TABLE tfs and docs, CHANGE TO YOUR OWN DIRECTORY

        String nytPath = "C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000";

        Importer importer = new Importer();
        importer.importDirectory(nytPath);

        QueryProcessor queryProcessor = new QueryProcessor();
        List<Accumulator> accumulatorList1 = queryProcessor.process("olympics opening ceremony", 5);
        System.out.println("olympics opening ceremony");
        for (Accumulator a : accumulatorList1)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());

        List<Accumulator> accumulatorList2 = queryProcessor.process("denmark sweden bridge", 5);
        System.out.println("denmark sweden bridge");
        for (Accumulator a : accumulatorList2)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());

        List<Accumulator> accumulatorList3 = queryProcessor.process("tokyo train disaster", 5);
        System.out.println("tokyo train disaster");
        for (Accumulator a : accumulatorList3)
            System.out.println("did: " + a.getDid() + " score: " + a.getScore());
    }
}