package hei.school.sarisary.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestException {
  private String type;
  private String message;
}
