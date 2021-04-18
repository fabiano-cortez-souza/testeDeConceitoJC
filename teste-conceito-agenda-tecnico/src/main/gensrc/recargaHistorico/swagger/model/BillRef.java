package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Bill reference
 */
@ApiModel(description = "Bill reference")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class BillRef   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("@type")
  private String type = null;

  public BillRef id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier of the bill
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the bill")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BillRef href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Reference of the bill
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the bill")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public BillRef type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Indicates the (class) type of the bill
   * @return type
  **/
  @ApiModelProperty(value = "Indicates the (class) type of the bill")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BillRef billRef = (BillRef) o;
    return Objects.equals(this.id, billRef.id) &&
        Objects.equals(this.href, billRef.href) &&
        Objects.equals(this.type, billRef.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BillRef {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

