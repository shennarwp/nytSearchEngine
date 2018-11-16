package de.htwsaar.nytSearchEngine.util;

import java.io.File;

/**
 * Importer-class
 *
 * @author Tobias Gottschalk
 * @author Agra Bimantara
 * @author Shenna RWP
 */
public class Importer
{
    public void importFiles (File startFolder) {
        //TODO Check if file is NULL

        File[] fileListe = startFolder.listFiles();
        for(File file : fileListe) {
            if(file.isDirectory()) {
                this.importFiles(file);
            }
            if(file.isFile()) {
                System.out.println("File path is:" + file.getPath() + " " + "Size is:" + file.length());
            }
        }
    }


}
