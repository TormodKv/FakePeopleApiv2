package com.webapp.fakepeople.interfaces;

import com.webapp.fakepeople.model.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public interface IAddressRepository {
    public ArrayList<Address> getAddresses();
    public void setAddresses(ArrayList<Address> addresses);
    public void addAddress(Address address);
    public void addAllAddresses(ArrayList<Address> addresses);
    public Address getById(UUID id);
}
