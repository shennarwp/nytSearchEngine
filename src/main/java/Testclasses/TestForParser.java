package Testclasses;

import de.htwsaar.nytSearchEngine.Model.Document;
import de.htwsaar.nytSearchEngine.Util.Parser;

import java.io.File;

public class TestForParser {
    public static void start(){
        File file = new File("C:\\Users\\Tobias Gottschalk\\Documents\\archives\\2000\\01\\01\\1165027.xml");
        Document document = Parser.parse(file);
        System.out.println("test");
    }
}
