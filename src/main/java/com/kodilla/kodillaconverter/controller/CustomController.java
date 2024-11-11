package com.kodilla.kodillaconverter.controller;

import com.kodilla.kodillaconverter.domain.MyCustomClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custom/")
public class CustomController {

    @PostMapping(path = "add")
    public void acceptCustomTextType(@RequestBody MyCustomClass customObject) {
        System.out.println(customObject.getFieldOne());
        System.out.println(customObject.getFieldTwo());
        System.out.println(customObject.getFieldThree());
    }

    @PostMapping(path = "addPipe", consumes = "text/pipe")
    public void acceptCustomPipeType(@RequestBody MyCustomClass customObject) {
        System.out.println(customObject.getFieldOne());
        System.out.println(customObject.getFieldTwo());
        System.out.println(customObject.getFieldThree());
    }

    @GetMapping(path = "getPipe", produces = "text/pipe")
    public ResponseEntity<MyCustomClass> getCustomObject() {
        MyCustomClass customObject = new MyCustomClass("Field1", "Field2", "Field3");
        return ResponseEntity.ok(customObject);
    }
}
