package model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class CreateTransferResponse   {
  
  private String transferId;

  public CreateTransferResponse () {

  }

  public CreateTransferResponse (String transferId) {
    this.transferId = transferId;
  }

    
  @JsonProperty("transferId")
  public String getTransferId() {
    return transferId;
  }
  public void setTransferId(String transferId) {
    this.transferId = transferId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransferResponse createTransferResponse = (CreateTransferResponse) o;
    return Objects.equals(transferId, createTransferResponse.transferId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transferId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransferResponse {\n");
    
    sb.append("    transferId: ").append(toIndentedString(transferId)).append("\n");
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
