package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.admin.dto.CustomerDTO;
import org.example.entity.admin.vo.CustomerVO;
import org.example.entity.pojo.User;
import org.example.exception.StatusException;
import org.example.exception.UserExistedException;
import org.example.exception.UserNotExistedException;
import org.example.mapper.UserMapper;
import org.example.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zklee
 * @date 2025/04/28
 */
@Service
public class AdminServiceImpl implements AdminService {
    //TODO 查询用户接口 和 未审核用户接口 有相似性 考虑抽离
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<PageResult<CustomerVO>> listCustomer(Integer pageNo, Integer pageSize, String keyword) {
        Page<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getRole, "customer");
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.like(User::getName, keyword)
                    .or()
                    .like(User::getCompany, keyword);
        }

        Page<User> resultPage = userMapper.selectPage(page, queryWrapper);

        // 【重点】转成 CustomerVO 列表
        List<CustomerVO> voList = resultPage.getRecords().stream().map(user -> {
            CustomerVO vo = new CustomerVO();
            vo.setId(user.getId());
            vo.setName(user.getName());
            vo.setUsername(user.getUsername());
            vo.setPhone(user.getPhone());
            vo.setCompany(user.getCompany());
            vo.setStatus(user.getStatus());
            vo.setAuditReason(user.getAuditReason());
            vo.setBalance(user.getBalance());
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<CustomerVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result<CustomerVO> getCustomerById(Integer id) {
        User user = userMapper.selectById(id);
        CustomerVO vo = new CustomerVO();
        BeanUtils.copyProperties(user, vo);
        return Result.success(vo);
    }

    @Override
    public Result updateCustomer(CustomerDTO customerDTO) {
        User user = userMapper.selectById(customerDTO.getId());
        BeanUtils.copyProperties(customerDTO, user);
        user.setAuditReason(customerDTO.getAuditReason());
        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result<PageResult<CustomerVO>> listCustomerReviewed(Integer pageNo, Integer pageSize, String keyword) {
        Page<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getRole, "customer")
                .eq(User::getStatus, 1);
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.like(User::getName, keyword)
                    .or()
                    .like(User::getCompany, keyword);
        }

        Page<User> resultPage = userMapper.selectPage(page, queryWrapper);

        // 【重点】转成 CustomerVO 列表
        List<CustomerVO> voList = resultPage.getRecords().stream().map(user -> {
            CustomerVO vo = new CustomerVO();
            vo.setId(user.getId());
            vo.setName(user.getName());
            vo.setUsername(user.getUsername());
            vo.setPhone(user.getPhone());
            vo.setCompany(user.getCompany());
            vo.setStatus(user.getStatus());
            vo.setAuditReason(user.getAuditReason());
            vo.setBalance(user.getBalance());
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<CustomerVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result approve(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null || !"customer".equals(user.getRole())) {
            throw new UserNotExistedException("客户不存在或角色不正确");
        }
        if (user.getStatus() != 1) {
            throw new StatusException("客户不是待审核状态");
        }
        user.setStatus(0); // 审核通过，改成正常
        userMapper.updateById(user);
        return Result.success();
    }
}
