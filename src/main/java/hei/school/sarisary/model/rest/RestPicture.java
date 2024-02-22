package hei.school.sarisary.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestPicture {
  private String id;
  private String originalPictureUrl;
  private String blackPictureUrl;
}
