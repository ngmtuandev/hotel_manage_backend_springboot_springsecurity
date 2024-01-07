package com.hoteladult.hotel_adult_ap.security.user_security;

import com.hoteladult.hotel_adult_ap.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/*
- Bieu dien, su dung thong tin nguoi dung
- Quan ly xac thuc va uy quyen user
*/

// Xac thuc nguoi dung -> bieu dien thong tin : id, email va cac quyen

/*
Uy quyen nguoi dung : SimpleGrantedAuthority -> the hien vai tro user
--> quyen User duoc tap hop va luu tai collection authorities
==> xac thuc quyen -> authorization
*/


public class HotelUserDetail implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;

    /*
    buildUserDetails : chuyen doi User -> HotelUserDetails
    ==> spring security su dung thong tin nay se xac thuc
    */

    /*
    GrantedAuthority : biểu diễn các quyền (vai trò) của người dùng.
    */


    // builUserDetail: se duoc goi HotelUserDetailService
    public static HotelUserDetail builUserDetail(User user) {
        //user : nhan duoc tu - HotelUserDetailService


           /*
            List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
            );
            */
        // GrantedAuthority : dai dien tap hop quyen nguoi dung
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                // dai dien cho vai tro - role
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // tra ve thong tin va quyen nguoi dung
        return new HotelUserDetail(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );

    }

    // quyen nguoi dung -> authorization
    // GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // getAuthorities : tra ve quyen (role) cua nguoi user
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public HotelUserDetail(Long id, String email, String password, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public HotelUserDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
