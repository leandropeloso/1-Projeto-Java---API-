package br.com.ecommit.chamado.controllers;

import br.com.ecommit.chamado.services.dtos.CalledRecordDto;
import br.com.ecommit.chamado.models.CalledModel;
import br.com.ecommit.chamado.repositories.CalledRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CalledController {

    @Autowired
    CalledRepository calledRepository;

    @PostMapping("/called")
    public ResponseEntity<CalledModel> saveCalled(@RequestBody @Valid CalledRecordDto calledRecordDto){
        var calledModel = new CalledModel();
        BeanUtils.copyProperties(calledRecordDto, calledModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(calledRepository.save(calledModel));
    }
    @GetMapping("/called")
    public ResponseEntity<List<CalledModel>> getAllCalled(){
        List<CalledModel> calledList = calledRepository.findAll();
        if(!calledList.isEmpty()){
            for(CalledModel called : calledList){
                UUID id = called.getIdCalled();
                called.add(linkTo(methodOn(CalledController.class).getOneCalled(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(calledList);
    }

    @GetMapping("/called/{id}")
    public ResponseEntity<Object> getOneCalled(@PathVariable(value="id") UUID id){
        Optional<CalledModel> calledExist = calledRepository.findById(id);
        if(calledExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Called not found.");
        }
        calledExist.get().add(linkTo(methodOn(CalledController.class).getAllCalled()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(calledExist.get());
    }

    @PutMapping("/called/{id}")
    public ResponseEntity<Object> updateCalled(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid CalledRecordDto calledRecordDto){
        Optional<CalledModel> calledExist = calledRepository.findById(id);
        if(calledExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Called not found.");
        }
        var calledModel = calledExist.get();
        BeanUtils.copyProperties(calledRecordDto, calledModel);
        return ResponseEntity.status(HttpStatus.OK).body(calledRepository.save(calledModel));
    }

    @DeleteMapping("/called/{id}")
    public ResponseEntity<Object> deleteCalled(@PathVariable(value="id") UUID id) {
        Optional<CalledModel> calledExist = calledRepository.findById(id);
        if(calledExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Called not found.");
        }
        calledRepository.delete(calledExist.get());
        return ResponseEntity.status(HttpStatus.OK).body("Called deleted successfully.");
    }

    @GetMapping("/called/open")
    public ResponseEntity<List<CalledModel>> getOpenCalled() {
        List<CalledModel> openCalledList = calledRepository.findOpenCalled();
        if (openCalledList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(openCalledList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(openCalledList);
    }

    @GetMapping("/called/in-progress")
    public ResponseEntity<List<CalledModel>> getInProgressCalled() {
        List<CalledModel> inProgressCalledList = calledRepository.findInProgressCalled();
        if (inProgressCalledList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(inProgressCalledList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(inProgressCalledList);
    }

    @GetMapping("/called/closed")
    public ResponseEntity<List<CalledModel>> getClosedCalled() {
        List<CalledModel> closedCalledList = calledRepository.findClosedCalled();
        if (closedCalledList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(closedCalledList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(closedCalledList);
    }
}
