package dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})
@lombok.Data
public class GetImageInfoResponse {

    @JsonProperty("data")
    private ImageDataResponsePart data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;
}


