package dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})
@lombok.Data
public class CreateAlbumResponse {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @lombok.Data
    public class Data {

        @JsonProperty("id")
        private String id;
        @JsonProperty("deletehash")
        private String deletehash;

    }


}
//-----------------------------------Data-----------------------------------

