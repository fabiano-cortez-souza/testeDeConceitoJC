package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import recargaHistorico.swagger.model.ZErrorDetail;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 
 */
@ApiModel(description = "")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class ZError   {
  @JsonProperty("code")
  private Integer code = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("infoURL")
  private String infoURL = null;

  @JsonProperty("details")
  private List<ZErrorDetail> details = null;

  public ZError code(Integer code) {
    this.code = code;
    return this;
  }

   /**
   * 
   * @return code
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ZError message(String message) {
    this.message = message;
    return this;
  }

   /**
   * 
   * @return message
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ZError description(String description) {
    this.description = description;
    return this;
  }

   /**
   * 
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ZError infoURL(String infoURL) {
    this.infoURL = infoURL;
    return this;
  }

   /**
   * 
   * @return infoURL
  **/
  @ApiModelProperty(value = "")


  public String getInfoURL() {
    return infoURL;
  }

  public void setInfoURL(String infoURL) {
    this.infoURL = infoURL;
  }

  public ZError details(List<ZErrorDetail> details) {
    this.details = details;
    return this;
  }

  public ZError addDetailsItem(ZErrorDetail detailsItem) {
    if (this.details == null) {
      this.details = new ArrayList<ZErrorDetail>();
    }
    this.details.add(detailsItem);
    return this;
  }

   /**
   * 
   * @return details
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ZErrorDetail> getDetails() {
    return details;
  }

  public void setDetails(List<ZErrorDetail> details) {
    this.details = details;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ZError zerror = (ZError) o;
    return Objects.equals(this.code, zerror.code) &&
        Objects.equals(this.message, zerror.message) &&
        Objects.equals(this.description, zerror.description) &&
        Objects.equals(this.infoURL, zerror.infoURL) &&
        Objects.equals(this.details, zerror.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, description, infoURL, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ZError {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    infoURL: ").append(toIndentedString(infoURL)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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

