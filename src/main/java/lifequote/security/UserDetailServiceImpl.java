package lifequote.security;

import lifequote.domain.User;
import lifequote.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milanashara on 1/3/17.
 */
@Repository
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException
    {

        List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
        gas.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = repo.findByName(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), true, true, true, true, gas);
        return userDetails;
    }
}
