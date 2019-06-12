package com.lambdaschool.dogsinitial.controller;

import com.lambdaschool.dogsinitial.exception.ResourceNotFound;
import com.lambdaschool.dogsinitial.model.Dog;
import com.lambdaschool.dogsinitial.DogsinitialApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    // localhost:8080/dogs/dogs
    @GetMapping(value = "/dogs", produces = {"application/json"})
    public ResponseEntity<?> getAllDogs()
    {
        DogsinitialApplication.ourDogList.dogList.sort((d1, d2) -> d1.getBreed().compareToIgnoreCase(d2.getBreed()));
        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> getDogDetail(@PathVariable long id) throws ResourceNotFound
    {
        Dog rtnDog;
        if (DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id)) == null)
        {
            throw new ResourceNotFound("Dog with id " + id + " not found");
        }else
        {
            rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));
        }
        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}", produces = {"application/json"})
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));

        if (rtnDogs.size() == 0)
        {
            throw new ResourceNotFound(("No Dog by that breed " + breed));
        }
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    // localhost:2019/data/employeetable
    @GetMapping(value = "/dogtable")
    public ModelAndView displayEmployeeTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList", DogsinitialApplication.ourDogList.dogList);

        return mav;
    }
}
