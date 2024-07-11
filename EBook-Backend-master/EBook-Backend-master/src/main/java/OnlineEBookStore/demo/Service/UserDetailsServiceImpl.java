package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonUser commonUser=userRepository.findByUserName(username).orElseThrow(()->{throw new UsernameNotFoundException("username is invalid:"+username);
        });
        if(commonUser!=null){
            return new User(commonUser.getUsername(),commonUser.getPassword(),buildSimpleGrantedAuthorities(commonUser.getRole()));
        }
        return null;
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Roles role) {
        List<SimpleGrantedAuthority>authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Role"+role.getRole()));
        return authorities;
    }
}
