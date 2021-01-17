package dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})
@Data
public class FavoriteInfoResponse {

    @JsonProperty("data")
    private String data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;
}

