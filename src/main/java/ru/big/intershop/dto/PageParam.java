package ru.big.intershop.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;
import ru.big.intershop.enums.Sorting;

@Data
public class PageParam {
    private String search = "";
    private int page = 0;
    private int maxPages;
    private int size = 10;
    private Sorting sorting = Sorting.ID;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private int[] pageSizes = new int[]{3, 10, 20, 50, 100};
}
