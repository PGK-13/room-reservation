package org.example.service;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.admin.dto.CustomerDTO;
import org.example.entity.admin.vo.CustomerVO;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public interface AdminService {
    Result<PageResult<CustomerVO>> listCustomer(Integer pageNo, Integer pageSize, String keyword);
    Result<CustomerVO> getCustomerById(Integer id);
    Result updateCustomer(CustomerDTO customerDTO);
    Result<PageResult<CustomerVO>> listCustomerReviewed(Integer pageNo, Integer pageSize, String keyword);
    Result approve(Integer id);
}
