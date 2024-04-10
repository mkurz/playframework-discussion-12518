package controllers;

import org.apache.pekko.stream.javadsl.FileIO;
import org.apache.pekko.stream.javadsl.Source;
import org.apache.pekko.util.ByteString;
import org.junit.Test;
import play.api.test.CSRFTokenHelper;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.OK;
import static play.test.Helpers.contentAsString;
import static play.api.test.CSRFTokenHelper.addCSRFToken;

public class AdminImportControllerTest extends WithApplication {
    private static final String TEST_FILE_CONTENT = "{  \"id\" : 32,  \"adminName\" : \"email-program\", \"adminDescription\" : \"\"}";

    @Test
    public void importProgram_resultHasFileContent() throws IOException {

        File file = getFile();
        Http.MultipartFormData.Part<Source<ByteString, ?>> part =
                new Http.MultipartFormData.FilePart<>(
                        "file",
                        "test.json",
                        Http.MimeTypes.JSON,
                        FileIO.fromPath(file.toPath()),
                        Files.size(file.toPath()));

        Http.RequestBuilder request =
                addCSRFToken(Helpers.fakeRequest()
                        .uri(routes.AdminImportController.importProgram().url())
                        .method("POST")
                        .bodyRaw(
                                Collections.singletonList(part),
                                play.libs.Files.singletonTemporaryFileCreator(),
                                app.asScala().materializer()));

        Result result = Helpers.route(app, request);
        assertThat(result.status()).isEqualTo(OK);
        assertThat(contentAsString(result)).contains(TEST_FILE_CONTENT);
    }

    private File getFile() {
        String filePath = "/tmp/output.json";
        try {
            FileWriter file = new FileWriter(filePath);
            file.write(TEST_FILE_CONTENT);
            file.close();
            return new File(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
