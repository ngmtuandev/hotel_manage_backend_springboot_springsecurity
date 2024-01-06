package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.DTO.RoomCreatedRequest;
import com.hoteladult.hotel_adult_ap.model.Room;
import com.hoteladult.hotel_adult_ap.reposiroty.RoomRepositories;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImplement implements IRoomService {

    @Autowired
    RoomRepositories roomRepositories;

    @Override
    @Transactional
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


    @Override
    public List<String> getAllRoomTypes() {
        return roomRepositories.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepositories.findAll();
    }


    // get photo (đặc biệt)
    @Override
    @Transactional
    public byte[] getRoomPhotoByRoomID(Long roomId) throws SQLException {
        Optional<Room> theRoom = roomRepositories.findById(roomId);
        if(theRoom.isEmpty()){
            System.out.println("not found");
        }
        Blob photoBlob = theRoom.get().getPhoto();
        if(photoBlob != null){
            // get theo blob
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public Void deleteRoom(Long roomID) {

        //Optional -> không cần kiểm tra null -> isPresent(), orElse()
        Optional<Room> theRoomDelete = roomRepositories.findById(roomID);
        if (theRoomDelete.isPresent()) {
            roomRepositories.deleteById(roomID);
        }
        return null;
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room find_room = roomRepositories.findById(roomId).get();
        if (roomType != null) find_room.setRoomType(roomType);
        if (roomPrice != null) find_room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                find_room.setPhoto(new SerialBlob(photoBytes));
            } catch (SerialException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return roomRepositories.save(find_room);

    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return roomRepositories.findById(roomId);
    }

}
