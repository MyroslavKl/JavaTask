package book.project.First.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
public class BookPatch {
    private String name;
    private String author;
    private String type;
    private Date releaseDate;
    private Long isbn;
    private String language;
    private int numberOfOPages;
    private String publishingHouse;
    private String description;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean empty = true;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isNameUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isAuthorUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isTypeUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isISBNUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isDateUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isLanguageUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isNumberOfPagesUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isPublishingHouseUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isDescriptionUpdated;

    public void setName(String name) {
        empty = false;
        isNameUpdated = true;

        this.name = name;
    }

    public void setAuthor(String author) {
        empty = false;
        isAuthorUpdated = true;

        this.author = author;
    }

    public void setType(String type) {
        empty = false;
        isTypeUpdated = true;

        this.type = type;
    }

    public void setISBN(Long isbn) {
        empty = false;
        isISBNUpdated = true;

        this.isbn = isbn;
    }

    public void setReleaseDate(Date releaseDate) {
        empty = false;
        isDateUpdated = true;

        this.releaseDate = releaseDate;
    }

    public void setLanguage(String language) {
        empty = false;
        isLanguageUpdated = true;

        this.language = language;
    }
    public void setNumberOfPages(int numberOfOPages) {
        empty = false;
        isNumberOfPagesUpdated = true;

        this.numberOfOPages = numberOfOPages;
    }

    public void setPublishingHouse(String publishingHouse) {
        empty = false;
        isPublishingHouseUpdated = true;

        this.publishingHouse = publishingHouse;
    }

    public void setDescription(String description) {
        empty = false;
        isDescriptionUpdated = true;

        this.description = description;
    }
}
