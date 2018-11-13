package de.htwsaar.nytSearchEngine.Model;


import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Document {


    private long id;
    private String title;
    private String url;
    private String[] content;

}
