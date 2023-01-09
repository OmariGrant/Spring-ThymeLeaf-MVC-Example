package com.example.thymeleafexample.controllers;

import com.example.thymeleafexample.entities.Actor;
import com.example.thymeleafexample.repositories.ActorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class SakilaController {
    private final ActorRepository actorRepository;

    public SakilaController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @GetMapping("/sakila/testName")
    public String getTestName(String name, Model model){
        model.addAttribute("name", name);
        return "displayName";
    }

    @GetMapping("sakila/actor")
    public String getActor(int id, Model model){
        Optional<Actor> actor = actorRepository.findById(id);

        if (actor.isPresent()){
            model.addAttribute("actor", actor.get());

        } else {
            model.addAttribute("id", id);
            return "errors/displayActor";
        }

        return "displayActor";
    }
    
    @GetMapping("sakila/allActors")
        public String getAllActors(Model model){
            List<Actor> actors = actorRepository.findAll();

            model.addAttribute("actors", actors);
            return "displayAllActors";
        }


    @GetMapping("/sakila/newActor")
    public String newActorSuccess(){
        return "displayActorForm";
    }

        @PostMapping("/sakila/newActor")
    public String postNewActor(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @ModelAttribute("actor") Actor actor
                               ){


            actor.setFirstName(firstName);
            actor.setLastName(lastName);
            actor.setLastUpdate(Instant.now());
            actorRepository.save(actor);

            return "displayActorForm";

        }


    @GetMapping("/sakila/updateActor")
    public String updateActorForm(@RequestParam int id,
                                  Model model
                                  ){

        try {

            Actor actor = actorRepository.findById(id).get();
            model.addAttribute("actor", actor);
            return "displayUpdateActorForm";

        } catch (NoSuchElementException e){
            return "errors/displayActor";

        }

    }
    @PostMapping("/sakila/updateActor")
    public String updateActor(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam int id,
                              Model model
    ){

        Actor updateActor = actorRepository.findById(id).get();
        updateActor.setFirstName(firstName);
        updateActor.setLastName(lastName);
        updateActor.setLastUpdate(Instant.now());
        actorRepository.save(updateActor);

        model.addAttribute("actor", updateActor);

        return "displayUpdateActorForm";

    }

    @PostMapping("/sakila/deleteActor")
    public String deleteActor(@RequestParam int id, Model model){

        Actor actor = actorRepository.findById(id).get();
        actorRepository.deleteById(id);

        model.addAttribute("actor", actor);
        model.addAttribute("id", id);

        return "displayActorDeleteSuccess";

    }

}
