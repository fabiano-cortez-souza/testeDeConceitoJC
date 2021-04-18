package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import recargaHistorico.swagger.model.Money;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A payment reference
 */
@ApiModel(description = "A payment reference")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class PaymentRef   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("paymentDate")
  private OffsetDateTime paymentDate = null;

  @JsonProperty("amount")
  private Money amount = null;

  @JsonProperty("@type")
  private String type = null;

  public PaymentRef id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier of the payment
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the payment")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PaymentRef href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Reference of the payment
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the payment")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public PaymentRef paymentDate(OffsetDateTime paymentDate) {
    this.paymentDate = paymentDate;
    return this;
  }

   /**
   * Payment date
   * @return paymentDate
  **/
  @ApiModelProperty(value = "Payment date")

  @Valid

  public OffsetDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(OffsetDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }

  public PaymentRef amount(Money amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Payment amount done
   * @return amount
  **/
  @ApiModelProperty(value = "Payment amount done")

  @Valid

  public Money getAmount() {
    return amount;
  }

  public void setAmount(Money amount) {
    this.amount = amount;
  }

  public PaymentRef type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Indicates the type of payment
   * @return type
  **/
  @ApiModelProperty(value = "Indicates the type of payment")


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
    PaymentRef paymentRef = (PaymentRef) o;
    return Objects.equals(this.id, paymentRef.id) &&
        Objects.equals(this.href, paymentRef.href) &&
        Objects.equals(this.paymentDate, paymentRef.paymentDate) &&
        Objects.equals(this.amount, paymentRef.amount) &&
        Objects.equals(this.type, paymentRef.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, paymentDate, amount, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentRef {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    paymentDate: ").append(toIndentedString(paymentDate)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

