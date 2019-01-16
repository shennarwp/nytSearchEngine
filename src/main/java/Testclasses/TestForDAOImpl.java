package Testclasses;

import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Posting;
import de.htwsaar.nytSearchEngine.util.InvertedIndex;

import java.util.List;

public class TestForDAOImpl {

    public  static void testgetDidByTerm(){
        InvertedIndex invertedIndex = new InvertedIndex();

        List<Posting> postingList = invertedIndex.getIndexList("you");

        System.out.println(postingList);



        System.out.println(invertedIndex.getSize());

        System.out.println(invertedIndex.getLength(1));
    }
}
