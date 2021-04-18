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
public enum StateValue {
  
  NEW("new"),
  
  ONHOLD("onHold"),
  
  VALIDATED("validated"),
  
  SENT("sent"),
  
  PARTIALLYPAID("partiallyPaid"),
  
  SETTLED("settled");

  private String value;

  StateValue(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StateValue fromValue(String text) {
    for (StateValue b : StateValue.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

