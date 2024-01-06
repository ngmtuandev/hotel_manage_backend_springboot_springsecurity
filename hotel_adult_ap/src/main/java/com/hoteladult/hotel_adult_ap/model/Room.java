package com.hoteladult.hotel_adult_ap.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long roomID;
    @Column(name = "roomType")
    private String roomType;
    @Column(name = "roomPrice")
    private BigDecimal roomPrice;

    // Tình trạng phòng đã được đặt hay chưa
    @Column(name = "isBooked")
    private boolean isBooked = false;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private Blob photo;



    // Lưu thong tin về các đặt phòng của phòng này (BookedRoom)
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings; // mỗi lần đặt phòng -> BookedRoom lwuu trong này
    // -- lịch sử

    public Room() {
        // Room được tạo -> khởi tạo list bookings ->tránh lỗi
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom bookedRoom) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }

        bookings.add(bookedRoom);
        bookedRoom.setRoom(this);

        isBooked = true;

        double bookingCode = Math.random();
        bookedRoom.setBookingConfirmCode(bookingCode);

    }

    public Room(Long roomID, String roomType, BigDecimal roomPrice, boolean isBooked, Blob photo, List<BookedRoom> bookings) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photo;
        this.bookings = bookings;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
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

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public List<BookedRoom> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookedRoom> bookings) {
        this.bookings = bookings;
    }

    public void setPhoto(String photoBase64) {
    }
}
