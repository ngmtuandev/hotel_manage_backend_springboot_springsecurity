package com.hoteladult.hotel_adult_ap.services;

import com.hoteladult.hotel_adult_ap.DTO.RoomCreatedRequest;
import com.hoteladult.hotel_adult_ap.model.Room;

import java.io.IOException;
import java.sql.SQLException;

public interface IRoomService {

    Room addNewRoom (RoomCreatedRequest roomCreatedRequest) throws SQLException, IOException;

}
