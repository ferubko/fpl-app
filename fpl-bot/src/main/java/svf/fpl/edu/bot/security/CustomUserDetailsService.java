package svf.fpl.edu.bot.security;

import com.svf.fpl.edu.entity.AdminUser;
import com.svf.fpl.edu.entity.AdminUserRole;
import com.svf.fpl.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AdminUser user = userService.findUserByUserName(userName);
        List<SimpleGrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<SimpleGrantedAuthority> getUserAuthority(Set<AdminUserRole> userRoles) {
        return userRoles.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole()))
                .collect(Collectors.toList());
    }

    private UserDetails buildUserForAuthentication(AdminUser user, List<SimpleGrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}
