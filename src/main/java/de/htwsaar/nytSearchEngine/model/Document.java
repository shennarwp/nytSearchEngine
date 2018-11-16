package de.htwsaar.nytSearchEngine.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Model class for Document-Object
 *
 * @author Tobias Gottschalk
 */
@Getter @Setter
public class Document
{
    private long id;
    private String title;
    private String url;
    private String[] content;
}
