package com.fanleiyi.tarena.cartoonlivehybrid.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tarena on 2017/7/25.
 */

public class ImageInfoEntity implements Serializable{

    /**
     * status : 0
     * data : [{"imageId":1,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0001.jpg"},{"imageId":2,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0002.jpg"},{"imageId":3,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0003.jpg"},{"imageId":4,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0004.JPG"},{"imageId":5,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0005.jpg"},{"imageId":6,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0006.jpg"},{"imageId":7,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0007.jpg"},{"imageId":8,"imageSrc":"/cartoonServer/upLoadComicData/30/1/0008.JPG"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * imageId : 1
         * imageSrc : /cartoonServer/upLoadComicData/30/1/0001.jpg
         */

        private int imageId;
        private String imageSrc;

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }
    }
}
