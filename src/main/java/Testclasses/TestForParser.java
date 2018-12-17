package Testclasses;

import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestForParser
{

    public static void start() {
        //CHANGE TO YOUR OWN DIRECTORY CONTAINING THE XML-FILE
        Map<String, Integer> words = new HashMap<>();
        File file = new File("C:\\Users\\Agra Bimantara\\Documents\\nytSearchEngine\\nyt\\data\\2000\\01\\01\\1165027.xml");
        Document document = Parser.parse(file);
        //System.out.println("test");
        System.out.println("ID     : " + document.getId());
        System.out.println("Title  : " + document.getTitle());
        System.out.println("URL    : " + document.getUrl());
        System.out.println("Content: ");
        for(String string : document.getContent())
            //System.out.println(string);
            try {
                Parser.termFreq(string, words);
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println(words);
    }

}
