package ro.bible.db.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.bible.db.entity.BookEntity;
import ro.bible.db.entity.ChapterEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterPojo {
    private int number;
    private BookPojo book;
    private List<VersePojo> versePojo;
}
