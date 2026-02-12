package com.woodstock.app.controller;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import com.woodstock.app.service.impelment.TreeTypeServiceV2;
import com.woodstock.app.utils.constants.RouteAppConstant;
import com.woodstock.app.utils.tool.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/v2"+RouteAppConstant.TREE_TYPE)
public class TreeTypeControllerV2 {

    private final TreeTypeServiceV2 treeTypeServiceV2;
    private final Mapper mapper;

    @Autowired
    public TreeTypeControllerV2(TreeTypeServiceV2 treeTypeServiceV2, Mapper mapper) {
        this.treeTypeServiceV2 = treeTypeServiceV2;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    public ResponseEntity<TreeTypeResponse> newTreeType(@RequestBody TreeTypeRequest request){

        ObjectMapper objectMapper = new ObjectMapper();

        TreeType treeType = mapper.toEntity(request);
        TreeType result = treeTypeServiceV2.save(treeType);

        return ResponseEntity.ok(result.toResponse());
    }

    @GetMapping("/all")
    public ResponseEntity<List<TreeTypeResponse>> getAllTreeType(){

        List<TreeTypeResponse> list = treeTypeServiceV2.findAll().stream()
                .map(TreeType::toResponse)
                .toList();

        return ResponseEntity.ok(list);
    }
}
