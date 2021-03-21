package es.dawgroup2.juding.security;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nick)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<GrantedAuthority> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return new org.springframework
                .security
                .core
                .userdetails
                .User(user.getNickname(), user.getPassword(), roles);
    }
}
