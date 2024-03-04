package com.example.securitydemo.security;

import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with login '" + username + "' not found");
        }

        return withUsername(username)
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .build();
    }


   private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> collection) {
       List<GrantedAuthority> authorities = new ArrayList<>();

       // Добавляем роли в список authorities
       for (Role role : collection) {
           authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

           // Добавляем права доступа (authorities) связанные с каждой ролью
           role.getAuthorities().forEach(authority ->
                   authorities.add(new SimpleGrantedAuthority(authority.getAuthority()))
           );
       }

       return authorities;
   }
}