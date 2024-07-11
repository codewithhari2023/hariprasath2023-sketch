package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.RoleDto;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.RoleRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.RegisterRequest;
import OnlineEBookStore.demo.Request.RoleRegisterRequest;
import OnlineEBookStore.demo.Request.RoleRequest;
import OnlineEBookStore.demo.Response.AuthResponse;
import OnlineEBookStore.demo.Response.RoleRegisterResponse;
import OnlineEBookStore.demo.Response.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
 private    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
  private   RoleDto roleDto;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RoleResponse addRole(RoleRequest roleRequest){
        Roles roles=new Roles();

        roles.setRole(roleRequest.getRole());
        roles=roleRepository.save(roles);
        return  roleDto.NewRole(roles);
    }

    public RoleRegisterResponse register(RoleRegisterRequest registerRequest) {
        CommonUser commonUser=new CommonUser();
        commonUser.setUsername(registerRequest.getUsername());
        commonUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        commonUser.setName(registerRequest.getName());
        commonUser.setRole(registerRequest.getRole());
        commonUser=userRepository.save(commonUser);
        return roleDto.MakeCrendentials(commonUser);
    }
    public RoleResponse getRoles(Long id){
        Roles roles=roleRepository.findById(id).orElseThrow(()->new ResoucreNotFoundException("roleid","Roleid",id));
        return roleDto.NewRole(roles);
    }
    public List<Roles> findall() {
        return roleRepository.findAll();
    }
}
