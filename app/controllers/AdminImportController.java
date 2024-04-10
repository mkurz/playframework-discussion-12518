package controllers;

import play.libs.Files;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.*;

import java.io.IOException;

public class AdminImportController {
  public Result importProgram(Http.Request request) {
    Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
    if (body == null) {
      // The test below will trigger this block, even though it shouldn't
      return ok(adminImportView.render("Request did not contain a file."));
    }
    Http.MultipartFormData.FilePart<Files.TemporaryFile> uploadedFile = body.getFile("file");
    if (uploadedFile == null) {
      return ok(adminImportView.render("No file was uploaded."));
    }
    Files.TemporaryFile uploadedFileRef = uploadedFile.getRef();
    // ... file processing ...
   return ok(adminImportView.render(fileContent));
  }
}
