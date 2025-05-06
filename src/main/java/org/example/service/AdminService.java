package org.example.service;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.admin.dto.CreateRoomDTO;
import org.example.entity.admin.dto.CustomerDTO;
import org.example.entity.admin.dto.UpdateRoomDTO;
import org.example.entity.admin.vo.CustomerVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @param
 * @author zklee
 * @return
 * @date 2025/04/28
 */
public interface AdminService {
    Result<PageResult<MeetingRoomVO>> listMeetingRoom(Integer pageNo, Integer pageSize);
    Result createMeetingRoom(CreateRoomDTO createRoomDTO);
    Result<MeetingRoomVO> getMeetingRoomById(Long id);
    Result updateMeetingRoom(UpdateRoomDTO updateRoomDTO);
    Result deleteMeetingRoom(List<Long> ids);

    Result<PageResult<CustomerVO>> listCustomer(Integer pageNo, Integer pageSize, String keyword);
    Result<CustomerVO> getCustomerById(Integer id);
    Result updateCustomer(CustomerDTO customerDTO);
    Result<PageResult<CustomerVO>> listCustomerReviewed(Integer pageNo, Integer pageSize, String keyword);
    Result approve(Integer id);
}
