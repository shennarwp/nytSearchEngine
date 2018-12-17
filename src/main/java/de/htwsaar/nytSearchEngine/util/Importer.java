package de.htwsaar.nytSearchEngine.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

/**
 * Importer-class
 *
 * @author Tobias Gottschalk
 * @author Agra Bimantara
 * @author Shenna RWP
 */
public class Importer
{
    /**
     * Traverses the given root recursively, collects all files with suffix
     * .xml, and appends them to the given list.
     *
     * @param root Root to be traversed.
     * @param files List to which found files are appended.
     */
    private void importDirectory(File root, List<File> files) {
        if (root.isFile()) {
            if (root.getAbsolutePath().endsWith(".xml")) {
                files.add(root);
            }
        } else {
            for (File file : root.listFiles()) {
                importDirectory(file, files);
            }
        }
    }

    public void importFile(File file) {
        LinkedList<File> xmlFiles = new LinkedList<File>();
        importDirectory(file, xmlFiles);

        // print file names and sizes
        for(File xmlFile : xmlFiles) {
            System.out.println(xmlFile.getAbsolutePath() + " " + xmlFile.length());
        }
    }

   /* public static void main(String[] args) {
        Importer importer = new Importer();
        importer.importFile(new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data"));
    }*/

}
