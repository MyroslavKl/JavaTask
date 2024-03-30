package book.project.First.filter;

import lombok.Data;

@Data
public class BookFilterOptions {
    private String name;
    private String author;
    private String type;
    private String language;
    private int numberOfOPages;
}
