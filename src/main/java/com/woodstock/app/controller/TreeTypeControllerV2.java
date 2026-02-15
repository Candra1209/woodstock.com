package com.woodstock.app.controller;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.params.PageParams;
import com.woodstock.app.models.params.SortParams;
import com.woodstock.app.models.params.TreeTypeSearch;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.PagingResponse;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import com.woodstock.app.service.impelment.TreeTypeServiceV2;
import com.woodstock.app.specification.TreeTypeSpecification;
import com.woodstock.app.utils.constants.RouteAppConstant;
import com.woodstock.app.utils.tool.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RestController
@RequestMapping("/v2"+RouteAppConstant.TREE_TYPE)
public class TreeTypeControllerV2 {

    private final TreeTypeServiceV2 treeTypeServiceV2;
    private final Mapper<TreeTypeResponse> mapper;

    @Autowired
    public TreeTypeControllerV2(TreeTypeServiceV2 treeTypeServiceV2, Mapper<TreeTypeResponse> mapper) {
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
    public ResponseEntity<PagingResponse<TreeTypeResponse>> getAllTreeType(
            @ModelAttribute PageParams pageParams,
            @ModelAttribute SortParams sortParams,
            @ModelAttribute TreeTypeSearch search
            ) {


        int pageConfig = Math.max(pageParams.getPage() - 1, 0);

        Sort sort = Sort.by(Sort.Direction.fromString(sortParams.getOrder()), sortParams.getFilter());

        Pageable pageable = PageRequest.of(pageConfig, pageParams.getSize(), sort);

        Specification<TreeType> specification = TreeTypeSpecification.getSpecification(search);

        log.trace("Get All tree-type");
        Page<TreeType> result = treeTypeServiceV2.getPagging(pageable, specification);

        return ResponseEntity.ok(mapper.mappedToResponse(result.map(TreeType::toResponse)));
    }
}
