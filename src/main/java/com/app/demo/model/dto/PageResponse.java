package com.app.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PageResponse<T> {

    private List<T> content;

    private PaginationDTO pagination;

    public PageResponse(Page<?> page) {
        this.pagination = new PaginationDTO(page);
    }
}