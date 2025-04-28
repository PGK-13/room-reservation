package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.pojo.User;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/27
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
