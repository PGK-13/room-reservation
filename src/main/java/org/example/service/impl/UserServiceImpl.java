package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.Result;
import org.example.entity.pojo.User;
import org.example.entity.user.dto.UserLoginDTO;
import org.example.entity.user.dto.UserRegisterDTO;
import org.example.entity.user.vo.UserLoginVO;
import org.example.exception.*;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        User existUser = findByUserName(username);
        if(existUser == null) {
            throw new UserNotExistedException();
        }

        if (!MD5Util.encrypt(userLoginDTO.getPassword()).equals(existUser.getPassword())) {
            throw new PasswordErrorException();
        }

        if (!userLoginDTO.getRole().equals(existUser.getRole())) {
            throw new RoleMatchedException();
        }

        if (existUser.getStatus() == 1) {
            throw new UserNotReviewedException();
        }

        if (existUser.getStatus() == 2) {
            throw new FrozenedException();
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", existUser.getId());
        String token = JwtUtil.genToken(claims);

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setToken(token);
        userLoginVO.setUserId(existUser.getId());
        return Result.success(userLoginVO);
    }

    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        User existUser = findByUserName(username);
        if(existUser != null) {
            throw new UserExistedException();
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);

        user.setPassword(MD5Util.encrypt(user.getPassword()));
        user.setStatus(1); // 设置为审核状态
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setBalance(10); // 注册送10元

        userMapper.insert(user);

        return Result.success();
    }

    private User findByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
