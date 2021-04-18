package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import recargaHistorico.swagger.model.Money;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The applied billing tax rate represents taxes applied billing rate it refers to. It is calculated during the billing process.
 */
@ApiModel(description = "The applied billing tax rate represents taxes applied billing rate it refers to. It is calculated during the billing process.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class AppliedBillingTaxRate   {
  @JsonProperty("taxCategory")
  private String taxCategory = null;

  @JsonProperty("taxRate")
  private Float taxRate = null;

  @JsonProperty("taxAmount")
  private Money taxAmount = null;

  public AppliedBillingTaxRate taxCategory(String taxCategory) {
    this.taxCategory = taxCategory;
    return this;
  }

   /**
   * A categorization of the tax rate
   * @return taxCategory
  **/
  @ApiModelProperty(value = "A categorization of the tax rate")


  public String getTaxCategory() {
    return taxCategory;
  }

  public void setTaxCategory(String taxCategory) {
    this.taxCategory = taxCategory;
  }

  public AppliedBillingTaxRate taxRate(Float taxRate) {
    this.taxRate = taxRate;
    return this;
  }

   /**
   * Applied rate
   * @return taxRate
  **/
  @ApiModelProperty(value = "Applied rate")


  public Float getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(Float taxRate) {
    this.taxRate = taxRate;
  }

  public AppliedBillingTaxRate taxAmount(Money taxAmount) {
    this.taxAmount = taxAmount;
    return this;
  }

   /**
   * Tax amount expressed in the given currency
   * @return taxAmount
  **/
  @ApiModelProperty(required = true, value = "Tax amount expressed in the given currency")
  @NotNull

  @Valid

  public Money getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(Money taxAmount) {
    this.taxAmount = taxAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppliedBillingTaxRate appliedBillingTaxRate = (AppliedBillingTaxRate) o;
    return Objects.equals(this.taxCategory, appliedBillingTaxRate.taxCategory) &&
        Objects.equals(this.taxRate, appliedBillingTaxRate.taxRate) &&
        Objects.equals(this.taxAmount, appliedBillingTaxRate.taxAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxCategory, taxRate, taxAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppliedBillingTaxRate {\n");
    
    sb.append("    taxCategory: ").append(toIndentedString(taxCategory)).append("\n");
    sb.append("    taxRate: ").append(toIndentedString(taxRate)).append("\n");
    sb.append("    taxAmount: ").append(toIndentedString(taxAmount)).append("\n");
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

