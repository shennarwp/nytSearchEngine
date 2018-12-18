package de.htwsaar.nytSearchEngine;

import Testclasses.TestForImporter;
import Testclasses.TestForParser;
import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;

public class Main
{
    private static File file = new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000\\01\\01\\1165029.xml");

    public static void main(String [] args) {
        //----------CHANGE THE DIRECTORIES INSIDE BOTH OF THE TESTCLASSES TO YOUR OWN DIRECTORY CONTAINING THE XML-FILES
        //TestForImporter.startTest();
        TestForParser.start();
        //--------------------------------------------------------------------------------------------
        //DAOImpl.createTable();
        //--------------------------------------------------------------------------------------------
        /*Document document = Parser.parse(file) ;
        DAOImpl insertDocs = new DAOImpl();

        insertDocs.insertIntoDocs(document.getId(),document.getTitle(),document.getUrl());*/
        //--------------------------------------------------------------------------------------------
        //insertDocs.insertIntoTfs(document.getId(),);
    }
}
