package ro.bible.db.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPojo {
    private long bookId;
    private String name;
    private String abbreviation;
    private String testament;
}
