package com.acquila.common.dto.pagination.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.acquila.common.dto.pagination.PaginationRequest;
import com.acquila.common.dto.pagination.PaginationResponse;

/**
 * Mapper for pagination related objects.
 */
public class PaginationMapper {

    /**
     * Convert a pagination request to a JPA PageRequest.
     *
     * @param paginationRequest the requested pagination details.
     * @return the page request.
     */
    public static PageRequest toPageRequest(PaginationRequest paginationRequest) {
        return new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize(), Sort.Direction.DESC, "created");
    }

    /**
     * Convert a page of entities to a pagination response.
     *
     * @param page            - the retrieved page of entities.
     * @param mappingFunction - the mapping function.
     * @param <E>             - the entity type.
     * @param <D>             - the data transfer object type.
     * @return
     */
    public static <E, D> PaginationResponse<D> buildPaginationResponseDto(Page<E> page, Function<E, D> mappingFunction) {
        PaginationResponse<D> responseDto = new PaginationResponse<>();
        responseDto.setTotalElements(page.getTotalElements());
        responseDto.setTotalPages(page.getTotalPages());
        responseDto.setPageNumber(page.getNumber());
        responseDto.setElements(page.getContent().stream()
                .map(mappingFunction)
                .collect(Collectors.toList()));

        return responseDto;
    }

    /**
     * Build a pagination request given the page size and the page number.
     *
     * @param pageSize   - the requested page size.
     * @param pageNumber - the requested page number.
     * @return the pagination request.
     */
    public static PaginationRequest buildPaginationRequest(final int pageSize, final int pageNumber) {
        return new PaginationRequest(pageSize, pageNumber);
    }
}
