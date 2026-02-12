package com.woodstock.app.utils.tool;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TreeType toEntity(TreeTypeRequest request){
        return TreeType.builder()
                .name(request.getName())
                .typeCode(request.getTypeCode())
                .build();
    }

}
