package com.hoteladult.hotel_adult_ap.reposiroty;

import com.hoteladult.hotel_adult_ap.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.SQLException;
import java.util.List;

public interface RoomRepositories extends JpaRepository<Room, Long> {

    @Query(value = "SELECT DISTINCT room_type FROM Room", nativeQuery = true)
    // chỉ lấy 1 cột room_type -> type String
    List<String> findDistinctRoomTypes();

    // SELECT photo FROM Room WHERE roomId = ?;
    byte[] getRoomPhotoByRoomID(Long roomId) throws SQLException;


}
