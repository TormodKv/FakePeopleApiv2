package com.webapp.fakepeople.repository;

import com.webapp.fakepeople.interfaces.IAddressRepository;
import com.webapp.fakepeople.model.Address;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class AddressRepository implements IAddressRepository {

    private ArrayList<Address> addresses = new ArrayList<>();

    @Override
    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    @Override
    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void addAddress(Address address){
        addresses.add(address);
    }

    @Override
    public void addAllAddresses(ArrayList<Address> addresses) {
        this.addresses.addAll(addresses);
    }

    @Override
    public Address getById(UUID id){
        return addresses.stream().filter(x -> x.getId().compareTo(id) == 0).findAny().orElse(null);
    }
}
