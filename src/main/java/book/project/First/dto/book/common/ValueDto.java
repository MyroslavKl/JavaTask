package book.project.First.dto.book.common;

import lombok.Data;

@Data
public class ValueDto<T> {
    private final T value;
}