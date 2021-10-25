package com.properties.propertiesapp.authentication.filter;


import com.properties.propertiesapp.authentication.entity.Role;
import com.properties.propertiesapp.authentication.entity.StaffDetails;
import com.properties.propertiesapp.authentication.repository.StaffDetailsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppUserDetailsService implements UserDetailsService {
    private final StaffDetailsRepository userRepository;

    public AppUserDetailsService(StaffDetailsRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        StaffDetails u = userRepository.findAllByEmailAddress( username );
        if(u == null) {
            throw new UsernameNotFoundException("User does not exist with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                u.getEmailAddress(),
                u.getPassword(),
                u.getRolesCollection().stream().map( Role::getName )
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toSet())
        );
    }
}
