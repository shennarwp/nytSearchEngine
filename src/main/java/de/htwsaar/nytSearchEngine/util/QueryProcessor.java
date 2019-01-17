package de.htwsaar.nytSearchEngine.util;



import de.htwsaar.nytSearchEngine.model.Accumulator;
import de.htwsaar.nytSearchEngine.model.Posting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import static de.htwsaar.nytSearchEngine.util.Tokenizer.tokenizeString;


public class QueryProcessor {
    public List<Accumulator> process(String query) {

        List<Accumulator> accList = new ArrayList<>();
        InvertedIndex invertedIndex = new InvertedIndex();
        int docSize = invertedIndex.getSize();

        String[] queryArray = tokenizeString(query);
        for(String term : queryArray) {

            List<Posting> postingList = invertedIndex.getIndexList(term);
            TreeMap<Long, Accumulator> mapAcc = new TreeMap<>();


            int docFreq = postingList.size();


            for(Posting p : postingList) {
                if(mapAcc.containsKey(p.getDid())) {
                    Accumulator acc = mapAcc.get(p.getDid());
                    double oldscore = acc.getScore();
                    double newscore = p.getTf() * Math.log((double)docSize / docFreq);
                    acc.setScore(oldscore + newscore);
                } else {
                    Accumulator acc = new Accumulator();
                    acc.setDid(p.getDid());
                    double score = p.getTf() * Math.log((double)docSize / docFreq);
                    acc.setScore(score);

                    mapAcc.put(acc.getDid(), acc);
                }

            }

            accList.addAll(mapAcc.values());
        }
        accList.sort(Comparator.comparing(Accumulator::getScore).reversed());
        return accList;

    }

    public List<Accumulator> process(String query, int k) {
        return process(query).subList(0, k);
    }
}