package com.ipq.ipq;


public class Image {


    private int ImgID;
    private String ImgName;
    private double ImgSize;
    private String ImgFile;

    public Image(int imgID, String imgName, double imgSize, String imgFile) {
        ImgID = imgID;
        ImgName = imgName;
        ImgSize = imgSize;
        ImgFile = imgFile;
    }

    public int getImgID() {
        return ImgID;
    }

    public void setImgID(int imgID) {
        ImgID = imgID;
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public double getImgSize() {
        return ImgSize;
    }

    public void setImgSize(double imgSize) {
        ImgSize = imgSize;
    }

    public String getImgFile() {
        return ImgFile;
    }

    public void setImgFile(String imgFile) {
        ImgFile = imgFile;
    }
}
