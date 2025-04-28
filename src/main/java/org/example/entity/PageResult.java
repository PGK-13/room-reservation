package org.example.entity;

import lombok.Data;
import java.util.List;

/**
 * 通用分页响应
 */
@Data
public class PageResult<T> {

    private Long total;    // 总条数
    private Long pages;    // 总页数
    private Long pageNo;   // 当前页
    private Long pageSize; // 每页条数
    private List<T> list;  // 当前页的数据列表

    public PageResult(Long total, Long pages, Long pageNo, Long pageSize, List<T> list) {
        this.total = total;
        this.pages = pages;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list = list;
    }
}
