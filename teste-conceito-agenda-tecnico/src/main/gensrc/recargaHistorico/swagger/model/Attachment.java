package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Attachment refers to extensions or additional parts that is or may be attached to something (agreements, contracts, appointments) to perform a particular function. They can be communication attachments, documents and other.
 */
@ApiModel(description = "Attachment refers to extensions or additional parts that is or may be attached to something (agreements, contracts, appointments) to perform a particular function. They can be communication attachments, documents and other.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class Attachment   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("mimeType")
  private String mimeType = null;

  @JsonProperty("sizeUnit")
  private String sizeUnit = null;

  @JsonProperty("size")
  private Float size = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  public Attachment id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier of the attachment
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the attachment")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Attachment href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Reference of the attachment
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the attachment")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Attachment name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the attachment
   * @return name
  **/
  @ApiModelProperty(value = "Name of the attachment")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Attachment description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Free short description of the attachment
   * @return description
  **/
  @ApiModelProperty(value = "Free short description of the attachment")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Attachment mimeType(String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

   /**
   * Mime type of the attachment
   * @return mimeType
  **/
  @ApiModelProperty(value = "Mime type of the attachment")


  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public Attachment sizeUnit(String sizeUnit) {
    this.sizeUnit = sizeUnit;
    return this;
  }

   /**
   * Size unit (in bytes by default)
   * @return sizeUnit
  **/
  @ApiModelProperty(value = "Size unit (in bytes by default)")


  public String getSizeUnit() {
    return sizeUnit;
  }

  public void setSizeUnit(String sizeUnit) {
    this.sizeUnit = sizeUnit;
  }

  public Attachment size(Float size) {
    this.size = size;
    return this;
  }

   /**
   * The size  in Bytes of the document or attachment
   * @return size
  **/
  @ApiModelProperty(value = "The size  in Bytes of the document or attachment")


  public Float getSize() {
    return size;
  }

  public void setSize(Float size) {
    this.size = size;
  }

  public Attachment url(String url) {
    this.url = url;
    return this;
  }

   /**
   * URL of the attachment
   * @return url
  **/
  @ApiModelProperty(value = "URL of the attachment")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Attachment type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Indicates the type of attachment
   * @return type
  **/
  @ApiModelProperty(value = "Indicates the type of attachment")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Attachment schemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
    return this;
  }

   /**
   * Link to the schema describing the REST resource
   * @return schemaLocation
  **/
  @ApiModelProperty(value = "Link to the schema describing the REST resource")


  public String getSchemaLocation() {
    return schemaLocation;
  }

  public void setSchemaLocation(String schemaLocation) {
    this.schemaLocation = schemaLocation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attachment attachment = (Attachment) o;
    return Objects.equals(this.id, attachment.id) &&
        Objects.equals(this.href, attachment.href) &&
        Objects.equals(this.name, attachment.name) &&
        Objects.equals(this.description, attachment.description) &&
        Objects.equals(this.mimeType, attachment.mimeType) &&
        Objects.equals(this.sizeUnit, attachment.sizeUnit) &&
        Objects.equals(this.size, attachment.size) &&
        Objects.equals(this.url, attachment.url) &&
        Objects.equals(this.type, attachment.type) &&
        Objects.equals(this.schemaLocation, attachment.schemaLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, name, description, mimeType, sizeUnit, size, url, type, schemaLocation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attachment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    sizeUnit: ").append(toIndentedString(sizeUnit)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
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

