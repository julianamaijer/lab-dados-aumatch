package com.fateczl.aumatch.controller;

import com.fateczl.aumatch.model.Animal;
import com.fateczl.aumatch.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Animal create(@RequestBody Animal animal){
        return animalService.save(animal);
    }

   @PutMapping("/{animalId}")
   public ResponseEntity<?> update(@PathVariable Long animalId, @RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.update(animalId,animal));
   }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Animal> delete(@PathVariable Long animalId){
        animalService.delete(animalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> get(@PathVariable Long animalId) {
        return ResponseEntity.ok(animalService.findById(animalId));
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotal() {
        return ResponseEntity.ok(animalService.getQuantidadeAnimais());
    }

    @GetMapping
    public ResponseEntity<List<Animal>> getAll() {
        return ResponseEntity.ok(animalService.findAll());
    }
}