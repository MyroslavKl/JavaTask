package book.project.First.entity.book;

import lombok.Data;

import java.util.Date;


@Data
public class BookEntity {
    private Long Id;
    private String name;
    private String author;
    private String type;
    private Date releaseDate;
    private Long isbn;
    private String language;
    private int numberOfOPages;
    private String publishingHouse;
    private String Description;
}
