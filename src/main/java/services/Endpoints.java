package services;

public final class Endpoints {
    public static final String getAccount = "/account/{username}";

    public static final String postAlbum = "/album";
    public static final String getAlbum = "/album/{albumId}";
    public static final String deleteAlbum = "/album/{albumId}";
    public static final String addAlbum = "/album/{albumId}/add";

    public static final String postUpdateImage = "/image/{imageHash}";
    public static final String postUploadImage = "/image";
    public static final String deleteImage = "/image/{imageHash}";
    public static final String postImageFavorite = "/image/{imageId}/favorite";

}
