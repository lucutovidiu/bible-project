package ro.bible.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
