package com.example.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CreateAccountRequest {
  
  private String accountId;
  private String description;
  private Double initialBalance = 0d;

  public CreateAccountRequest () {

  }

  public CreateAccountRequest(String accountId, String description, Double initialBalance) {
    this.accountId = accountId;
    this.description = description;
    this.initialBalance = initialBalance;
  }

    
  @JsonProperty("accountId")
  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

    
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

    
  @JsonProperty("initialBalance")
  public Double getInitialBalance() {
    return initialBalance;
  }
  public void setInitialBalance(Double initialBalance) {
    this.initialBalance = initialBalance;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAccountRequest createAccountRequest = (CreateAccountRequest) o;
    return Objects.equals(accountId, createAccountRequest.accountId) &&
        Objects.equals(description, createAccountRequest.description) &&
        Objects.equals(initialBalance, createAccountRequest.initialBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, description, initialBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateAccountRequest {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    initialBalance: ").append(toIndentedString(initialBalance)).append("\n");
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
