package de.htwsaar.nytSearchEngine.util;



import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Accumulator;
import de.htwsaar.nytSearchEngine.model.Posting;

import java.util.*;

import static de.htwsaar.nytSearchEngine.util.Tokenizer.WHITESPACES;
import static de.htwsaar.nytSearchEngine.util.Tokenizer.tokenizeString;

/**
 * class for processing query by user
 */
public class QueryProcessor {

    /**
     * this function will process a query, read all posting list,
     * and count the score for each Accumulator
     * @param query from user
     * @return the List of Accumulator
     */
    List<Accumulator> process(String query) {

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
        List<Accumulator> boostedList = boostScore(accList, query);
        boostedList.sort(Comparator.comparing(Accumulator::getScore).reversed());
        return boostedList;

    }

    /**
     * this method will call the other process(), but only return top k results
     * @param query query from user
     * @param k how many results to be returned
     * @return the List of Accumulator
     */
    public List<Accumulator> process(String query, int k) {
        return process(query).subList(0, k);
    }

    /**
     * this method will boost score in accumulator by 5%
     * for each term in query found in the title
     * @param acc the List of Accumulator that want to be boosted
     * @param query the query from user
     * @return the boosted List
     */
    private List<Accumulator> boostScore(List<Accumulator> acc, String query) {
        DAOImpl dao = new DAOImpl();
        List<String> queryList = Arrays.asList(query.split(WHITESPACES));

        for(Accumulator a : acc) {
            String title = String.join(" ", tokenizeString(dao.getTitleByDid(a.getDid())));
            double contains =  queryList.stream()
                                      .map(title::contains)
                                      .mapToDouble(t -> 0.05)
                                      .sum();
            a.setScore(a.getScore() * (1 + contains));
        }
        return acc;
    }
}