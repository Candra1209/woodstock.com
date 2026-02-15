package com.woodstock.app.utils.tool;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
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
