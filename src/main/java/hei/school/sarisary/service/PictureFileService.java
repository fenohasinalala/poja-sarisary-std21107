package hei.school.sarisary.service;

import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.model.exception.BadRequestException;
import hei.school.sarisary.model.rest.RestPicture;
import hei.school.sarisary.repository.model.Picture;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PictureFileService {

  BucketComponent bucketComponent;
  PictureService pictureService;

  public File getBlackAndWhiteImage(String id, byte[] file) {
    try {
      if (file == null) {
        throw new BadRequestException("Image file is mandatory");
      }
      String fileSuffix = ".jpeg";
      String inputFilePrefix = id + fileSuffix;
      String outputFilePrefix = id + "-black" + fileSuffix;
      File originalTmpFile;
      File outputTmpFile;
      try {
        originalTmpFile = File.createTempFile(inputFilePrefix, fileSuffix);
        outputTmpFile = File.createTempFile(outputFilePrefix, fileSuffix);
      } catch (IOException e) {
        throw new RuntimeException("Creation of temp file failed");
      }
      writeFileFromByteArray(file, originalTmpFile);

      File blackImageFile = convertImageToBlackAndWhite(originalTmpFile, outputTmpFile);
      String originalBucketKey = getImageBucketName(inputFilePrefix);
      String blackBucketKey = getImageBucketName(outputFilePrefix);
      uploadImageFile(originalTmpFile, originalBucketKey);
      uploadImageFile(blackImageFile, blackBucketKey);
      Picture toSave = new Picture(id, originalBucketKey, blackBucketKey, null, null);
      pictureService.save(toSave);
      return bucketComponent.download(blackBucketKey);
    } catch (Exception e) {
      return null;
    }
  }

  private File convertImageToBlackAndWhite(File originalFile, File outputFile) {
    ImagePlus image = IJ.openImage(originalFile.getPath());
    try {
      ImageConverter converter = new ImageConverter(image);
      converter.convertToGray8();
    } catch (Exception e) {
      throw new BadRequestException("Image file invalid");
    }
    // Save the grayscale image as a new file
    ij.io.FileSaver fileSaver = new ij.io.FileSaver(image);
    fileSaver.saveAsJpeg(outputFile.getPath());
    return outputFile;
  }

  private void uploadImageFile(File imageFile, String bucketKey) {
    bucketComponent.upload(imageFile, bucketKey);
    imageFile.delete();
  }

  private String getImageBucketName(String filename) {
    return "image/" + filename;
  }

  public RestPicture getRestPicture(Picture picture) {
    try {
      RestPicture restPicture = new RestPicture();
      restPicture.setOriginal_url(
          bucketComponent.presign(picture.getOriginalBucketKey(), Duration.ofHours(12)).toString());
      restPicture.setTransformed_url(
          bucketComponent.presign(picture.getBlackBucketKey(), Duration.ofHours(12)).toString());
      return restPicture;
    } catch (Exception e) {
      return null;
    }
  }

  private File writeFileFromByteArray(byte[] bytes, File file) {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(bytes);
      return file;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
