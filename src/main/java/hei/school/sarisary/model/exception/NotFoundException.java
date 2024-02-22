package hei.school.sarisary.model.exception;

import static hei.school.sarisary.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class NotFoundException extends ApiException {
  public NotFoundException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
