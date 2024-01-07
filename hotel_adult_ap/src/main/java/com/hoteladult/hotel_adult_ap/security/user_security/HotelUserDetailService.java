package com.hoteladult.hotel_adult_ap.security.user_security;

import com.hoteladult.hotel_adult_ap.model.User;
import com.hoteladult.hotel_adult_ap.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
/*
 UserDetailsService này cung cấp một cách để xác định
 cách tải thông tin người dùng từ cơ sở dữ liệu
 hoặc bất kỳ nguồn dữ liệu nào khác.
*/
public class HotelUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    /*
 UserDetailsService : 1 method loadUserByUsername
 UserDetailsService -> get info user from database -> return UserDetails
    */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // HotelUserDetail.builUserDetail -> la method class HotelUserDetail
        return HotelUserDetail.builUserDetail(user);
    }
}
