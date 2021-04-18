package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import recargaHistorico.swagger.model.Money;
import recargaHistorico.swagger.model.PaymentRef;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The applied payment is the result of lettering process. It enables to assign automatically or manually part of incoming payment amount to a bill.
 */
@ApiModel(description = "The applied payment is the result of lettering process. It enables to assign automatically or manually part of incoming payment amount to a bill.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class AppliedPayment   {
  @JsonProperty("appliedAmount")
  private Money appliedAmount = null;

  @JsonProperty("payment")
  private PaymentRef payment = null;

  public AppliedPayment appliedAmount(Money appliedAmount) {
    this.appliedAmount = appliedAmount;
    return this;
  }

   /**
   * Part of a payment amount lettered to the bill
   * @return appliedAmount
  **/
  @ApiModelProperty(value = "Part of a payment amount lettered to the bill")

  @Valid

  public Money getAppliedAmount() {
    return appliedAmount;
  }

  public void setAppliedAmount(Money appliedAmount) {
    this.appliedAmount = appliedAmount;
  }

  public AppliedPayment payment(PaymentRef payment) {
    this.payment = payment;
    return this;
  }

   /**
   * A payment reference
   * @return payment
  **/
  @ApiModelProperty(value = "A payment reference")

  @Valid

  public PaymentRef getPayment() {
    return payment;
  }

  public void setPayment(PaymentRef payment) {
    this.payment = payment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppliedPayment appliedPayment = (AppliedPayment) o;
    return Objects.equals(this.appliedAmount, appliedPayment.appliedAmount) &&
        Objects.equals(this.payment, appliedPayment.payment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appliedAmount, payment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppliedPayment {\n");
    
    sb.append("    appliedAmount: ").append(toIndentedString(appliedAmount)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
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

