package de.htwsaar.nytSearchEngine;

import Testclasses.TestForImporter;
import Testclasses.TestForParser;
import de.htwsaar.nytSearchEngine.Util.Index;

public class Main
{
    public static void main(String [] args) {
        //----------CHANGE THE DIRECTORIES INSIDE BOTH OF THE TESTCLASSES TO YOUR OWN DIRECTORY CONTAINING THE XML-FILES
        //TestForImporter.startTest();
        //TestForParser.start();
        Index index = new Index();
        index.indexFD();
    }
}
