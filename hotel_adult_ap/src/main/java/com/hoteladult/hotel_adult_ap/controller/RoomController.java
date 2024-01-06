package com.hoteladult.hotel_adult_ap.controller;

import com.hoteladult.hotel_adult_ap.DTO.RoomCreatedRequest;
import com.hoteladult.hotel_adult_ap.model.ResultModel;
import com.hoteladult.hotel_adult_ap.model.Room;
import com.hoteladult.hotel_adult_ap.response.RoomResponse;
import com.hoteladult.hotel_adult_ap.services.RoomServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomServiceImplement roomServiceImplement;

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

}
