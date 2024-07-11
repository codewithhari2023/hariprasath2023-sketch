package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.AddressDto;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Repository.AddressRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.AddressRequest;
import OnlineEBookStore.demo.Response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressDto addressDto;

    public List<AddressResponse> findall() {
        List<Address> addressList = addressRepository.findAll();
        return addressDto.mapToAddressResponse(addressList);
    }


    @Transactional
    public List<AddressResponse> create(AddressRequest addressRequest) {
        Address address = addressDto.mapToAddress(addressRequest);
        CommonUser commonUser = userRepository.findById((addressRequest.getUserId())).orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", addressRequest.getUserId()));
        address.setCommonUser(commonUser);
        addressRepository.save(address);
        return findall();
    }
    public List<AddressResponse> update(AddressRequest addressRequest) {
        Address address = addressDto.mapToAddress(addressRequest);
        CommonUser commonUser = userRepository.findById((addressRequest.getUserId())).orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", addressRequest.getUserId()));
        address.setCommonUser(commonUser);
        addressRepository.save(address);
        return findall();
    }

    public  List<AddressResponse> deleteById(Long id) {
        addressRepository.deleteById(id);
        return findall();
    }



}
