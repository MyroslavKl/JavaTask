package book.project.First.dto.book;

import lombok.Data;

import java.util.Date;

@Data
public class BaseBook {
    private String name;
    private String author;
    private String type;
    private Date releaseDate;
    private Long isbn;
    private String language;
    private int numberOfOPages;
    private String publishingHouse;
    private String description;

}
