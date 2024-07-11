package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.AddressRequest;
import OnlineEBookStore.demo.Response.AddressResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressDto {
    public List<AddressResponse> mapToAddressResponse(List<Address> addressList) {
//        AddressRequest addressRequest=new AddressRequest();
        List<AddressResponse>addressResponses=new ArrayList<>();
        CommonUser commonUser=new CommonUser();

        List<Address> addresses=new ArrayList<>();
        for(Address addressx:addressList)
        {
            AddressResponse addressResponse=new AddressResponse();
            addressResponse.setId(addressx.getId());
            addressResponse.setCity(addressx.getCity());
            addressResponse.setUserId(addressx.getCommonUser().getId());
            addressResponse.setStreet(addressx.getStreet());
            addressResponse.setZipcode(addressx.getZipcode());
            addressResponses.add(addressResponse);

        }
        return addressResponses;
    }

    public Address mapToAddress(AddressRequest addressRequest) {
        Address address = new Address();
        if (addressRequest.getId() != null) {
            address.setId((addressRequest.getId()));

        }

        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setZipcode(addressRequest.getZipcode());
        return address;
    }
}
