package book.project.First.repository.implementation;

import book.project.First.dto.book.BookPatch;
import book.project.First.entity.book.BookEntity;
import book.project.First.exceptions.DataConflictException;
import book.project.First.exceptions.NotFoundException;
import book.project.First.filter.BookFilterOptions;
import book.project.First.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;


@Repository
@AllArgsConstructor
public class BookRepositoryImplementation implements BookRepository {
    private static final String SELECT_BOOK_COUNT_QUERY = """
            SELECT COUNT(1) FROM books
            """;

    private static final String UPDATE_BOOK_BY_ID_QUERY = """
        UPDATE books SET
            name = :name,
            author = :author,
            type = :type,
            releaseDate = :releaseDate,
            isbn = :isbn,
            language = :language,
            numberOfPages = :numberOfPages,
            publishingHouse = :publishingHouse,
            description = :description
        WHERE id = :id
        """;

    private static final String PATCH_BOOK_BY_ID_QUERY_TEMPLATE = """
            UPDATE books SET
                %s
            WHERE id = :id
            """;

    private static final String DELETE_BOOK_BY_ID_QUERY = """
            DELETE FROM books WHERE id = :id
            """;


    private static final String INSERT_BOOK_QUERY = """
            INSERT INTO books (
                name,
                author,
                type,
                releaseDate,
                isbn,
                language,
                numberOfPages,
                publishingHouse,
                description
            ) VALUES (
                :name,
                :author,
                :type,
                :releaseDate,
                :isbn,
                :language,
                :numberOfPages,
                :publishingHouse,
                :description
            )
            """;


    private static final String SELECT_BOOKS_QUERY = """
            SELECT
                id,
                name,
                author,
                type,
                releaseDate,
                isbn,
                language,
                numberOfPages,
                publishingHouse,
                description
            FROM books
            """;

    private static final String SELECT_BOOK_BY_ID_QUERY = """
            SELECT
                id,
                name,
                author,
                type,
                releaseDate,
                isbn,
                language,
                numberOfPages,
                publishingHouse,
                description
            FROM books
            WHERE id = :id
            """;

