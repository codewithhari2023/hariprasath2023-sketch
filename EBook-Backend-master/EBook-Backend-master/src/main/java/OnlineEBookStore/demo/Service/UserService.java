package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.AuthDto;
import OnlineEBookStore.demo.Exeption.InvalidUserException;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.AddressRepository;
import OnlineEBookStore.demo.Repository.RoleRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.LoginRequest;
import OnlineEBookStore.demo.Request.RegisterRequest;
import OnlineEBookStore.demo.Request.VerificationRequest;
import OnlineEBookStore.demo.Response.AuthResponse;
import OnlineEBookStore.demo.Response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private AuthDto authDto;
    @Autowired
   private UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private  TwoFactorAuthenticationService tfaService;

    public AuthResponse register(RegisterRequest registerRequest){

        if (userRepository.findByUserName(registerRequest.getUsername()).isPresent()) {
            throw new InvalidUserException("User already exists");
        }

        CommonUser commonUser=new CommonUser();
        commonUser.setUsername(registerRequest.getUsername());
        commonUser.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        commonUser.setName(registerRequest.getName());
        commonUser.setRole(roleRepository.findByRole(Roles.PUBLIC));
        if (registerRequest.isMfaEnabled()) {
            commonUser.setSecret(tfaService.generateNewSecret());
        }
        commonUser=userRepository.save(commonUser);
        AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(commonUser.getSecret()))
                .mfaEnabled(commonUser.isMfaEnabled())
                .build();
        return  authDto.MapToAuthResponse(commonUser);

    }
    public  AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        LocalDateTime loginTime = LocalDateTime.now();
        String ipAddress = request.getRemoteAddr();
        CommonUser commonUser = userRepository.findByUserName(loginRequest.getUsername()).orElseThrow(() -> new InvalidUserException("Email not found"));

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), commonUser.getPassword())) {
            throw  new InvalidUserException("password not found");

        }
        if (commonUser.isMfaEnabled()) {
          AuthenticationResponse.builder()
                    .mfaEnabled(true)
                    .build();
        }

        commonUser.setLoginTime(loginTime);
        commonUser.setIpAddress(ipAddress);
        userRepository.save(commonUser);
        AuthenticationResponse.builder()
                .mfaEnabled(false)
                .build();
        return authDto.MapToAuthResponse(commonUser);


    }
    public List<CommonUser>findAllUsers(){return userRepository.findAll();}
    public CommonUser findOneUSer(Long id){return  userRepository.findById(id).orElseThrow(()->new ResoucreNotFoundException("commonuser","id",id));}

//   public  void savedatatoDb(MultipartFile file) throws IOException {
//        if(ExcelService.isvalid(file)){
//            List<CommonUser>commonUsers=ExcelService.getUserData(file.getInputStream());
//            userRepository.saveAll(commonUsers);
//        }
//   }
   public  void getusers(List<CommonUser> commonUsers){
        userRepository.saveAll(commonUsers);
   }

    public List<CommonUser> saveUsers(List<CommonUser> userList) {
        userRepository.saveAll(userList);
        return userList;
    }
    public AuthenticationResponse verifyCode(
            VerificationRequest verificationRequest
    ) {
        CommonUser user = userRepository
                .findByUserName(verificationRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with %S", verificationRequest))
                );
        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        };
        return AuthenticationResponse.builder()
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

}
