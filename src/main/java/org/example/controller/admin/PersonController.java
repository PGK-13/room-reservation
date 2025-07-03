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

    /**
     * 获取用户列表
     * @param pageNo
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/users")
    public Result<PageResult<CustomerVO>> getCustomer(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return adminService.listCustomer(pageNo, pageSize, keyword);
    }

    /**
     * 获取id用户的详细信息
     * @param id
     * @return
     */
    @GetMapping("/userById/{id}")
    public Result<CustomerVO> getCustomerById(@PathVariable Integer id) {
        return adminService.getCustomerById(id);
    }

    /**
     * 更新用户的详细信息
     * @param customerDTO
     * @return
     */
    @PutMapping("/updateuser")
    public Result updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return adminService.updateCustomer(customerDTO);
    }

    /**
     * 获得申请账号的客户名单
     * @param pageNo
     * @param pageSize
     * @param keyword
     * @return
     */
    @GetMapping("/usersReviewed")
    public Result<PageResult<CustomerVO>> getCustomerReviewed(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return adminService.listCustomerReviewed(pageNo, pageSize, keyword);
    }

    /**
     * 赞成按钮
     * @param id
     * @return
     */
    @PatchMapping("/approve/{id}")
    public Result approve(@PathVariable Integer id) {
        return adminService.approve(id);
    }

    //审核不通过
    //重置密码
}
