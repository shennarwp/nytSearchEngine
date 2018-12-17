package Testclasses;

import de.htwsaar.nytSearchEngine.Util.Importer;

import java.io.File;

public class TestForImporter
{
    public static void startTest() {

        //Path to the start directory
        //CHANGE TO YOUR OWN DIRECTORY CONTAINING THE XML-FILES
        final String PATH_TO_FOLDER = "C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000";

        File file = new File(PATH_TO_FOLDER);
        Importer importer = new Importer();

        importer.importFile(file);
    }
}
