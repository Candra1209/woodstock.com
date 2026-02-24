package com.woodstock.app.controller;

import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.models.response.tree.TreeMinResponse;
import com.woodstock.app.service.impelment.TreeServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteAppConstant.BASE_V1 + RouteAppConstant.TREE)
public class TreeController {

    private final TreeServiceImpl treeService;

    @Autowired
    public TreeController(TreeServiceImpl treeService) {
        this.treeService = treeService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TreeMinResponse addNewTree(@RequestBody TreeRequest request){

        return  treeService.saveNewTree(request).toMinResponse();

    }

}
