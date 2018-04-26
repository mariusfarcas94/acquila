package com.acquila.common.dto.pagination;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic request dto for pagination.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    @NotNull
    @Min(1)
    private Integer pageSize;

    @NotNull
    @Min(0)
    private Integer pageNumber;

    @Override
    public String toString() {
        return "PaginationRequest{" +
                "pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
