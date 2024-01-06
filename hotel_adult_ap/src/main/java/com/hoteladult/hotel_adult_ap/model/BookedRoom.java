package com.hoteladult.hotel_adult_ap.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "BookedRoom")
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name = "checkInDate")
    private LocalDate checkInDate;
    @Column(name = "checkOutDate")
    private LocalDate checkOutDate;
    @Column(name = "guestFullName")
    private String guestFullName;
    @Column(name = "guestEmail")
    private String guestEmail;
    @Column(name = "numOfAdult")
    private int numOfAdult;
    @Column(name = "numOfChildren")
    private int numOfChildren;
    @Column(name = "totalNumOfGuest")
    private int totalNumOfGuest;

    @Column(name = "bookingConfirmCode")
    private Number bookingConfirmCode;

    @ManyToOne(fetch = FetchType.LAZY) // Mỗi đơn đặt phòng thuộc 1 phòng cụ thể
    @JoinColumn(name = "room_id")
    private Room room;
    public void calculateTotalNumberOfGuest() {
        this.totalNumOfGuest = this.numOfChildren + this.numOfAdult;
    }

    public BookedRoom(LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, int numOfAdult, int numOfChildren, int totalNumOfGuest, Number bookingConfirmCode, Room room) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestFullName = guestFullName;
        this.guestEmail = guestEmail;
        this.numOfAdult = numOfAdult;
        this.numOfChildren = numOfChildren;
        this.totalNumOfGuest = totalNumOfGuest;
        this.bookingConfirmCode = bookingConfirmCode;
        this.room = room;
    }

    public BookedRoom() {
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName(String guestFullName) {
        this.guestFullName = guestFullName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public int getNumOfAdult() {
        return numOfAdult;
    }

    public void setNumOfAdult(int numOfAdult) {
        this.numOfAdult = numOfAdult;
        calculateTotalNumberOfGuest();
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumberOfGuest();
    }

    public int getTotalNumOfGuest() {
        return totalNumOfGuest;
    }

    public void setTotalNumOfGuest(int totalNumOfGuest) {
        this.totalNumOfGuest = totalNumOfGuest;
    }

    public Number getBookingConfirmCode() {
        return bookingConfirmCode;
    }

    public void setBookingConfirmCode(Number bookingConfirmCode) {
        this.bookingConfirmCode = bookingConfirmCode;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
