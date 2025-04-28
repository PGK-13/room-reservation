package org.example.controller.admin;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.admin.dto.CustomerDTO;
import org.example.entity.admin.vo.CustomerVO;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
@RestController
@RequestMapping("/api/admin")
public class PersonController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public Result<PageResult<CustomerVO>> getCustomer(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return adminService.listCustomer(pageNo, pageSize, keyword);
    }

    @GetMapping("/userById/{id}")
    public Result<CustomerVO> getCustomerById(@PathVariable Integer id) {
        return adminService.getCustomerById(id);
    }

    @PutMapping("/updateuser")
    public Result updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return adminService.updateCustomer(customerDTO);
    }

    @GetMapping("/usersReviewed")
    public Result<PageResult<CustomerVO>> getCustomerReviewed(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return adminService.listCustomerReviewed(pageNo, pageSize, keyword);
    }

    @PatchMapping("/approve/{id}")
    public Result approve(@PathVariable Integer id) {
        return adminService.approve(id);
    }

    //审核不通过
    //重置密码
}
