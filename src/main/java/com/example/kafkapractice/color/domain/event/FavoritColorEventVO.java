package com.example.kafkapractice.color.domain.event;

public class FavoritColorEventVO {


    private String userName;
    private String colorName;
    private String timeStamp;
    private String userAgent;

    public FavoritColorEventVO(String userName, String colorName, String timeStamp, String userAgent) {
        this.userName = userName;
        this.colorName = colorName;
        this.timeStamp = timeStamp;
        this.userAgent = userAgent;
    }

    protected FavoritColorEventVO() {
    }


}
