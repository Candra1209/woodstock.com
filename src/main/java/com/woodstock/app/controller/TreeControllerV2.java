package com.woodstock.app.controller;

import com.woodstock.app.models.params.PageParams;
import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.models.response.PagingResponse;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.tree.TreeResponseV2;
import com.woodstock.app.service.impelment.TreeServiceImplV2;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RouteAppConstant.BASE_V2 + RouteAppConstant.TREE)
public class TreeControllerV2 {

    private final TreeServiceImplV2 treeServiceImpl;


    public TreeControllerV2(TreeServiceImplV2 treeServiceImpl) {
        this.treeServiceImpl = treeServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<?>> getAllTree(@ModelAttribute PageParams pageParams) {

        Pageable pageable = PageRequest.of(pageParams.getPage() - 1, pageParams.getSize());

        PagingResponse<TreeResponseV2> response = treeServiceImpl.getDataPagging(pageable);

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success get all tree")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNewTree(@RequestBody TreeRequest request){

        TreeResponseV2 response = treeServiceImpl.addNewTree(request);

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.CREATED)
                .message("success created new tree")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteTree(@PathVariable String id){


        HttpStatus status = HttpStatus.OK;

        TreeResponseV2 response = treeServiceImpl.deleteTree(id);

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(status)
                .message("success created new tree")
                .data(response)
                .build();

        return ResponseEntity.status(status).body(result);

    }

}
