package Testclasses;

import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestForParser
{

    public static void start() {
        Parser parser = new Parser();

        //CHANGE TO YOUR OWN DIRECTORY CONTAINING THE XML-FILE
        Map<String, Integer> words = new HashMap<>();
        File file = new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000\\01\\01\\1165027.xml");

        Document document = parser.parse(file);
        //System.out.println("test");
        System.out.println("ID     : " + document.getId());
        System.out.println("Title  : " + document.getTitle());
        System.out.println("URL    : " + document.getURL());
        System.out.println("Content: ");

    }

}
