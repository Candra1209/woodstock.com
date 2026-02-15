package com.woodstock.app.controller;

import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import com.woodstock.app.service.impelment.TreeTypeService;
import com.woodstock.app.utils.constants.RouteAppConstant;
import com.woodstock.app.utils.exception.InvalidQueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1"+RouteAppConstant.TREE_TYPE)
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

        switch (status) {
            case "active" -> {
                Page<TreeTypeResponse> paged = service.getAll(pageable);
                return assembler.toModel(paged);
            }
            case "deleted" -> {
                Page<TreeTypeResponse> paged = service.getAllDeleted(pageable);
                return assembler.toModel(paged);
            }
            case "all" -> {
                Page<TreeTypeResponse> paged = service.getAllWithDeleted(pageable);
                return assembler.toModel(paged);
            }
            default -> throw new InvalidQueryParameter("invalid status type for get all tree type : use either 'active', 'deleted', or 'all'");
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

    @DeleteMapping("/delete/{id}")
    private SuccessResponse<Object> deleteTreeTypeByID(@PathVariable(name = "id") String target){

        TreeTypeResponse data = service.deleteById(UUID.fromString(target));

        return SuccessResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("success deleted tree type")
                .data(data)
                .build();

    }

    @PatchMapping("/recover/{id}")
    private SuccessResponse<Object> recoverTreeType(@PathVariable String id){

        service.recover(UUID.fromString(id));

        return SuccessResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("Recovered tree type with ID : " + id)
                .build();
    }

    @PutMapping("/update/{id}")
    private SuccessResponse<Object> updateTreeType(@PathVariable String id, @RequestBody TreeTypeRequest request){

        TreeTypeResponse data = service.update(request, UUID.fromString(id));
        return SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success updated tree type")
                .data(data)
                .build();
    }

}
