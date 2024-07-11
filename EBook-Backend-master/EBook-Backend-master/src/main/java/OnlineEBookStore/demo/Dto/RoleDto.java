package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Request.RoleRequest;
import OnlineEBookStore.demo.Response.RoleRegisterResponse;
import OnlineEBookStore.demo.Response.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleDto {
    public RoleResponse NewRole(Roles role){
        RoleResponse roleResponse=new RoleResponse();
//        roleResponse.setUserId(role.getCommonUser().getId());
        roleResponse.setRole(role.getRole());
        return roleResponse;
    }
    public RoleRegisterResponse MakeCrendentials(CommonUser commonUser)
    {
        RoleRegisterResponse roleRegisterResponse=new RoleRegisterResponse();
        roleRegisterResponse.setUsername(commonUser.getUsername());
        roleRegisterResponse.setEmail(commonUser.getName());
        roleRegisterResponse.setPassword(commonUser.getPassword());
        roleRegisterResponse.setRole(commonUser.getRole());
        return roleRegisterResponse;
    }

}
