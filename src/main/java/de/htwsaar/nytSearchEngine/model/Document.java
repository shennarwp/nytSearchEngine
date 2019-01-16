package de.htwsaar.nytSearchEngine.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for Document-Object
 *
 * @author Tobias Gottschalk
 */
@Getter @Setter @ToString
public class Document
{
    private long id;
    private String title;
    private String URL;
    private String[] content;
}
