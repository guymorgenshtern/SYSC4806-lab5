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

    @PostMapping("/addressBook")
    public String newAddressBook(Model model) {
        AddressBook book = new AddressBook();
        repository.save(book);
        model.addAttribute("addressBook", book);
        return "addressbook";
    }

//    @PostMapping("/addToAddressBook/{id}")
//    public String addBuddyInfo(@PathVariable(name = "id") Long addressBookId, @RequestBody BuddyInfo buddyInfo, Model model) {
//        AddressBook addressBook = repository.findById(addressBookId).isPresent() ? repository.findById(addressBookId).get() : null;
//
//        if (addressBook == null) {
//            throw new ResourceNotFoundException("Address book not found");
//        }
//
//        buddyInfoRepository.save(buddyInfo);
//        addressBook.addBuddy(buddyInfo);
//        repository.save(addressBook);
//        model.addAttribute("newBuddy", buddyInfo);
//        System.out.println(buddyInfo);
//        return "addressbook";
//
//    }

    @PostMapping("/addToAddressBook/{id}")
    public String addBuddyInfo(@PathVariable(name = "id") Long addressBookId, @RequestParam String name,
                               @RequestParam String number, @RequestParam String address, Model model) {

        AddressBook addressBook = repository.findById(addressBookId).isPresent() ? repository.findById(addressBookId).get() : null;

        if (addressBook == null) {
            throw new ResourceNotFoundException("Address book not found");
        }

        BuddyInfo b = new BuddyInfo(name, number, address);
        buddyInfoRepository.save(b);
        addressBook.addBuddy(b);
        repository.save(addressBook);
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("addressBookId", addressBookId);
        return "addressbook";

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

        model.addAttribute("addressBook", addressBook.toString());
        model.addAttribute("addressBookId", addressBookId);

        return "addressbook";
    }


    @GetMapping("/")
    public String createAddressBook(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "formPage";
    }

    @PostMapping("/")
    public String addressBookView(@ModelAttribute AddressBook book, Model model) {
        repository.save(book);
        model.addAttribute("addressBook", book.toString());
        model.addAttribute("addressBookId", book.getId());
        return "addressbook";
    }

}
