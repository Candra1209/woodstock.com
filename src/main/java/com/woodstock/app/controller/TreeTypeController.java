package com.woodstock.app.controller;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.TreeTypeResponse;
import com.woodstock.app.service.TreeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tree-type")
public class TreeTypeController {

    @Autowired
    private TreeTypeService service;

    @GetMapping("/{id}")
    private SuccessResponse<Object> getTreeTypeById(@PathVariable String id){

        TreeTypeResponse data = service.getById(UUID.fromString(id));

        return SuccessResponse.builder()
                .status(HttpStatus.FOUND)
                .message("tree type has been found")
                .data(data)
                .build();

    }

    @PostMapping("")
    private SuccessResponse<Object> addNewTreeType(@RequestBody TreeTypeRequest request){

        TreeTypeResponse data = service.create(request);

        return SuccessResponse.builder()
                .status(HttpStatus.CREATED)
                .message("success created new tree type")
                .data(data)
                .build();
    }

}
