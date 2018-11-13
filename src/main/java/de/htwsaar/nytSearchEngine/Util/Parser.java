package de.htwsaar.nytSearchEngine.Util;

import com.nytlabs.corpus.NYTCorpusDocument;
import com.nytlabs.corpus.NYTCorpusDocumentParser;
import de.htwsaar.nytSearchEngine.Model.Document;

import java.io.File;

public class Parser {

    public static Document parse(File file){
        final boolean VALIDATING = false;

        NYTCorpusDocument nytDocument;
        Document document = new Document();
        NYTCorpusDocumentParser nytCorpusDocumentParser = new NYTCorpusDocumentParser();

        //Get a NYTCorpusDocument from a File
        nytDocument = nytCorpusDocumentParser.parseNYTCorpusDocumentFromFile(file,VALIDATING);

        //Wrap Information.
        document.setId(nytDocument.getGuid());
        document.setTitle(nytDocument.getHeadline());
        document.setUrl(nytDocument.getUrl().toString());


        // TODO The entire body is in one field. Tokenization her?
        String[] contentBody = new String[1];
        contentBody[1] = nytDocument.getBody();
        document.setContent(contentBody);

        return document;
    }
}
