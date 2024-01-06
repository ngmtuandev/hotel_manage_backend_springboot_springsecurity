package com.hoteladult.hotel_adult_ap.controller;

import com.hoteladult.hotel_adult_ap.DTO.RoomCreatedRequest;
import com.hoteladult.hotel_adult_ap.model.BookedRoom;
import com.hoteladult.hotel_adult_ap.model.ResultModel;
import com.hoteladult.hotel_adult_ap.model.Room;
import com.hoteladult.hotel_adult_ap.reposiroty.RoomRepositories;
import com.hoteladult.hotel_adult_ap.response.BookingResponse;
import com.hoteladult.hotel_adult_ap.response.RoomResponse;
import com.hoteladult.hotel_adult_ap.services.BookingService;
import com.hoteladult.hotel_adult_ap.services.RoomServiceImplement;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomServiceImplement roomServiceImplement;

    @Autowired
    RoomRepositories roomRepositories;

    @Autowired
    BookingService bookingService;

    @PostMapping("/add/new-room")
    public ResponseEntity<ResultModel> addNewRoom(

            // @RequestParam : url query hoặc formdata
            // trường hp này ửi lên bằng formData
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice")BigDecimal roomPrice
            ) throws SQLException, IOException {

        RoomCreatedRequest newRoomRequest = new RoomCreatedRequest();
        newRoomRequest.setPhoto(photo);
        newRoomRequest.setRoomType(roomType);
        newRoomRequest.setRoomPrice(roomPrice);

        Room newSaveRoom = roomServiceImplement.addNewRoom(newRoomRequest);
        RoomResponse response_new_room = new RoomResponse(newSaveRoom.getRoomID(), newSaveRoom.getRoomType(), newSaveRoom.getRoomPrice());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResultModel("ok", "Create room successfully", response_new_room)
        );

    }


    @GetMapping("/room/types")
    public List<String> geRoomType() {
        return roomServiceImplement.getAllRoomTypes();
    }

    @GetMapping("/all-room")
    public List<RoomResponse> getAllRoom() throws SQLException {
        List<Room> rooms = roomServiceImplement.getAllRooms();
        // return -> 1 list
        List<RoomResponse> list_rooms = new ArrayList<>();
        for (Room room : rooms) {
            System.out.println(room.getRoomType());
            // get ra photo
            byte[] photoByteRoom = roomServiceImplement.getRoomPhotoByRoomID(room.getRoomID());
//            System.out.println("photoByteRoom >>>>" + photoByteRoom);
            if (photoByteRoom != null && photoByteRoom.length > 0) {

                // convert photo -> base 64
                String photoBase64 = Base64.encodeBase64String(photoByteRoom);
//                System.out.println("photoBase64 >>>>" + photoBase64);
                RoomResponse room_get = new RoomResponse(room.getRoomID(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoBase64);

//                System.out.println("test : >>>" + room_get);

                list_rooms.add(room_get);
            }
        }
        return list_rooms;
    }


//    ============== Delete Room
    @DeleteMapping("/delete/room/{roomID}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomID) {
        roomServiceImplement.deleteRoom(roomID);
        System.out.println("delete" + roomID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    ============= Update
    @PutMapping("/update/{roomId}")
    public ResponseEntity<ResultModel> updateRoom(
            @PathVariable Long roomId,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) BigDecimal roomPrice,
            @RequestParam(required = false) MultipartFile photo
            ) throws IOException, SQLException {
        System.out.println("check update >>>>" + roomType);
        byte[] photoBytes = photo != null && !photo.isEmpty() ? photo.getBytes() : roomServiceImplement.getRoomPhotoByRoomID(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length > 0
                ? new SerialBlob(photoBytes) : null;
        Room theRoomUpdated = roomServiceImplement.updateRoom(roomId, roomType, roomPrice, photoBytes);
        theRoomUpdated.setPhoto(photoBlob);
        RoomResponse roomUpdateNew = getRoomResponse(theRoomUpdated);
        System.out.println("roomUpdateNew >>>>" + roomUpdateNew);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResultModel("ok", "Create room successfully", roomUpdateNew)
        );


    }


//    ============================ Get Detail Item
    @GetMapping("/room/{roomId}")
    public Room getRoomById(@PathVariable Long roomId) throws SQLException {
        System.out.println("tesst get detail >>>" + roomId);
        Optional<Room> theRoomDetail = roomServiceImplement.getRoomById(roomId);

        if (theRoomDetail.isPresent()) {
            Room room = new Room();
            Room roomDetail = theRoomDetail.get();
            byte[] photoBytes = roomServiceImplement.getRoomPhotoByRoomID(roomDetail.getRoomID());
            if (photoBytes != null && photoBytes.length > 0) {
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                room.setRoomType(roomDetail.getRoomType());
                room.setRoomPrice(roomDetail.getRoomPrice());
                room.setPhoto(photoBase64);
            }
            return room;
        }
        else {
            return null;
        }

    }




//    ================================= check bookedRoom
    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getRoomID());
        List<BookingResponse> booking_detail_info = bookings
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getGuestEmail(),
                        booking.getBookingConfirmCode()
                )).toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            }
            catch (SQLException e)  {
                System.out.println("photo errorr in controller PhotoRetrievalException");
            }
        }
        return new RoomResponse(room.getRoomID(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoBytes, booking_detail_info);

    }

    private List<BookedRoom> getAllBookingsByRoomId(Long id) {
        return bookingService.getAllBookingsByRoomId(id);
    }

}
