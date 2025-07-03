package org.example.controller.admin;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.admin.dto.CreateRoomDTO;
import org.example.entity.admin.dto.UpdateRoomDTO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zklee
 * date 2025/4/29
 */
@RestController
@RequestMapping("/api/admin")
public class RoomManageController {

    @Autowired
    private AdminService adminService;

    /**
     * 获取会议室列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/rooms")
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return adminService.listMeetingRoom(pageNo, pageSize);
    }

    /**
     * 添加会议室
     * @param createRoomDTO
     * @return
     */
    @PostMapping("/addrooms")
    public Result createMeetingRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        return adminService.createMeetingRoom(createRoomDTO);
    }

    /**
     * 通过id拿到会议室详细信息
     * @param id
     * @return
     */
    @GetMapping("/roomsById/{id}")
    public Result<MeetingRoomVO> getMeetingRoomById(@PathVariable Long id) {
        return adminService.getMeetingRoomById(id);
    }

    /**
     * 更新某个会议室的信息
     * @param updateRoomDTO
     * @return
     */
    @PutMapping("/updaterooms")
    public Result updateMeetingRoom(@RequestBody UpdateRoomDTO updateRoomDTO) {
        return adminService.updateMeetingRoom(updateRoomDTO);
    }

    /**
     * 批量删除会议室
     * @param ids
     * @return
     */
    @DeleteMapping("/deleterooms")
    public Result deleteMeetingRoom(
            @RequestParam List<Long> ids
    ) {
        return adminService.deleteMeetingRoom(ids);
    }
}
