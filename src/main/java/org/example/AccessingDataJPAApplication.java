package org.example;

import org.example.jpa.AddressBookRepository;
import org.example.jpa.BuddyInfoRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * SpringBootApplication tag encompasses all these
 *  Configuration:
 *  EnableAutoConfiguration:
 *  ComponentScan:
 */
@SpringBootApplication
public class AccessingDataJPAApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJPAApplication.class);

    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJPAApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner buddyInfoDemo(BuddyInfoRepository buddyRepo, AddressBookRepository addressRepo) {
        return (args) -> {
            buddyRepo.save(new BuddyInfo("Guy", "123", "1 address"));
            buddyRepo.save(new BuddyInfo("Yug", "456", "2 address"));

            //logging
            log.info("BuddyInfo using findAll()");
            log.info("--------------------------");
            for (BuddyInfo b : buddyRepo.findAll()) {
                log.info("\n" + b.toString());
            }
            log.info("");

            //log by IDs
            log.info("BuddyInfo using findByID()");
            log.info("--------------------------");
            BuddyInfo bud = buddyRepo.findById(1L);
            log.info("\n" + bud.toString());


            AddressBook addy = new AddressBook();
            addy.addBuddy(new BuddyInfo("Guy", "123", "1 address"));
            addy.addBuddy(new BuddyInfo("Yug", "456", "2 address"));
            addressRepo.save(addy);

            //logging
            log.info("AddressBook using findAll()");
            log.info("--------------------------");
            for (AddressBook a : addressRepo.findAll()) {
                //                for (BuddyInfo b : a.getBuddies().values()) {
                //                    log.info(b.toString());
                //                }
                log.info("\n" + a.toString());
            }
            log.info("");
        };
    }
}


