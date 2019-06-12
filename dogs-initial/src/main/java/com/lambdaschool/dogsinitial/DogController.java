package com.lambdaschool.dogsinitial;

import com.lambdaschool.dogsinitial.errors.DogNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dogs")
public class DogController {
    private static final Logger logger = LoggerFactory.getLogger(DogController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    // localhost:8080/dogs/dogs
    @GetMapping(value = "/dogs", produces = {"application/json"})
    public ResponseEntity<?> getAllDogs() {
        logger.info("/dogs/dogs was accessed");

        MessageDetail messageDetail =
                MessageDetail.builder()
                        .text("/dogs/dogs was accessed at " + new Date())
                        .priority(5)
                        .secret(false)
                        .build();
        rabbitTemplate.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH, messageDetail);

        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDogDetail(@PathVariable long id) {
        logger.info("/dogs/{id} was accessed where id=" + id);

        Dog rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));

        if (rtnDog == null) {
            throw new DogNotFound("Dog where id=" + id + " was not found.");
        }

        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}")
    public ResponseEntity<?> getDogBreeds(@PathVariable String breed) {
        logger.info("/dogs/breeds/{breed} was accessed where breed=" + breed);

        rabbitTemplate.convertAndSend(DogsinitialApplication.QUEUE_NAME_LOW,
                MessageDetail.builder()
                        .text("/dogs/breeds/" + breed + " was accessed at " + new Date())
                        .priority(5)
                        .secret(false)
                        .build());

        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    @GetMapping(value = "/table/all_dogs")
    public ModelAndView displayDogsTable() {
        logger.info("/dogs/table/all_dogs was accessed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");

        ArrayList<Dog> dogList = DogsinitialApplication.ourDogList.dogList.stream()
                .sorted(Comparator.comparing(Dog::getBreed))
                .collect(Collectors.toCollection(ArrayList::new));

        mav.addObject("dogList", dogList);

        return mav;
    }

    //display all dogs suitable for apartments ordered by breed
    @GetMapping(value = "/table/apartment_dogs")
    public ModelAndView displayApartmentDogsTable() {
        logger.info("/dogs/table/apartment_dogs was accessed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");

        ArrayList<Dog> dogList = DogsinitialApplication.ourDogList.dogList.stream()
                .filter(Dog::isApartmentSuitable)
                .sorted(Comparator.comparing(Dog::getBreed))
                .collect(Collectors.toCollection(ArrayList::new));

        mav.addObject("dogList", dogList);

        return mav;
    }
}