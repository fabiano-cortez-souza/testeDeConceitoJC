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
 * A tax item is created for each tax rate and tax type used in the bill. The tax item specifies the applied tax rate and the total resulting amount.
 */
@ApiModel(description = "A tax item is created for each tax rate and tax type used in the bill. The tax item specifies the applied tax rate and the total resulting amount.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class TaxItem   {
  @JsonProperty("taxCategory")
  private String taxCategory = null;

  @JsonProperty("taxRate")
  private Float taxRate = null;

  @JsonProperty("taxAmount")
  private Money taxAmount = null;

  public TaxItem taxCategory(String taxCategory) {
    this.taxCategory = taxCategory;
    return this;
  }

   /**
   * A tax category
   * @return taxCategory
  **/
  @ApiModelProperty(value = "A tax category")


  public String getTaxCategory() {
    return taxCategory;
  }

  public void setTaxCategory(String taxCategory) {
    this.taxCategory = taxCategory;
  }

  public TaxItem taxRate(Float taxRate) {
    this.taxRate = taxRate;
    return this;
  }

   /**
   * Applied rate of the tax
   * @return taxRate
  **/
  @ApiModelProperty(value = "Applied rate of the tax")


  public Float getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(Float taxRate) {
    this.taxRate = taxRate;
  }

  public TaxItem taxAmount(Money taxAmount) {
    this.taxAmount = taxAmount;
    return this;
  }

   /**
   * Amount of tax expressed in the given currency
   * @return taxAmount
  **/
  @ApiModelProperty(value = "Amount of tax expressed in the given currency")

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
    TaxItem taxItem = (TaxItem) o;
    return Objects.equals(this.taxCategory, taxItem.taxCategory) &&
        Objects.equals(this.taxRate, taxItem.taxRate) &&
        Objects.equals(this.taxAmount, taxItem.taxAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxCategory, taxRate, taxAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaxItem {\n");
    
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

