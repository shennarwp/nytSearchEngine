package de.htwsaar.nytSearchEngine.util;

import com.nytlabs.corpus.NYTCorpusDocument;
import com.nytlabs.corpus.NYTCorpusDocumentParser;
import de.htwsaar.nytSearchEngine.util.Tokenizer;
import de.htwsaar.nytSearchEngine.model.Document;

import java.io.File;

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
}
