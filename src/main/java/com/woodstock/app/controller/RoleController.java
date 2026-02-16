package com.woodstock.app.controller;

import com.woodstock.app.entity.Roles;
import com.woodstock.app.models.request.roles.RolesRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.roles.RolesResponse;
import com.woodstock.app.service.impelment.RoleServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import com.woodstock.app.utils.tool.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1"+ RouteAppConstant.ROLES)
public class RoleController {

    private final RoleServiceImpl roleService;
    private final Mapper<RolesResponse> mapper;

    @Autowired
    public RoleController(RoleServiceImpl roleService, Mapper<RolesResponse> mapper) {
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @PostMapping("/add/batch")
    public ResponseEntity<SuccessResponse<List<RolesResponse>>> createNewRoleBatch(@RequestBody List<RolesRequest> requestList){

        List<Roles> roles = roleService.saveAll(requestList.stream().map(mapper::toEntity).toList());

        SuccessResponse<List<RolesResponse>> response = SuccessResponse.<List<RolesResponse>>builder()
                .status(HttpStatus.OK)
                .message("")
                .data(roles.stream().map(Roles::toResponse).toList())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<RolesResponse>> createNewRole(@RequestBody RolesRequest request) {

        SuccessResponse<RolesResponse> result = SuccessResponse.<RolesResponse>builder()
                .status(HttpStatus.OK)
                .message("success add new Role")
                .data(roleService.save(mapper.toEntity(request)).toResponse())
                .build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<RolesResponse>>> showAllRoles() {

        List<Roles> roles = roleService.findAll();

        SuccessResponse<List<RolesResponse>> response = SuccessResponse.<List<RolesResponse>>builder()
                .status(HttpStatus.OK)
                .message("success get all role")
                .data(roles.stream().map(Roles::toResponse).toList())
                .build();

        return ResponseEntity.ok(response);

    }

}
