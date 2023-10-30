package org.example.controllers;

import com.thoughtworks.qdox.model.expression.Add;
import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.jpa.AddressBookRepository;
import org.example.jpa.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ABRestController {

    @Autowired
    AddressBookRepository repository;

    @Autowired
    BuddyInfoRepository buddyInfoRepository;
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/initializeAddressBook")
    public AddressBook addressBook() {
        AddressBook a = new AddressBook();
        counter.getAndIncrement();
        repository.save(a);
        return a;
    }

    @GetMapping(value ="/viewBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressBook viewAddressBook() {
        Iterable<AddressBook> a = repository.findAll();
        ArrayList<AddressBook> aList = new ArrayList<>();
        for (AddressBook ab : a) {
            aList.add(ab);
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(aList.size());
        return aList.get(randomIndex);
    }

    @RequestMapping(value = "/addBuddyToBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuddyInfo addBud(@RequestParam Long id,@RequestParam String name, @RequestParam String number,
                         @RequestParam String address) {

        AddressBook addressBook = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        BuddyInfo b = new BuddyInfo(name, number, address);
        buddyInfoRepository.save(b);

        addressBook.addBuddy(b);
        System.out.println(addressBook);
        repository.save(addressBook);
        return b;
    }
}
