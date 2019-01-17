package de.htwsaar.nytSearchEngine.util;



import de.htwsaar.nytSearchEngine.model.Accumulator;
import de.htwsaar.nytSearchEngine.model.Posting;
import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {
//	List<Accumulator> process(String query) {
//		String[] queryArray = tokenizeString(query);
//		for(String s : queryArray) {
//
//		}
//	}
//}


    public List<Accumulator> process(String query, int k) {
        InvertedIndex invertedIndex = new InvertedIndex();
        String[] queryArray = Tokenizer.tokenizeString(query);
        ArrayList<List<Posting>> listOfPostingLists = new ArrayList<>();

        List<Posting> postingList1 = invertedIndex.getIndexList(queryArray[i]);
        List<Posting> postingList2 = invertedIndex.getIndexList(queryArray[i]);
        List<Posting> postingList3 = invertedIndex.getIndexList(queryArray[i]);


        for (int i = 0; i < queryArray.length; i++) {
            //List<Posting> postingList = invertedIndex.getIndexList(queryArray[i]);
            //listOfPostingLists.add(postingList);
        }





        return null;
    }

}