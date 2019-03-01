package com.pinyougou.shop.service;

import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 9:03
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private SellerService sellerService;

    /** 调用认证服务*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /** 创建List集合封装角色 */
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        Seller seller = sellerService.findOne(username);
        if(seller != null && seller.getStatus().equals("1")) {
            /** 添加角色 */
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        }
        /** 返回用户信息对象 */
        return new User(username,seller.getPassword(), grantedAuths);
    }

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
