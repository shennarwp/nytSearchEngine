package Testclasses;

import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import java.io.File;

public class TestForParser
{
    public static void start() {
        //CHANGE TO YOUR OWN DIRECTORY CONTAINING THE XML-FILE
        File file = new File("C:\\Users\\ruip_\\Downloads\\nyt\\data\\2000\\01\\01\\1165027.xml");
        Document document = Parser.parse(file);
        System.out.println("test");
        System.out.println(document.getId());
        System.out.println(document.getTitle());
        System.out.println(document.getUrl());
        for(String string : document.getContent())
            System.out.println(string);
    }
}
