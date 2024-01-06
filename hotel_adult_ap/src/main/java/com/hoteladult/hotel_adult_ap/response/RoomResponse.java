package com.hoteladult.hotel_adult_ap.response;

import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse> bookings;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    @Override
    public String toString() {
        return "RoomResponse{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", isBooked=" + isBooked +
                ", photo='" + photo + '\'' +
                ", bookings=" + bookings +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public List<BookingResponse> getBookings() {
        return bookings;
    }

    public String getPhoto() {
        return photo;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked,
                        byte[] photoBytes , List<BookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }
}
