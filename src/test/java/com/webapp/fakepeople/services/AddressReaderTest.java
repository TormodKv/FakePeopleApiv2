package com.webapp.fakepeople.services;

import com.webapp.fakepeople.model.Address;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddressReaderTest {

    @Test
    void readLines() throws IOException {
        AddressReader addressReader = new AddressReader();
        ArrayList<Address> addresses = addressReader.readLines(false);
        assertTrue(addresses.size() > 5 &&
                addresses.stream().allMatch(a -> a.getAddressText().length() > 0) &&
                addresses.stream().anyMatch(a -> a.getHouseNumber() != 0));
    }
}