    private static final RowMapper<BookEntity> BOOK_ROW_MAPPER = (rs, rowNum) -> {
        BookEntity entity = new BookEntity();

        entity.setId(rs.getObject("id", Long.class));
        entity.setName(rs.getString("name"));
        entity.setAuthor(rs.getString("author"));
        entity.setType(rs.getString("type"));
        entity.setReleaseDate(rs.getDate("releaseDate"));
        entity.setIsbn(rs.getObject("isbn", Long.class));
        entity.setLanguage(rs.getString("language"));
        entity.setNumberOfOPages(rs.getInt("numberOfPages"));
        entity.setPublishingHouse(rs.getString("publishingHouse"));
        entity.setDescription(rs.getString("description"));

        return entity;
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public BookEntity Find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID_QUERY, new MapSqlParameterSource("id", id), BOOK_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Book with id " + id + " not found!");
        }
    }

    @Override
    public BookEntity Create(BookEntity bookEntity) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", bookEntity.getId())
                .addValue("name", bookEntity.getName())
                .addValue("author", bookEntity.getAuthor())
                .addValue("type", bookEntity.getType())
                .addValue("releaseDate", bookEntity.getReleaseDate())
                .addValue("isbn", bookEntity.getIsbn())
                .addValue("language", bookEntity.getLanguage())
                .addValue("numberOfPages", bookEntity.getNumberOfOPages())
                .addValue("publishingHouse", bookEntity.getPublishingHouse())
                .addValue("description", bookEntity.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(INSERT_BOOK_QUERY, sqlParameters, keyHolder);
        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Book with name \"%s\" already exists!", bookEntity.getName()));
            }

            throw e;
        }

        Long id = (Long) keyHolder.getKeys().get("id");
        bookEntity.setId(id);

        return bookEntity;
    }

    @Override
    public List<BookEntity> FindAll(BookFilterOptions bookOptions, Integer limit, Integer offset) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_BOOKS_QUERY);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        appendConditions(queryBuilder, parameters, bookOptions);

        if (limit != null) {
            queryBuilder.append(" LIMIT :limit");
            parameters.addValue("limit", limit);
        }

        if (offset != null && offset != 0) {
            queryBuilder.append(" OFFSET :offset");
            parameters.addValue("offset", offset);
        }

        String query = queryBuilder.toString();

        return jdbcTemplate.query(query, parameters, BOOK_ROW_MAPPER);
    }

    @Override
    public int Count(BookFilterOptions bookOptions) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_BOOK_COUNT_QUERY);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        appendConditions(queryBuilder, parameters, bookOptions);

        String query = queryBuilder.toString();

        return jdbcTemplate.queryForObject(query, parameters, Integer.class);
    }
    @Override
    public void Update(BookEntity bookEntity) {
        int affectedRows;
        try {
            affectedRows = jdbcTemplate.update(UPDATE_BOOK_BY_ID_QUERY, new MapSqlParameterSource()
                    .addValue("id", bookEntity.getId())
                    .addValue("name", bookEntity.getName())
                    .addValue("author", bookEntity.getAuthor())
                    .addValue("type", bookEntity.getType())
                    .addValue("releaseDate", bookEntity.getReleaseDate())
                    .addValue("isbn", bookEntity.getIsbn())
                    .addValue("language", bookEntity.getLanguage())
                    .addValue("numberOfPages", bookEntity.getNumberOfOPages())
                    .addValue("publishingHouse", bookEntity.getPublishingHouse())
                    .addValue("description", bookEntity.getDescription())
            );
        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Book with name \"%s\" already exists!", bookEntity.getName()));
            }

            throw e;
        }

        if (affectedRows == 0) {
            throw new NotFoundException("Book with id " + bookEntity.getId() + " not found!");
        }
    }

    @Override
    public void Patch(Long id, BookPatch bookPatch) {
        List<String> assignments = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);

        if (bookPatch.isNameUpdated()) {
            assignments.add("name = :name");
            parameters.addValue("name", bookPatch.getName());
        }

        if (bookPatch.isAuthorUpdated()) {
            assignments.add("author = :author");
            parameters.addValue("author", bookPatch.getAuthor());
        }

        if (bookPatch.isTypeUpdated()) {
            assignments.add("type = :type");
            parameters.addValue("type", bookPatch.getType());
        }

        if (bookPatch.isDateUpdated()) {
            assignments.add("date = :date");
            parameters.addValue("date", bookPatch.getReleaseDate());
        }

        if (bookPatch.isISBNUpdated()) {
            assignments.add("isbn = :isbn");
            parameters.addValue("isbn", bookPatch.getIsbn());
        }

        if (bookPatch.isLanguageUpdated()) {
            assignments.add("language = :language");
            parameters.addValue("language", bookPatch.getLanguage());
        }

        //

        if (bookPatch.isNumberOfPagesUpdated()) {
            assignments.add("numberOfPages = :numberOfPages");
            parameters.addValue("numberOfPages", bookPatch.getNumberOfOPages());
        }

        if (bookPatch.isPublishingHouseUpdated()) {
            assignments.add("publishingHouse = :publishingHouse");
            parameters.addValue("publishingHouse", bookPatch.getPublishingHouse());
        }

        if (bookPatch.isDescriptionUpdated()) {
            assignments.add("description = :description");
            parameters.addValue("description", bookPatch.getDescription());
        }

        String assigmentStr = String.join(", ", assignments);
        String query = String.format(PATCH_BOOK_BY_ID_QUERY_TEMPLATE, assigmentStr);

        int affectedRows;
        try {
            affectedRows = jdbcTemplate.update(query, parameters);
        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Book with name \"%s\" already exists!", bookPatch.getName()));
            }

            throw e;
        }

        if (affectedRows == 0) {
            throw new NotFoundException("Book with id " + id + " not found!");
        }
    }

    @Override
    public void Delete(Long id) {
        int affectedRows = jdbcTemplate.update(DELETE_BOOK_BY_ID_QUERY, new MapSqlParameterSource("id", id));

        if (affectedRows == 0) {
            throw new NotFoundException("Book with id " + id + " not found!");
        }
    }


    private void appendConditions(StringBuilder queryBuilder, MapSqlParameterSource parameters, BookFilterOptions bookOptions) {
        List<String> conditions = new ArrayList<>();

        String namePararm = bookOptions.getName();
        if (namePararm != null) {
            conditions.add("name LIKE(:name)");
            parameters.addValue("name", "%" + namePararm + "%");
        }

        String authorPararm = bookOptions.getAuthor();
        if (authorPararm != null) {
            conditions.add("author LIKE(:author)");
            parameters.addValue("author", "%" + authorPararm + "%");
        }

        String typePararm = bookOptions.getType();
        if (typePararm != null) {
            conditions.add("type LIKE(:type)");
            parameters.addValue("type", "%" + typePararm + "%");
        }

        String languagePararm = bookOptions.getLanguage();
        if (languagePararm != null) {
            conditions.add("language LIKE(:language)");
            parameters.addValue("language", "%" + languagePararm + "%");
        }

        int pagesPararm = bookOptions.getNumberOfOPages();
        if (pagesPararm != 0) {
            conditions.add("numberOfPages LIKE(:numberOfPages)");
            parameters.addValue("numberOfPages", "%" + pagesPararm + "%");
        }

        if (!conditions.isEmpty()) {
            String conditionStr = String.join(" AND ", conditions);

            queryBuilder.append(" WHERE ");
            queryBuilder.append(conditionStr);
        }
    }
}
