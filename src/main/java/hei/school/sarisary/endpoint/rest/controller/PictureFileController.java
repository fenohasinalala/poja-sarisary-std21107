package hei.school.sarisary.endpoint.rest.controller;

import hei.school.sarisary.model.rest.RestPicture;
import hei.school.sarisary.repository.model.Picture;
import hei.school.sarisary.service.PictureFileService;
import hei.school.sarisary.service.PictureService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@AllArgsConstructor
public class PictureFileController {

  PictureFileService service;
  PictureService pictureService;

  @PutMapping(value = "/blacks/{id}")
  public ResponseEntity<FileSystemResource> getBlackAndWhiteImage(
      @PathVariable(name = "id") String id, @RequestBody(required = false) byte[] file) {

    File output = service.getBlackAndWhiteImage(id, file);
    FileSystemResource resource = new FileSystemResource(output);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
  }

  /*
  @PutMapping(value = "/blacks/{id}/mp")
  public File getBlackAndWhiteImageMultipart(
      @PathVariable(name = "id") String id,
      @RequestPart(value = "file", required = false) MultipartFile file)
      throws IOException {
    return service.getBlackAndWhiteImage(id, file.getBytes());
  }
   */

  @GetMapping(value = "/blacks/{id}")
  public RestPicture getPictureById(@PathVariable(name = "id") String id) {
    Picture picture = pictureService.getPictureById(id);
    return service.getRestPicture(picture);
  }
}
