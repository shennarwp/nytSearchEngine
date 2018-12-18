package de.htwsaar.nytSearchEngine.util;

import com.nytlabs.corpus.NYTCorpusDocument;
import com.nytlabs.corpus.NYTCorpusDocumentParser;
import de.htwsaar.nytSearchEngine.model.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Parser class
 *
 * @author Tobias Gottschalk
 * @author Agra Bimantara
 * @author Shenna RWP
 */
public class Parser
{
    /**
     * parse xml file and create Document-object based on its attributes
     * @param file the xml file
     * @return Document-object
     */
    public static Document parse(File file) {
        final boolean VALIDATING = false;
        //Map<String, Integer> words = new HashMap<String, Integer>();

        NYTCorpusDocument nytDocument;
        Document document = new Document();
        NYTCorpusDocumentParser nytCorpusDocumentParser = new NYTCorpusDocumentParser();

        //Get a NYTCorpusDocument from a File
        nytDocument = nytCorpusDocumentParser.parseNYTCorpusDocumentFromFile(file,VALIDATING);

        //Wrap Information.
        document.setId(nytDocument.getGuid());
        document.setTitle(nytDocument.getHeadline());
        document.setUrl(nytDocument.getUrl().toString());

        //get body/content from NYTCorpusParser
        String body = nytDocument.getBody();
        document.setContent(Tokenizer.tokenizeString(body));

        return document;
    }

    //termFreq
    public static void termFreq(String contents, Map<String, Integer> words) throws FileNotFoundException {
        Scanner files =  new Scanner(contents);
        while(files.hasNext()){
            String word = files.next();
            Integer count = words.get(word);
            if (count != null)
                count++;
            else
                count= 1;

            words.put(word, count);
        }
        files.close();
    }
}

