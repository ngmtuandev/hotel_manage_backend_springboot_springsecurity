package com.hoteladult.hotel_adult_ap.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class RoomCreatedRequest {
    private MultipartFile photo;
    private String roomType;
    private BigDecimal roomPrice;

    public RoomCreatedRequest() {
    }

    public RoomCreatedRequest(MultipartFile photo, String roomType, BigDecimal roomPrice) {
        this.photo = photo;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }
}
