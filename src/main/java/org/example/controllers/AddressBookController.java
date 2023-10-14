package org.example.controllers;

import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.jpa.AddressBookRepository;
import org.example.jpa.BuddyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AddressBookController {
    @Autowired
    AddressBookRepository repository;

    @Autowired
    BuddyInfoRepository buddyInfoRepository;

    @GetMapping("/addressBook")
    public String newAddressBook(Model model) {
        AddressBook book = new AddressBook();
        repository.save(book);
        model.addAttribute("addressBook", book);
        return "addressbook";
    }

    @PostMapping("/addToAddressBook/{id}")
    public String addBuddyInfo(@PathVariable(name = "id") Long addressBookId, @RequestBody BuddyInfo buddyInfo, Model model) {
        AddressBook addressBook = repository.findById(addressBookId).isPresent() ? repository.findById(addressBookId).get() : null;

        if (addressBook == null) {
            throw new ResourceNotFoundException("Address book not found");
        }

        buddyInfoRepository.save(buddyInfo);
        addressBook.addBuddy(buddyInfo);
        repository.save(addressBook);
        model.addAttribute("newBuddy", buddyInfo);
        System.out.println(buddyInfo);
        return "buddyinfo";

    }

    @PostMapping("/removeBuddy")
    public String removeBuddy(@RequestBody Map<String, Long> removeInfo) {
        AddressBook addressBook = repository.findById(removeInfo.get("addressBookId"))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find AddressBook with id = " + removeInfo.get("addressBookId")));

        System.out.println("gehheheh" +  removeInfo.get("buddyId"));

        addressBook.removeBuddyById(removeInfo.get("buddyId"));
        repository.save(addressBook);

        return "addressbook";
    }

    @GetMapping("/addressbook/{id}")
    public String getAllBuddiesByAddressBookId(@PathVariable(value = "id") Long addressBookId, Model model) {
        AddressBook addressBook = repository.findById(addressBookId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + addressBookId));

        Map<String, BuddyInfo> buddies = new HashMap<String, BuddyInfo>(addressBook.getBuddies());
        model.addAttribute("buddies", buddies);

        return "addressbook";
    }


    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

}
