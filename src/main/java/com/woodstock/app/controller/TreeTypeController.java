package com.woodstock.app.controller;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.TreeTypeResponse;
import com.woodstock.app.service.TreeTypeService;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import javax.swing.text.html.parser.Entity;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(RouteAppConstant.TREE_TYPE)
public class TreeTypeController {

    @Autowired
    private TreeTypeService service;

    @GetMapping("/active")
    private PagedModel<EntityModel<TreeTypeResponse>> getAllTreeType(@RequestParam  Map<String, String> map, PagedResourcesAssembler<TreeTypeResponse> assembler){

        int size = Integer.parseInt(map.getOrDefault("size", "5"));
        int page = Integer.parseInt(map.getOrDefault("page", "0"));

        Pageable pageable = PageRequest.of(page,size);

        Page<TreeTypeResponse> paged = service.getAll(pageable);

        return  assembler.toModel(paged);
    }

    @GetMapping("")
    private PagedModel<EntityModel<TreeTypeResponse>> getAllTreeTypeRobust(@RequestParam  Map<String, String> map, PagedResourcesAssembler<TreeTypeResponse> assembler){

        int size = Integer.parseInt(map.getOrDefault("size", "5"));
        int page = Integer.parseInt(map.getOrDefault("page", "0"));
        String status = map.getOrDefault("status", "active");

        Pageable pageable = PageRequest.of(page,size);

        if (status.equals("active")){
            Page<TreeTypeResponse> paged = service.getAll(pageable);
            return  assembler.toModel(paged);
        } else if (status.equals("deleted")) {
            Page<TreeTypeResponse> paged = service.getAllDeleted(pageable);
            return  assembler.toModel(paged);
        } else if (status.equals("all")) {
            Page<TreeTypeResponse> paged = service.getAllWithDeleted(pageable);
            return  assembler.toModel(paged);
        }else {
            throw new RuntimeException("BAD REQUEST : invalid status type");
        }
    }

    @GetMapping("/all")
    private PagedModel<EntityModel<TreeTypeResponse>> getAllTreeTypeWithDeleted(@RequestParam  Map<String, String> map, PagedResourcesAssembler<TreeTypeResponse> assembler){

        int size = Integer.parseInt(map.getOrDefault("size", "5"));
        int page = Integer.parseInt(map.getOrDefault("page", "0"));

        Pageable pageable = PageRequest.of(page,size);

        Page<TreeTypeResponse> paged = service.getAllWithDeleted(pageable);

        return  assembler.toModel(paged);

    }
    @GetMapping("/trash")
    private PagedModel<EntityModel<TreeTypeResponse>> getAllDeleted(@RequestParam  Map<String, String> map, PagedResourcesAssembler<TreeTypeResponse> assembler){

        int size = Integer.parseInt(map.getOrDefault("size", "5"));
        int page = Integer.parseInt(map.getOrDefault("page", "0"));

        Pageable pageable = PageRequest.of(page,size);

        Page<TreeTypeResponse> paged = service.getAllDeleted(pageable);

        return  assembler.toModel(paged);

    }

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

    @DeleteMapping("/{id}")
    private SuccessResponse<Object> deleteTreeTypeByID(@PathVariable(name = "id") String target){

        TreeTypeResponse data = service.deleteById(UUID.fromString(target));

        return SuccessResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("success deleted tree type")
                .data(data)
                .build();

    }

    @PatchMapping("/{id}/recover")
    private SuccessResponse<Object> recoverTreeType(@PathVariable String id){
        service.recover(UUID.fromString(id));
        return SuccessResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("Recovered tree type with ID : " + id)
                .build();
    }

    @PutMapping("/{id}/update")
    private SuccessResponse<Object> updateTreeType(@PathVariable String id, @RequestBody TreeTypeRequest request){

        TreeTypeResponse data = service.update(request, UUID.fromString(id));
        return SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success updated tree type")
                .data(data)
                .build();
    }

}
