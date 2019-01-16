package de.htwsaar.nytSearchEngine.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import dao.DAOImpl;
import de.htwsaar.nytSearchEngine.model.Document;
import de.htwsaar.nytSearchEngine.util.Parser;

import static de.htwsaar.nytSearchEngine.constants.PathConstant.PATH_TO_FOLDER;

public class Index {

    private DAOImpl dao = new DAOImpl();


    public void indexFD(){
        //Change the directory pathname
        //final String PATH_TO_FOLDER = "C:\\Users\\ruip_\\Downloads\\nyt\\nyt\\data\\2000\\01\\01\\1165099.xml";

        File file = new File(PATH_TO_FOLDER);
        de.htwsaar.nytSearchEngine.util.Importer importer = new de.htwsaar.nytSearchEngine.util.Importer();


        List<File> files =  importer.returnListofFiles(file);
        ArrayList<Document> documents = new ArrayList<>();

        for (File fileItem: files){
            Document document =  Parser.parse(fileItem);
            dao.insertIntoDocs(document.getId(),document.getTitle(),document.getUrl());
            calctf(document);
        }


    }

    /**
     * calculating the term frequency and insert them into tfs-table
     * @param document
     */
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
