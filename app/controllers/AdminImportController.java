package controllers;

import play.libs.Files;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.*;

import java.io.IOException;

public class AdminImportController extends Controller {
  public Result importProgram(Http.Request request) {
    Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
    if (body == null) {
      // The test below will trigger this block, even though it shouldn't
      return ok("Request did not contain a file.");
    }
    Http.MultipartFormData.FilePart<Files.TemporaryFile> uploadedFile = body.getFile("file");
    if (uploadedFile == null) {
      return ok("No file was uploaded.");
    }
    Files.TemporaryFile uploadedFileRef = uploadedFile.getRef();
    // ... file processing ...
    String content = null;
    try {
      content = java.nio.file.Files.readString(uploadedFileRef.path());
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return ok(content); // ok(adminImportView.render(fileContent));
  }
}
