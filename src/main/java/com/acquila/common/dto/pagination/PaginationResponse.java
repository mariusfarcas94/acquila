package com.acquila.common.dto.pagination;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic pagination response.
 *
 * @param <T> type of the list of elements that are returned.
 */
@Getter
@Setter
public class PaginationResponse<T> {

    private long totalElements;

    private int totalPages;

    private int pageNumber;

    private List<T> elements = new ArrayList<>();

}
