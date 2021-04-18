package recargaHistorico.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import recargaHistorico.swagger.model.AppliedPayment;
import recargaHistorico.swagger.model.Attachment;
import recargaHistorico.swagger.model.BillingAccountRef;
import recargaHistorico.swagger.model.FinancialAccountRef;
import recargaHistorico.swagger.model.Money;
import recargaHistorico.swagger.model.PaymentMethodRef;
import recargaHistorico.swagger.model.RelatedPartyRef;
import recargaHistorico.swagger.model.StateValue;
import recargaHistorico.swagger.model.TaxItem;
import recargaHistorico.swagger.model.TimePeriod;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The billing account receives all charges (recurring, one time and usage) of the offers and products assigned to it during order process. Periodically according to billing cycle specifications attached to the billing account or as a result of an event, a customer bill (aka invoice) is produced. This customer bill concerns different related parties which play a role on it : for example, a customer bill is produced by an operator, is sent to a bill receiver and has to be paid by a payer. A payment method could be assigned to the customer bill to build the call of payment. Lettering process enables to assign automatically or manually incoming amount from payments to customer bills (payment items). A tax item is created for each tax rate used in the customer bill. The financial account represents a financial entity which records all customer’s accounting events : payment amount are recorded as credit and invoices amount are recorded as debit. It gives the customer overall balance (account balance). The customer bill is linked to one or more documents that can be downloaded via a provided url.
 */
@ApiModel(description = "The billing account receives all charges (recurring, one time and usage) of the offers and products assigned to it during order process. Periodically according to billing cycle specifications attached to the billing account or as a result of an event, a customer bill (aka invoice) is produced. This customer bill concerns different related parties which play a role on it : for example, a customer bill is produced by an operator, is sent to a bill receiver and has to be paid by a payer. A payment method could be assigned to the customer bill to build the call of payment. Lettering process enables to assign automatically or manually incoming amount from payments to customer bills (payment items). A tax item is created for each tax rate used in the customer bill. The financial account represents a financial entity which records all customer’s accounting events : payment amount are recorded as credit and invoices amount are recorded as debit. It gives the customer overall balance (account balance). The customer bill is linked to one or more documents that can be downloaded via a provided url.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-25T17:12:43.631-02:00")

