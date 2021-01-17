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
public class GetAlbumResponse {
    @JsonProperty("data")
    private Data data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;

    //-----------------------------------com.example.Data.java-----------------------------------
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @lombok.Data
    public class Data {

        @JsonProperty("id")
        private String id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;
        @JsonProperty("datetime")
        private Integer datetime;
        @JsonProperty("cover")
        private String cover;
        @JsonProperty("cover_edited")
        private Object coverEdited;
        @JsonProperty("cover_width")
        private Integer coverWidth;
        @JsonProperty("cover_height")
        private Integer coverHeight;
        @JsonProperty("account_url")
        private String accountUrl;
        @JsonProperty("account_id")
        private Integer accountId;
        @JsonProperty("privacy")
        private String privacy;
        @JsonProperty("layout")
        private String layout;
        @JsonProperty("views")
        private Integer views;
        @JsonProperty("link")
        private String link;
        @JsonProperty("favorite")
        private Boolean favorite;
        @JsonProperty("nsfw")
        private Boolean nsfw;
        @JsonProperty("section")
        private Object section;
        @JsonProperty("images_count")
        private Integer imagesCount;
        @JsonProperty("in_gallery")
        private Boolean inGallery;
        @JsonProperty("is_ad")
        private Boolean isAd;
        @JsonProperty("include_album_ads")
        private Boolean includeAlbumAds;
        @JsonProperty("is_album")
        private Boolean isAlbum;
        @JsonProperty("deletehash")
        private String deletehash;
        @JsonProperty("images")
        private List<ImageDataResponsePart> images = new ArrayList<ImageDataResponsePart>();
        @JsonProperty("ad_config")
        private AdConfig adConfig;

        //-----------------------------------AdConfig-----------------------------------
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @lombok.Data
        public class AdConfig {

            @JsonProperty("safeFlags")
            private List<String> safeFlags = new ArrayList<String>();
            @JsonProperty("highRiskFlags")
            private List<Object> highRiskFlags = new ArrayList<Object>();
            @JsonProperty("unsafeFlags")
            private List<String> unsafeFlags = new ArrayList<String>();
            @JsonProperty("wallUnsafeFlags")
            private List<Object> wallUnsafeFlags = new ArrayList<Object>();
            @JsonProperty("showsAds")
            private Boolean showsAds;

        }
    }
}