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

@Controller
public class BuddyInfoController {
    @Autowired
    BuddyInfoRepository buddyInfoRepository;

    @PostMapping("/buddyInfo")
    //@ResponseBody removing this tag makes a view appear after fn runs
    public String newBuddyInfo(@RequestBody BuddyInfo buddyInfo, Model model) {

        buddyInfoRepository.save(buddyInfo);
        model.addAttribute("newBuddy", buddyInfo);
        System.out.println(buddyInfo);
        return "buddyinfo";
    }

}
