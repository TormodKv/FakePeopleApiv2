package com.webapp.fakepeople.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.webapp.fakepeople.model.Address;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AddressReader {
    @SuppressWarnings("unchecked")
    public ArrayList<Address> readLines(String path) throws IOException {

        return (ArrayList<Address>) new CsvToBeanBuilder(new FileReader(path))
                .withType(Address.class)
                .withSeparator(';')
                .build()
                .parse();
    }
    public ArrayList<Address> readLines(boolean limitInput) throws IOException {
        String fileName = limitInput ? "matrikkelenAdresseSmall.csv" /*Only selected addresses*/ : "matrikkelenAdresse.csv" /*All addresses*/;
        String filePath = new File("").getAbsolutePath().concat("\\src\\main\\resources\\static\\").concat(fileName);
        return readLines(filePath);
    }

}
