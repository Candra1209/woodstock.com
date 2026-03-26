package com.woodstock.app.controller;
import com.woodstock.app.entity.Tree;
import com.woodstock.app.models.params.PageParams;
import com.woodstock.app.models.params.SortParams;
import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.models.response.PagingResponse;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.tree.TreeResponse;
import com.woodstock.app.service.impelment.TreeServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<SuccessResponse<TreeResponse>> addNewTree(@RequestBody TreeRequest request, Authentication authentication){

        String username = authentication.getName();

        TreeResponse response = treeService.saveNewTree(request, username).toTreeResponse();

        SuccessResponse<TreeResponse> result = SuccessResponse.<TreeResponse>builder()
                .status(HttpStatus.CREATED)
                .message("success created new tree")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<PagingResponse<TreeResponse>>> getAllTree(
            @ModelAttribute PageParams pageParams,
            @ModelAttribute SortParams sortParams
    ) {

        int pageConfig = Math.max(pageParams.getPage()-1, 0);

        Sort sort = Sort.by(Sort.Direction.fromString(sortParams.getOrder()), sortParams.getFilter());

        Pageable pageable = PageRequest.of(pageConfig, pageParams.getSize(), sort);

        Page<TreeResponse> page = treeService.getPagging(pageable).map(Tree::toTreeResponse);

        PagingResponse<TreeResponse> paging = PagingResponse.<TreeResponse>builder()
                .content(page.getContent())
                .page(pageParams.getPage())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .totalData(page.getTotalElements())
                .build();

        SuccessResponse<PagingResponse<TreeResponse>> result = SuccessResponse.<PagingResponse<TreeResponse>>builder()
                .status(HttpStatus.OK)
                .message("success get all tree information from database")
                .data(paging)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<TreeResponse>> getTree(@PathVariable String id) {

        UUID targetId = UUID.fromString(id);

        SuccessResponse<TreeResponse> result = SuccessResponse.<TreeResponse>builder()
                .status(HttpStatus.OK)
                .message("success find tree with id : " + id)
                .data(treeService.findbyId(targetId).toTreeResponse())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<TreeResponse>> deleteTree(@PathVariable String id, Authentication authentication){

        String username = authentication.getName();

        TreeResponse target = treeService.deleteTree(UUID.fromString(id), username).toTreeResponse();

        SuccessResponse<TreeResponse> result = SuccessResponse.<TreeResponse>builder()
                .status(HttpStatus.OK)
                .message("success deleted tree with id : " + id)
                .data(target)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<TreeResponse>> updatedTree(@PathVariable String id, @RequestBody TreeRequest request, Authentication authentication) {

        UUID treeId = UUID.fromString(id);
        String username = authentication.getName();

        TreeResponse response = treeService.updateTree(treeId,request,username).toTreeResponse();

        SuccessResponse<TreeResponse> result = SuccessResponse.<TreeResponse>builder()
                .status(HttpStatus.OK)
                .message("success success tree with id : " + id)
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
