package de.htwsaar.nytSearchEngine;

import Testclasses.TestForImporter;
import Testclasses.TestForParser;
import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Index;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;

public class Main
{
    //Change the directory pathname
    //private static final File file = new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000\\01\\01\\1165027.xml");

    public static void main(String [] args) {
        //----------CHANGE THE DIRECTORIES INSIDE BOTH OF THE TESTCLASSES TO YOUR OWN DIRECTORY CONTAINING THE XML-FILES
        //TestForImporter.startTest();
        //TestForParser.start();


        //-----------CREATING TABLES FOR DATABASE (ONLY INITIALIZATION)
        //DAOImpl.createTable();


        //------------INSERT DATA TO DOCS-TABLE
//        Document document = Parser.parse(file) ;
//        DAOImpl insertDocs = new DAOImpl();
//
//        insertDocs.insertIntoDocs(document.getId(),document.getTitle(),document.getUrl());

        //-------------INSERT DATA TO TFS-TABLE
        Index index = new Index();
        index.indexFD();
    }
}
