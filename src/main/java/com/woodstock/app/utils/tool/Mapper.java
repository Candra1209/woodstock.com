package com.woodstock.app.utils.tool;

import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.roles.RolesRequest;
import com.woodstock.app.models.request.tree_type.TreeTypeRequest;
import com.woodstock.app.models.response.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class Mapper<T> {

    public TreeType toEntity(TreeTypeRequest request){
        return TreeType.builder()
                .name(request.getName())
                .typeCode(request.getTypeCode())
                .build();
    }

    public Roles toEntity(RolesRequest request){
        return Roles.builder()
                .name(RoleEnum.valueOf(request.getName()))
                .build();
    }

    public PagingResponse<T> mappedToResponse(Page<T> paging){

        return PagingResponse.<T>builder()
                .content(paging.getContent())
                .page(paging.getNumber() + 1)
                .size(paging.getSize())
                .totalPage(paging.getTotalPages())
                .totalData(paging.getTotalElements())
                .build();
    }

}
