package org.example.service;

import org.example.entity.Result;
import org.example.entity.user.dto.UserLoginDTO;
import org.example.entity.user.dto.UserRegisterDTO;
import org.example.entity.user.vo.UserLoginVO;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
public interface UserService {
    Result<UserLoginVO> login(UserLoginDTO userLoginDTO);
    Result register(UserRegisterDTO userRegisterDTO);
}
