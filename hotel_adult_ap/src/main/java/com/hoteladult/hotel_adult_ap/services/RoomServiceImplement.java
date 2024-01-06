package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.DTO.RoomCreatedRequest;
import com.hoteladult.hotel_adult_ap.model.Room;
import com.hoteladult.hotel_adult_ap.reposiroty.RoomRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class RoomServiceImplement implements IRoomService {

    @Autowired
    RoomRepositories roomRepositories;

    @Override
    public Room addNewRoom(RoomCreatedRequest roomCreatedRequest) throws SQLException, IOException {
        Room room_new = new Room();
        room_new.setRoomPrice(roomCreatedRequest.getRoomPrice());
        room_new.setRoomType(roomCreatedRequest.getRoomType());
        if (!roomCreatedRequest.getPhoto().isEmpty()) {
            byte[] photoBytesRoom = roomCreatedRequest.getPhoto().getBytes();
            Blob photoBlobRoom = new SerialBlob(photoBytesRoom);
            room_new.setPhoto(photoBlobRoom);
        }
        return roomRepositories.save(room_new);
    }
}
