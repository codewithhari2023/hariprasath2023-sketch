package com.bookshopping.bookshopping.Service;

import com.bookshopping.bookshopping.Dto.AddressDto;
import com.bookshopping.bookshopping.Exception.ResourceNotFoundException;
import com.bookshopping.bookshopping.Model.Address;
import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Repository.AddressRepository;
import com.bookshopping.bookshopping.Repository.UserRepository;
import com.bookshopping.bookshopping.Request.AddressRequest;
import com.bookshopping.bookshopping.Response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class Addressservice {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressDto addressDto;

    public AddressResponse findall() {
        List<Address> addressList = addressRepository.findAll();
        return addressDto.mapToAddressResponse(addressList);
    }

    @Transactional
    public AddressResponse create(AddressRequest addressRequest) {
        Address address = addressDto.mapToAddress(addressRequest);
        AppUser appUser = userRepository.findById((addressRequest.getUserId())).orElseThrow(() -> new ResourceNotFoundException("userId", "userId", addressRequest.getUserId()));
        address.setAppUser(appUser);
        addressRepository.save(address);
        return findall();
    }

    public AddressResponse update(AddressRequest addressRequest) {
        Address address = addressDto.mapToAddress(addressRequest);
        AppUser appUser = userRepository.findById((addressRequest.getUserId())).orElseThrow(() -> new ResourceNotFoundException("userId", "userId", addressRequest.getUserId()));
        address.setAppUser(appUser);
        addressRepository.save(address);
        return findall();
    }

    public AddressResponse deleteById(Long id) {
        addressRepository.deleteById(id);
        return findall();
    }
}