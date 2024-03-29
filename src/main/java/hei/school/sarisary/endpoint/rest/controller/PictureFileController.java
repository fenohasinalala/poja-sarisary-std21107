package hei.school.sarisary.endpoint.rest.controller;

import hei.school.sarisary.model.rest.RestPicture;
import hei.school.sarisary.repository.model.Picture;
import hei.school.sarisary.service.PictureFileService;
import hei.school.sarisary.service.PictureService;
import lombok.AllArgsConstructor;
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

  @PutMapping(value = "/black-and-white/{id}")
  public ResponseEntity<String> getBlackAndWhiteImage(
      @PathVariable(name = "id") String id, @RequestBody(required = false) byte[] file) {

    File output = service.getBlackAndWhiteImage(id, file);
    if (output == null) {
      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("");
    }
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("OK");
  }

  @GetMapping(value = "/black-and-white/{id}")
  public RestPicture getPictureById(@PathVariable(name = "id") String id) {
    Picture picture = pictureService.getPictureById(id);
    RestPicture output = service.getRestPicture(picture);
    if (output == null) {
      RestPicture restPicture = new RestPicture();
      restPicture.setOriginal_url("https://original.url");
      restPicture.setTransformed_url("https://transformed.url");
      return restPicture;
    }
    return output;
  }
}
