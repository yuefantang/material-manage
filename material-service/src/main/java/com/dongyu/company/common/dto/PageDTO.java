package com.dongyu.company.common.dto;

import com.dongyu.company.mould.domain.PurchaseMould;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页返回工具类DTO
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
public final class PageDTO<V> {
    private final long total;
    private final int totalPages;
    private final List<V> datas;
    private final int pageNo;
    private final int pageSize;

    private PageDTO(long total, int totalPages, int pageNo, int pageSize, List<V> datas) {
        this.total = total;
        this.totalPages = totalPages;
        this.datas = datas;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static void of(Page<PurchaseMould> page, Object o) {
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<V> getDatas() {
        return datas;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public static <V> PageDTO.Builder<V> builder(List<V> datas) {
        return new PageDTO.Builder(datas);
    }

    public static <V> PageDTO<V> of(Page<V> page) {
        Objects.requireNonNull(page, "page is null");
        return new PageDTO(page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize(), (List) Optional.ofNullable(page.getContent()).orElse(Collections.emptyList()));
    }

    public static <T, V> PageDTO<V> of(Page<T> page, Function<T, V> converter) {
        Objects.requireNonNull(page, "page is null");
        Objects.requireNonNull(converter, "converter is null");
        return new PageDTO(page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize(), (List) Optional.ofNullable(page.getContent()).map((content) -> {
            return (List) content.stream().map(converter).collect(Collectors.toList());
        }).orElse(Collections.emptyList()));
    }

    public static <T, V> PageDTO<V> of(PageDTO<T> page, Function<T, V> converter) {
        Objects.requireNonNull(page, "page is null");
        Objects.requireNonNull(converter, "converter is null");
        return new PageDTO(page.getTotal(), page.getTotalPages(), page.getPageNo(), page.getPageSize(), (List) Optional.ofNullable(page.getDatas()).map((content) -> {
            return (List) content.stream().map(converter).collect(Collectors.toList());
        }).orElse(Collections.emptyList()));
    }

    public static class Builder<V> {
        private long totalRecords;
        private int totalPages;
        private List<V> datas;
        private int pageNo;
        private int pageSize;

        private Builder(List<V> datas) {
            this.totalPages = 1;
            this.pageNo = 1;
            Objects.requireNonNull(datas, "datas is null");
            this.datas = datas;
            this.totalRecords = datas.size();
            this.pageSize = datas.size();

        }

        public final PageDTO.Builder<V> totalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
            return this;
        }

        public final PageDTO.Builder<V> totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public final PageDTO.Builder<V> pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public final PageDTO.Builder<V> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public final PageDTO<V> build() {
            return new PageDTO(this.totalRecords, this.totalPages, this.pageNo, this.pageSize, this.datas);
        }


    }


}
