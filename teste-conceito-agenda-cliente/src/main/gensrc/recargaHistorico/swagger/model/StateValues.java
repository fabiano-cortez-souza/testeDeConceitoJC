package recargaHistorico.swagger.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 
 */
public enum StateValues {
  
  INPROGRESS("inProgress"),
  
  REJECTED("rejected"),
  
  DONE("done"),
  
  TERMINATEDWITHERROR("terminatedWithError");

  private String value;

  StateValues(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StateValues fromValue(String text) {
    for (StateValues b : StateValues.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

