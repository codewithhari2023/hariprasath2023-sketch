package OnlineEBookStore.demo.DataLoader;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Status;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.OrderStatusRepository;
import OnlineEBookStore.demo.Repository.RoleRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private Boolean alreadySetup = false;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Roles adminRole = CreateRoleIfNotFound(Roles.ADMIN);
        Roles PublicRole = CreateRoleIfNotFound(Roles.PUBLIC);

        CreateUserIfNotFound("admin@gmail.com", "admin", "admin", adminRole);
        createStatusIfNotFount("Pending");
        createStatusIfNotFount("Confirmed");
        createStatusIfNotFount("Out for Delivery");
        createStatusIfNotFount("Delivered");
    }

    private CommonUser CreateUserIfNotFound(final String username, final String password, final String name, final Roles Role) {
        Optional<CommonUser> optionalUser = userRepository.findByUserName(username);
        CommonUser user = null;
        if (optionalUser.isEmpty()) {
            user = new CommonUser();
            user.setUsername(username);
            user.setName(name);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setRole(Role);
            user = userRepository.save(user);
        }

        return user;
    }

        private Roles CreateRoleIfNotFound ( final String username){
            Roles roles = roleRepository.findByRole(username);
            if (roles == null) {
                roles = new Roles();
                roles.setRole(username);
                roles = roleRepository.save(roles);

            }
            return roles;
        }
    private void createStatusIfNotFount(String status) {
        Status orderStatus = orderStatusRepository.findByStatus(status);
        if (orderStatus == null) {
            orderStatus = new Status();
            orderStatus.setStatus(status);
            orderStatusRepository.save(orderStatus);
        }
    }
    }

