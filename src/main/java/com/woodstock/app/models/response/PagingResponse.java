package com.woodstock.app.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PagingResponse<T> {

    List<T> content;
    Integer page;
    Integer size;

    @JsonProperty("total_page")
    Integer totalPage;
    @JsonProperty("total_data")
    Long totalData;

}
