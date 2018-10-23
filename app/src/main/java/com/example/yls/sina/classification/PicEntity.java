package com.example.yls.sina.classification;

public class PicEntity {
    private String title;
    private int imageId;
    private String  textId;

    public PicEntity() {
        super();
    }

    public PicEntity(String title, int imageId,String  textId) {
        super();
        this.title = title;
        this.imageId = imageId;
        this.textId = textId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }
}
