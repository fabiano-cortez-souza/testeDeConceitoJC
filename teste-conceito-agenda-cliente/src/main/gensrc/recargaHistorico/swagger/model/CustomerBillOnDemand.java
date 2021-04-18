package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import recargaHistorico.swagger.model.BillRef;
import recargaHistorico.swagger.model.BillingAccountRef;
import recargaHistorico.swagger.model.RelatedPartyRef;
import recargaHistorico.swagger.model.StateValues;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * This resource is used to manage the creation request of a customer bill in real-time (on demand).
 */
@ApiModel(description = "This resource is used to manage the creation request of a customer bill in real-time (on demand).")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class CustomerBillOnDemand   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("state")
  private StateValues state = null;

  @JsonProperty("lastUpdate")
  private String lastUpdate = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("billingAccount")
  private BillingAccountRef billingAccount = null;

  @JsonProperty("relatedParty")
  private RelatedPartyRef relatedParty = null;

  @JsonProperty("customerBill")
  private BillRef customerBill = null;

  public CustomerBillOnDemand id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier of the customer bill on demand request given by the server
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of the customer bill on demand request given by the server")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CustomerBillOnDemand href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Reference of the customer bill on demand request
   * @return href
  **/
  @ApiModelProperty(value = "Reference of the customer bill on demand request")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public CustomerBillOnDemand name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Friendly name to identify the customer bill on demand request
   * @return name
  **/
  @ApiModelProperty(value = "Friendly name to identify the customer bill on demand request")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomerBillOnDemand description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Additional data describing the customer bill on demand request
   * @return description
  **/
  @ApiModelProperty(value = "Additional data describing the customer bill on demand request")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CustomerBillOnDemand state(StateValues state) {
    this.state = state;
    return this;
  }

   /**
   * State of the customer bill on demand request : rejected, inProgress, done or terminatedWithError
   * @return state
  **/
  @ApiModelProperty(value = "State of the customer bill on demand request : rejected, inProgress, done or terminatedWithError")

  @Valid

  public StateValues getState() {
    return state;
  }

  public void setState(StateValues state) {
    this.state = state;
  }

  public CustomerBillOnDemand lastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
    return this;
  }

   /**
   * The last date time when the customer bill on demand has been updated
   * @return lastUpdate
  **/
  @ApiModelProperty(value = "The last date time when the customer bill on demand has been updated")


  public String getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public CustomerBillOnDemand type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Indicates the (class) type of the customer bill on demand request
   * @return type
  **/
  @ApiModelProperty(value = "Indicates the (class) type of the customer bill on demand request")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CustomerBillOnDemand schemaLocation(String schemaLocation) {
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

  public CustomerBillOnDemand billingAccount(BillingAccountRef billingAccount) {
    this.billingAccount = billingAccount;
    return this;
  }

   /**
   * Reference of the billing account that produces the customer bill on demand
   * @return billingAccount
  **/
  @ApiModelProperty(value = "Reference of the billing account that produces the customer bill on demand")

  @Valid

  public BillingAccountRef getBillingAccount() {
    return billingAccount;
  }

  public void setBillingAccount(BillingAccountRef billingAccount) {
    this.billingAccount = billingAccount;
  }

  public CustomerBillOnDemand relatedParty(RelatedPartyRef relatedParty) {
    this.relatedParty = relatedParty;
    return this;
  }

   /**
   * A related party for which the customer bill on demand is requested
   * @return relatedParty
  **/
  @ApiModelProperty(value = "A related party for which the customer bill on demand is requested")

  @Valid

  public RelatedPartyRef getRelatedParty() {
    return relatedParty;
  }

  public void setRelatedParty(RelatedPartyRef relatedParty) {
    this.relatedParty = relatedParty;
  }

  public CustomerBillOnDemand customerBill(BillRef customerBill) {
    this.customerBill = customerBill;
    return this;
  }

   /**
   * Reference of the bill generated by the customer bill on demand request
   * @return customerBill
  **/
  @ApiModelProperty(value = "Reference of the bill generated by the customer bill on demand request")

  @Valid

  public BillRef getCustomerBill() {
    return customerBill;
  }

  public void setCustomerBill(BillRef customerBill) {
    this.customerBill = customerBill;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerBillOnDemand customerBillOnDemand = (CustomerBillOnDemand) o;
    return Objects.equals(this.id, customerBillOnDemand.id) &&
        Objects.equals(this.href, customerBillOnDemand.href) &&
        Objects.equals(this.name, customerBillOnDemand.name) &&
        Objects.equals(this.description, customerBillOnDemand.description) &&
        Objects.equals(this.state, customerBillOnDemand.state) &&
        Objects.equals(this.lastUpdate, customerBillOnDemand.lastUpdate) &&
        Objects.equals(this.type, customerBillOnDemand.type) &&
        Objects.equals(this.schemaLocation, customerBillOnDemand.schemaLocation) &&
        Objects.equals(this.billingAccount, customerBillOnDemand.billingAccount) &&
        Objects.equals(this.relatedParty, customerBillOnDemand.relatedParty) &&
        Objects.equals(this.customerBill, customerBillOnDemand.customerBill);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, name, description, state, lastUpdate, type, schemaLocation, billingAccount, relatedParty, customerBill);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerBillOnDemand {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
    sb.append("    relatedParty: ").append(toIndentedString(relatedParty)).append("\n");
    sb.append("    customerBill: ").append(toIndentedString(customerBill)).append("\n");
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