public class CustomerBill   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("billNo")
  private String billNo = null;

  @JsonProperty("runType")
  private String runType = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("state")
  private StateValue state = null;

  @JsonProperty("lastUpdate")
  private OffsetDateTime lastUpdate = null;

  @JsonProperty("billDate")
  private OffsetDateTime billDate = null;

  @JsonProperty("nextBillDate")
  private OffsetDateTime nextBillDate = null;

  @JsonProperty("billingPeriod")
  private TimePeriod billingPeriod = null;

  @JsonProperty("amountDue")
  private Money amountDue = null;

  @JsonProperty("paymentDueDate")
  private OffsetDateTime paymentDueDate = null;

  @JsonProperty("remainingAmount")
  private Money remainingAmount = null;

  @JsonProperty("taxExcludedAmount")
  private Money taxExcludedAmount = null;

  @JsonProperty("taxIncludedAmount")
  private Money taxIncludedAmount = null;

  @JsonProperty("@baseType")
  private String baseType = null;

  @JsonProperty("@type")
  private String type = null;

  @JsonProperty("@schemaLocation")
  private String schemaLocation = null;

  @JsonProperty("billDocument")
  private List<Attachment> billDocument = null;

  @JsonProperty("appliedPayment")
  private List<AppliedPayment> appliedPayment = null;

  @JsonProperty("billingAccount")
  private BillingAccountRef billingAccount = null;

  @JsonProperty("taxItem")
  private List<TaxItem> taxItem = null;

  @JsonProperty("paymentMethod")
  private PaymentMethodRef paymentMethod = null;

  @JsonProperty("relatedParty")
  private List<RelatedPartyRef> relatedParty = null;

  @JsonProperty("financialAccount")
  private FinancialAccountRef financialAccount = null;

  public CustomerBill id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Unique identifier of he bill
   * @return id
  **/
  @ApiModelProperty(value = "Unique identifier of he bill")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CustomerBill href(String href) {
    this.href = href;
    return this;
  }

   /**
   * Bill unique reference
   * @return href
  **/
  @ApiModelProperty(value = "Bill unique reference")


  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public CustomerBill billNo(String billNo) {
    this.billNo = billNo;
    return this;
  }

   /**
   * Bill reference known by the customer or the party and displayed on the bill. Could be different from the id
   * @return billNo
  **/
  @ApiModelProperty(value = "Bill reference known by the customer or the party and displayed on the bill. Could be different from the id")


  public String getBillNo() {
    return billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public CustomerBill runType(String runType) {
    this.runType = runType;
    return this;
  }

   /**
   * onCycle (a bill can be created as a result of a cycle run) or offCycle (a bill can be created as a result of other events such as customer request or account close)
   * @return runType
  **/
  @ApiModelProperty(value = "onCycle (a bill can be created as a result of a cycle run) or offCycle (a bill can be created as a result of other events such as customer request or account close)")


  public String getRunType() {
    return runType;
  }

  public void setRunType(String runType) {
    this.runType = runType;
  }

  public CustomerBill category(String category) {
    this.category = category;
    return this;
  }

   /**
   * Category of the bill produced : normal, duplicate, interim, last, trial customer or credit note for example
   * @return category
  **/
  @ApiModelProperty(value = "Category of the bill produced : normal, duplicate, interim, last, trial customer or credit note for example")


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public CustomerBill state(StateValue state) {
    this.state = state;
    return this;
  }

   /**
   * 
   * @return state
  **/
  @ApiModelProperty(value = "")

  @Valid

  public StateValue getState() {
    return state;
  }

  public void setState(StateValue state) {
    this.state = state;
  }

  public CustomerBill lastUpdate(OffsetDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
    return this;
  }

   /**
   * Date of bill last update
   * @return lastUpdate
  **/
  @ApiModelProperty(value = "Date of bill last update")

  @Valid

  public OffsetDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(OffsetDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public CustomerBill billDate(OffsetDateTime billDate) {
    this.billDate = billDate;
    return this;
  }

   /**
   * Bill date
   * @return billDate
  **/
  @ApiModelProperty(value = "Bill date")

  @Valid

  public OffsetDateTime getBillDate() {
    return billDate;
  }

  public void setBillDate(OffsetDateTime billDate) {
    this.billDate = billDate;
  }

  public CustomerBill nextBillDate(OffsetDateTime nextBillDate) {
    this.nextBillDate = nextBillDate;
    return this;
  }

   /**
   * ). Approximate date of  the next bill production given for information (only used for onCycle bill)
   * @return nextBillDate
  **/
  @ApiModelProperty(value = "). Approximate date of  the next bill production given for information (only used for onCycle bill)")

  @Valid

  public OffsetDateTime getNextBillDate() {
    return nextBillDate;
  }

  public void setNextBillDate(OffsetDateTime nextBillDate) {
    this.nextBillDate = nextBillDate;
  }

  public CustomerBill billingPeriod(TimePeriod billingPeriod) {
    this.billingPeriod = billingPeriod;
    return this;
  }

   /**
   * Billing period of the bill (used for onCycle bill only)
   * @return billingPeriod
  **/
  @ApiModelProperty(value = "Billing period of the bill (used for onCycle bill only)")

  @Valid

  public TimePeriod getBillingPeriod() {
    return billingPeriod;
  }

  public void setBillingPeriod(TimePeriod billingPeriod) {
    this.billingPeriod = billingPeriod;
  }

  public CustomerBill amountDue(Money amountDue) {
    this.amountDue = amountDue;
    return this;
  }

   /**
   * Amount due for this bill expressed in the given currency
   * @return amountDue
  **/
  @ApiModelProperty(value = "Amount due for this bill expressed in the given currency")

  @Valid

  public Money getAmountDue() {
    return amountDue;
  }

  public void setAmountDue(Money amountDue) {
    this.amountDue = amountDue;
  }

  public CustomerBill paymentDueDate(OffsetDateTime paymentDueDate) {
    this.paymentDueDate = paymentDueDate;
    return this;
  }

   /**
   * Date at which the amount due should have been paid
   * @return paymentDueDate
  **/
  @ApiModelProperty(value = "Date at which the amount due should have been paid")

  @Valid

  public OffsetDateTime getPaymentDueDate() {
    return paymentDueDate;
  }

  public void setPaymentDueDate(OffsetDateTime paymentDueDate) {
    this.paymentDueDate = paymentDueDate;
  }

  public CustomerBill remainingAmount(Money remainingAmount) {
    this.remainingAmount = remainingAmount;
    return this;
  }

   /**
   * Remaining amount to be paid for this bill expressed in the given currency
   * @return remainingAmount
  **/
  @ApiModelProperty(value = "Remaining amount to be paid for this bill expressed in the given currency")

  @Valid

  public Money getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(Money remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public CustomerBill taxExcludedAmount(Money taxExcludedAmount) {
    this.taxExcludedAmount = taxExcludedAmount;
    return this;
  }

   /**
   * Total tax excluded amount expressed in the given currency
   * @return taxExcludedAmount
  **/
  @ApiModelProperty(value = "Total tax excluded amount expressed in the given currency")

  @Valid

  public Money getTaxExcludedAmount() {
    return taxExcludedAmount;
  }

  public void setTaxExcludedAmount(Money taxExcludedAmount) {
    this.taxExcludedAmount = taxExcludedAmount;
  }

  public CustomerBill taxIncludedAmount(Money taxIncludedAmount) {
    this.taxIncludedAmount = taxIncludedAmount;
    return this;
  }

   /**
   * Total tax included amount expressed in the given
   * @return taxIncludedAmount
  **/
  @ApiModelProperty(value = "Total tax included amount expressed in the given")

  @Valid

  public Money getTaxIncludedAmount() {
    return taxIncludedAmount;
  }

  public void setTaxIncludedAmount(Money taxIncludedAmount) {
    this.taxIncludedAmount = taxIncludedAmount;
  }

  public CustomerBill baseType(String baseType) {
    this.baseType = baseType;
    return this;
  }

   /**
   * Indicates the base (class) type of the customer bill
   * @return baseType
  **/
  @ApiModelProperty(value = "Indicates the base (class) type of the customer bill")


  public String getBaseType() {
    return baseType;
  }

  public void setBaseType(String baseType) {
    this.baseType = baseType;
  }

  public CustomerBill type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Indicates the (class) type of the customer bill
   * @return type
  **/
  @ApiModelProperty(value = "Indicates the (class) type of the customer bill")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CustomerBill schemaLocation(String schemaLocation) {
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

  public CustomerBill billDocument(List<Attachment> billDocument) {
    this.billDocument = billDocument;
    return this;
  }

  public CustomerBill addBillDocumentItem(Attachment billDocumentItem) {
    if (this.billDocument == null) {
      this.billDocument = new ArrayList<Attachment>();
    }
    this.billDocument.add(billDocumentItem);
    return this;
  }

   /**
   * A list of documents accompanying an entity
   * @return billDocument
  **/
  @ApiModelProperty(value = "A list of documents accompanying an entity")

  @Valid

  public List<Attachment> getBillDocument() {
    return billDocument;
  }

  public void setBillDocument(List<Attachment> billDocument) {
    this.billDocument = billDocument;
  }

  public CustomerBill appliedPayment(List<AppliedPayment> appliedPayment) {
    this.appliedPayment = appliedPayment;
    return this;
  }

  public CustomerBill addAppliedPaymentItem(AppliedPayment appliedPaymentItem) {
    if (this.appliedPayment == null) {
      this.appliedPayment = new ArrayList<AppliedPayment>();
    }
    this.appliedPayment.add(appliedPaymentItem);
    return this;
  }

   /**
   * A list of payment items already assigned to this bill
   * @return appliedPayment
  **/
  @ApiModelProperty(value = "A list of payment items already assigned to this bill")

  @Valid

  public List<AppliedPayment> getAppliedPayment() {
    return appliedPayment;
  }

  public void setAppliedPayment(List<AppliedPayment> appliedPayment) {
    this.appliedPayment = appliedPayment;
  }

  public CustomerBill billingAccount(BillingAccountRef billingAccount) {
    this.billingAccount = billingAccount;
    return this;
  }

   /**
   * Reference of the BillingAccount that produced this bill
   * @return billingAccount
  **/
  @ApiModelProperty(value = "Reference of the BillingAccount that produced this bill")

  @Valid

  public BillingAccountRef getBillingAccount() {
    return billingAccount;
  }

  public void setBillingAccount(BillingAccountRef billingAccount) {
    this.billingAccount = billingAccount;
  }

  public CustomerBill taxItem(List<TaxItem> taxItem) {
    this.taxItem = taxItem;
    return this;
  }

  public CustomerBill addTaxItemItem(TaxItem taxItemItem) {
    if (this.taxItem == null) {
      this.taxItem = new ArrayList<TaxItem>();
    }
    this.taxItem.add(taxItemItem);
    return this;
  }

   /**
   * A list of  tax items created for each tax rate and tax type used in the bill. The tax item summarize the total of the various tax types.
   * @return taxItem
  **/
  @ApiModelProperty(value = "A list of  tax items created for each tax rate and tax type used in the bill. The tax item summarize the total of the various tax types.")

  @Valid

  public List<TaxItem> getTaxItem() {
    return taxItem;
  }

  public void setTaxItem(List<TaxItem> taxItem) {
    this.taxItem = taxItem;
  }

  public CustomerBill paymentMethod(PaymentMethodRef paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

   /**
   * A payment method reference. A payment method defines a specific mean of payment (e.g direct debit) used to build the call of payment
   * @return paymentMethod
  **/
  @ApiModelProperty(value = "A payment method reference. A payment method defines a specific mean of payment (e.g direct debit) used to build the call of payment")

  @Valid

  public PaymentMethodRef getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethodRef paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public CustomerBill relatedParty(List<RelatedPartyRef> relatedParty) {
    this.relatedParty = relatedParty;
    return this;
  }

  public CustomerBill addRelatedPartyItem(RelatedPartyRef relatedPartyItem) {
    if (this.relatedParty == null) {
      this.relatedParty = new ArrayList<RelatedPartyRef>();
    }
    this.relatedParty.add(relatedPartyItem);
    return this;
  }

   /**
   * A list of related party references. A related party defines party or party role linked to the bill
   * @return relatedParty
  **/
  @ApiModelProperty(value = "A list of related party references. A related party defines party or party role linked to the bill")

  @Valid

  public List<RelatedPartyRef> getRelatedParty() {
    return relatedParty;
  }

  public void setRelatedParty(List<RelatedPartyRef> relatedParty) {
    this.relatedParty = relatedParty;
  }

  public CustomerBill financialAccount(FinancialAccountRef financialAccount) {
    this.financialAccount = financialAccount;
    return this;
  }

   /**
   * A financial account reference. An account of money owed by a party to another entity in exchange for goods or services that have been delivered or used. An account receivable aggregates the amounts of one or more billing accounts owned by a given party.
   * @return financialAccount
  **/
  @ApiModelProperty(value = "A financial account reference. An account of money owed by a party to another entity in exchange for goods or services that have been delivered or used. An account receivable aggregates the amounts of one or more billing accounts owned by a given party.")

  @Valid

  public FinancialAccountRef getFinancialAccount() {
    return financialAccount;
  }

  public void setFinancialAccount(FinancialAccountRef financialAccount) {
    this.financialAccount = financialAccount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerBill customerBill = (CustomerBill) o;
    return Objects.equals(this.id, customerBill.id) &&
        Objects.equals(this.href, customerBill.href) &&
        Objects.equals(this.billNo, customerBill.billNo) &&
        Objects.equals(this.runType, customerBill.runType) &&
        Objects.equals(this.category, customerBill.category) &&
        Objects.equals(this.state, customerBill.state) &&
        Objects.equals(this.lastUpdate, customerBill.lastUpdate) &&
        Objects.equals(this.billDate, customerBill.billDate) &&
        Objects.equals(this.nextBillDate, customerBill.nextBillDate) &&
        Objects.equals(this.billingPeriod, customerBill.billingPeriod) &&
        Objects.equals(this.amountDue, customerBill.amountDue) &&
        Objects.equals(this.paymentDueDate, customerBill.paymentDueDate) &&
        Objects.equals(this.remainingAmount, customerBill.remainingAmount) &&
        Objects.equals(this.taxExcludedAmount, customerBill.taxExcludedAmount) &&
        Objects.equals(this.taxIncludedAmount, customerBill.taxIncludedAmount) &&
        Objects.equals(this.baseType, customerBill.baseType) &&
        Objects.equals(this.type, customerBill.type) &&
        Objects.equals(this.schemaLocation, customerBill.schemaLocation) &&
        Objects.equals(this.billDocument, customerBill.billDocument) &&
        Objects.equals(this.appliedPayment, customerBill.appliedPayment) &&
        Objects.equals(this.billingAccount, customerBill.billingAccount) &&
        Objects.equals(this.taxItem, customerBill.taxItem) &&
        Objects.equals(this.paymentMethod, customerBill.paymentMethod) &&
        Objects.equals(this.relatedParty, customerBill.relatedParty) &&
        Objects.equals(this.financialAccount, customerBill.financialAccount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, billNo, runType, category, state, lastUpdate, billDate, nextBillDate, billingPeriod, amountDue, paymentDueDate, remainingAmount, taxExcludedAmount, taxIncludedAmount, baseType, type, schemaLocation, billDocument, appliedPayment, billingAccount, taxItem, paymentMethod, relatedParty, financialAccount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerBill {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    billNo: ").append(toIndentedString(billNo)).append("\n");
    sb.append("    runType: ").append(toIndentedString(runType)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
    sb.append("    billDate: ").append(toIndentedString(billDate)).append("\n");
    sb.append("    nextBillDate: ").append(toIndentedString(nextBillDate)).append("\n");
    sb.append("    billingPeriod: ").append(toIndentedString(billingPeriod)).append("\n");
    sb.append("    amountDue: ").append(toIndentedString(amountDue)).append("\n");
    sb.append("    paymentDueDate: ").append(toIndentedString(paymentDueDate)).append("\n");
    sb.append("    remainingAmount: ").append(toIndentedString(remainingAmount)).append("\n");
    sb.append("    taxExcludedAmount: ").append(toIndentedString(taxExcludedAmount)).append("\n");
    sb.append("    taxIncludedAmount: ").append(toIndentedString(taxIncludedAmount)).append("\n");
    sb.append("    baseType: ").append(toIndentedString(baseType)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    schemaLocation: ").append(toIndentedString(schemaLocation)).append("\n");
    sb.append("    billDocument: ").append(toIndentedString(billDocument)).append("\n");
    sb.append("    appliedPayment: ").append(toIndentedString(appliedPayment)).append("\n");
    sb.append("    billingAccount: ").append(toIndentedString(billingAccount)).append("\n");
    sb.append("    taxItem: ").append(toIndentedString(taxItem)).append("\n");
    sb.append("    paymentMethod: ").append(toIndentedString(paymentMethod)).append("\n");
    sb.append("    relatedParty: ").append(toIndentedString(relatedParty)).append("\n");
    sb.append("    financialAccount: ").append(toIndentedString(financialAccount)).append("\n");
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

