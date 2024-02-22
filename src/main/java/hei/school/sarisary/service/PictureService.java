package hei.school.sarisary.service;

import hei.school.sarisary.model.exception.NotFoundException;
import hei.school.sarisary.repository.PictureRepository;
import hei.school.sarisary.repository.model.Picture;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PictureService {
  private final PictureRepository repository;

  @Transactional
  public Picture save(Picture picture) {
    return repository.save(picture);
  }

  public Picture getPictureById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Picture with id " + id + " not found"));
  }
}
