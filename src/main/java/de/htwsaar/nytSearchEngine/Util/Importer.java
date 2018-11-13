package de.htwsaar.nytSearchEngine.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

public class Importer {

    public void importFiles (File startFolder){
        //TODO Check if file is NULL

        File[] fileListe = startFolder.listFiles();
        for(File file : fileListe){
            if(file.isDirectory()){
                this.importFiles(file);
            }
            if(file.isFile()){
                System.out.println("File path is:" + file.getPath() + " " + "Size is:" + file.length());
            }
        }



    }


}
