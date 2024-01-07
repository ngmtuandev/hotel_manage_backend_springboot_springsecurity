package com.hoteladult.hotel_adult_ap.security.jwt;

import com.hoteladult.hotel_adult_ap.security.user_security.HotelUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
/* Filter : xu ly cac yeu cau truoc khi chung duoc dua den cac endpoint cua ung dung */
public class AuthTokenFilter extends OncePerRequestFilter {
    /* filter duoc goi moi lan co request gui den */
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    HotelUserDetailService hotelUserDetailService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    // chuc nang 1 :  xac thuc nguoi dung dua tren token
    /* chuc nang 2 : thiet lap thong tin xac thuc trong " Security Context Holder " de
    su dung trong toan bo qua tri xu ly */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // trich xuat chuoi token tu header cua request
            if (jwt != null && jwtUtil.validatedToken(jwt)) {
                String email = jwtUtil.getUserNameFromToken(jwt); // lay username from JWT

                // UserDetails la cua Spring security
                // -> loadUserByUsername -> tai thong tin nguoi dung tu database
                UserDetails userDetails = hotelUserDetailService.loadUserByUsername(email);

                // UsernamePasswordAuthenticationToken : bieu dien thong tin ve qua trinh xac thuc
                //
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // tra ve userDetails


                /*thiet lap thong tin xac thuc trong " Security Context Holder " de
    su dung trong toan bo qua tri xu ly*/
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication : {} ", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    /*  === handle jwt === */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }

}
