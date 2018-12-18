package de.htwsaar.nytSearchEngine.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

public class Index {

    DAOImpl dao = new DAOImpl();


    public void indexFD(){
        final String PATH_TO_FOLDER = "C:\\Users\\Tobias\\Documents\\2000\\";

        File file = new File(PATH_TO_FOLDER);
        de.htwsaar.nytSearchEngine.Util.Importer importer = new de.htwsaar.nytSearchEngine.Util.Importer();


        List<File> files =  importer.returnListofFiles(file);
        ArrayList<Document> documents = new ArrayList<>();

        for (File fileItem: files){
          Document document =  Parser.parse(fileItem);
          //documents.add(document);
          calctf(document);
        }


    }

    private void calctf(Document document){
        String[] content = document.getContent();
        HashMap<String, Integer> tfDocument = new HashMap<>();

        for(int i =0; i<content.length;i++){
            if (tfDocument.containsKey(content[i])){
                Integer tf = tfDocument.get(content[i]);
                tfDocument.put(content[i],tf+1);
            }else {
                tfDocument.put(content[i],1);
            }


        }

        for(String inhalt : tfDocument.keySet()){
            System.out.println("docID: " + document.getId() + " " + inhalt + " " + tfDocument.get(inhalt));
            dao.insertIntoTfs(document.getId(),inhalt,tfDocument.get(inhalt));

        }
    }
}
