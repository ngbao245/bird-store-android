package com.example.birdstoreandroid.Model;

public class CreatePhoiGiongResponse {
    private String messageError;
    private int statusCode;
    private PhoiGiongData data;

    public static class PhoiGiongData {
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
        private Boolean daThanhToan;
        private Double conLai;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private Object deletedTime;

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

        public Boolean getDaThanhToan() {
            return daThanhToan;
        }

        public void setDaThanhToan(Boolean daThanhToan) {
            this.daThanhToan = daThanhToan;
        }

        public Double getConLai() {
            return conLai;
        }

        public void setConLai(Double conLai) {
            this.conLai = conLai;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Object getDeletedTime() {
            return deletedTime;
        }

        public void setDeletedTime(Object deletedTime) {
            this.deletedTime = deletedTime;
        }
    }

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

    public PhoiGiongData getData() {
        return data;
    }

    public void setData(PhoiGiongData data) {
        this.data = data;
    }
}
