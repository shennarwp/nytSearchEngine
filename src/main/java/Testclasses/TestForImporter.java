package Testclasses;

import de.htwsaar.nytSearchEngine.Util.Importer;

import java.io.File;

public class TestForImporter {
    public static void startTest(){

        //Path to the start directory
        final String PATH_TO_FOLDER = "C:\\Users\\Tobias Gottschalk\\Documents\\archives";

        File file = new File(PATH_TO_FOLDER);
        Importer importer = new Importer();

        importer.importFiles(file);
    }
}
