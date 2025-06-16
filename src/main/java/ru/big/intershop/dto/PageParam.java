package ru.big.intershop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageParam {
    private String search = "";
    private int page = 0;
    private int maxPages;
    private int size = 10;
    private Sorting sorting = Sorting.ID;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private int[] pageSizes = new int[]{3, 10, 20, 50, 100};

    public Pageable getPageable() {
        Sort sort = Sort.by(sortDirection, sorting.getValue());
        return PageRequest.of(page, size, sort);
    }

    public PageParam setMaxPages(int countItem) {
        this.maxPages =  (countItem % this.size == 0 ? countItem / this.size - 1 : countItem / this.size);
        return this;
    }

    @Getter
    @RequiredArgsConstructor
    enum Sorting {
        ID("id"),
        PRICE("price"),
        ALPHABETICAL("title");

        private final String value;
    }
}
