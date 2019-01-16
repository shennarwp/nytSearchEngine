package de.htwsaar.nytSearchEngine.util;

import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Posting;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InvertedIndex {

    DAOImpl dao = new DAOImpl();

    public List<Posting> getIndexList(String term){
        List<Posting> postingList = new ArrayList<>();
        TreeMap<Integer,Integer> treeMap;

        treeMap = dao.getDidByTerm(term);

        if(treeMap == null){
            return null;
        }

        for(Map.Entry<Integer,Integer> entry : treeMap.entrySet()) {
            Integer did = entry.getKey();
            Integer tf = entry.getValue();

            Posting posting = new Posting();

            posting.setDid(did);
            posting.setTf(tf);

            postingList.add(posting);
        }


        return postingList;
    }

    public int getDF(String term){

        return dao.getDF(term);
    }

    public int getSize(){

        return dao.getSize();
    }

    public int getLength(long did){

        return dao.getSize();
    }
}