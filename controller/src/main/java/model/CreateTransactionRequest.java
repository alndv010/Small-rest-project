package model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CreateTransactionRequest   {
  
  private Double amount;
  private String fromAccountId;
  private String toAccountId;
  private String transactionId;

  public CreateTransactionRequest () {

  }

  public CreateTransactionRequest (Double amount, String fromAccountId, String toAccountId, String transactionId) {
    this.amount = amount;
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
    this.transactionId = transactionId;
  }

    
  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }
  public void setAmount(Double amount) {
    this.amount = amount;
  }

    
  @JsonProperty("fromAccountId")
  public String getFromAccountId() {
    return fromAccountId;
  }
  public void setFromAccountId(String fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

    
  @JsonProperty("toAccountId")
  public String getToAccountId() {
    return toAccountId;
  }
  public void setToAccountId(String toAccountId) {
    this.toAccountId = toAccountId;
  }

  @JsonProperty("transactionId")
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionRequest createTransactionRequest = (CreateTransactionRequest) o;
    return Objects.equals(amount, createTransactionRequest.amount) &&
        Objects.equals(fromAccountId, createTransactionRequest.fromAccountId) &&
        Objects.equals(toAccountId, createTransactionRequest.toAccountId) &&
        Objects.equals(transactionId, createTransactionRequest.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, fromAccountId, toAccountId, transactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransactionRequest {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    fromAccountId: ").append(toIndentedString(fromAccountId)).append("\n");
    sb.append("    toAccountId: ").append(toIndentedString(toAccountId)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
