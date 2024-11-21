package ro.bible.importexport.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import ro.bible.importexport.service.YamlExporterService;
import ro.bible.importexport.util.BookPojoYamlPathUtil;
import ro.bible.persistence.model.BookPojo;
import ro.bible.shared.util.FileUtil;

@ApplicationScoped
public class YamlFileExporterService implements YamlExporterService {

    @Override
    public void exportYaml(BookPojo bookPojo, String bookContent) {
        FileUtil.createFolderIfNotExists(new BookPojoYamlPathUtil(bookPojo.getTestament(), bookPojo.getAbbreviation())
                .getYamlFullBasePath());

        String bookPath = new BookPojoYamlPathUtil(bookPojo.getTestament(), bookPojo.getAbbreviation())
                .getYamlFullBaseFile();

        boolean success = FileUtil.writeContentToFile(bookPath, bookContent);
        handleResponseLog(success, bookPojo.getName(), bookPath);
    }

    private void handleResponseLog(boolean success, String bookName, String path) {
        if (success) {
            Log.infof("Book %s exported to path: '%s'", bookName, path);
        } else {
            Log.errorf("Book %s could not be exported to path: '%s'", bookName, path);
        }
    }
}
