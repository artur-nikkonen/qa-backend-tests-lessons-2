package dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "success",
        "status"
})
@Data
public class AccountInfoResponse {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;

    //-----------------------------------Data-----------------------------------
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @lombok.Data
    public class Data {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("url")
        private String url;
        @JsonProperty("bio")
        private Object bio;
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("avatar_name")
        private String avatarName;
        @JsonProperty("cover")
        private String cover;
        @JsonProperty("cover_name")
        private String coverName;
        @JsonProperty("reputation")
        private Integer reputation;
        @JsonProperty("reputation_name")
        private String reputationName;
        @JsonProperty("created")
        private Integer created;
        @JsonProperty("pro_expiration")
        private Boolean proExpiration;
        @JsonProperty("user_follow")
        private UserFollow userFollow;
        @JsonProperty("is_blocked")
        private Boolean isBlocked;

        //-----------------------------------UserFollow-----------------------------------
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @lombok.Data
        public class UserFollow {
            @JsonProperty("status")
            private Boolean status;
        }
    }

}