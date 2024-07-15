package com.example.birdstoreandroid.Model;

import java.util.List;

public class GetAllPhoigiongResponse {
    private String messageError;
    private int statusCode;
    private List<PhoiGiongData> data;

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<PhoiGiongData> getData() {
        return data;
    }

    public void setData(List<PhoiGiongData> data) {
        this.data = data;
    }

    public static class PhoiGiongData {
        private String id;
        private String bird_Shop_Male;
        private String bird_Shop_Female;
        private String ngayChoPhoi;
        private String ngayCoTrung;
        private String ngayTrungNo;
        private Integer soTrung;
        private Integer soTrungNo;
        private Integer soTrungHong;
        private Integer soChimGiao;
        private int phoiGiongStatus;
        private Double giaTien;
        private Double daThanhToan;
        private Double conLai;
        private BirdShopDetail bird_Shop_Male_Detail;
        private BirdShopDetail bird_Shop_Female_Detail;
        private String createdTime;
        private String lastUpdatedTime;
        private String deletedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBird_Shop_Male() {
            return bird_Shop_Male;
        }

        public void setBird_Shop_Male(String bird_Shop_Male) {
            this.bird_Shop_Male = bird_Shop_Male;
        }

        public String getBird_Shop_Female() {
            return bird_Shop_Female;
        }

        public void setBird_Shop_Female(String bird_Shop_Female) {
            this.bird_Shop_Female = bird_Shop_Female;
        }

        public String getNgayChoPhoi() {
            return ngayChoPhoi;
        }

        public void setNgayChoPhoi(String ngayChoPhoi) {
            this.ngayChoPhoi = ngayChoPhoi;
        }

        public String getNgayCoTrung() {
            return ngayCoTrung;
        }

        public void setNgayCoTrung(String ngayCoTrung) {
            this.ngayCoTrung = ngayCoTrung;
        }

        public String getNgayTrungNo() {
            return ngayTrungNo;
        }

        public void setNgayTrungNo(String ngayTrungNo) {
            this.ngayTrungNo = ngayTrungNo;
        }

        public Integer getSoTrung() {
            return soTrung;
        }

        public void setSoTrung(Integer soTrung) {
            this.soTrung = soTrung;
        }

        public Integer getSoTrungNo() {
            return soTrungNo;
        }

        public void setSoTrungNo(Integer soTrungNo) {
            this.soTrungNo = soTrungNo;
        }

        public Integer getSoTrungHong() {
            return soTrungHong;
        }

        public void setSoTrungHong(Integer soTrungHong) {
            this.soTrungHong = soTrungHong;
        }

        public Integer getSoChimGiao() {
            return soChimGiao;
        }

        public void setSoChimGiao(Integer soChimGiao) {
            this.soChimGiao = soChimGiao;
        }

        public int getPhoiGiongStatus() {
            return phoiGiongStatus;
        }

        public void setPhoiGiongStatus(int phoiGiongStatus) {
            this.phoiGiongStatus = phoiGiongStatus;
        }

        public Double getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(Double giaTien) {
            this.giaTien = giaTien;
        }

        public Double getDaThanhToan() {
            return daThanhToan;
        }

        public void setDaThanhToan(Double daThanhToan) {
            this.daThanhToan = daThanhToan;
        }

        public Double getConLai() {
            return conLai;
        }

        public void setConLai(Double conLai) {
            this.conLai = conLai;
        }

        public BirdShopDetail getBird_Shop_Male_Detail() {
            return bird_Shop_Male_Detail;
        }

        public void setBird_Shop_Male_Detail(BirdShopDetail bird_Shop_Male_Detail) {
            this.bird_Shop_Male_Detail = bird_Shop_Male_Detail;
        }

        public BirdShopDetail getBird_Shop_Female_Detail() {
            return bird_Shop_Female_Detail;
        }

        public void setBird_Shop_Female_Detail(BirdShopDetail bird_Shop_Female_Detail) {
            this.bird_Shop_Female_Detail = bird_Shop_Female_Detail;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(String lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public String getDeletedTime() {
            return deletedTime;
        }

        public void setDeletedTime(String deletedTime) {
            this.deletedTime = deletedTime;
        }
    }

    public static class BirdShopDetail {
        private String id;
        private String category_id;
        private String image;
        private int price;
        private String name;
        private boolean sex;
        private String description;
        private int statusProduct;
        private int discount;
        private String day_of_birth;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatusProduct() {
            return statusProduct;
        }

        public void setStatusProduct(int statusProduct) {
            this.statusProduct = statusProduct;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getDay_of_birth() {
            return day_of_birth;
        }

        public void setDay_of_birth(String day_of_birth) {
            this.day_of_birth = day_of_birth;
        }
    }
}
