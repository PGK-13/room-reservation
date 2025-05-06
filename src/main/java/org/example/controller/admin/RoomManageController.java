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

    @GetMapping("/rooms")
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return adminService.listMeetingRoom(pageNo, pageSize);
    }

    @PostMapping("/addrooms")
    public Result createMeetingRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        return adminService.createMeetingRoom(createRoomDTO);
    }

    @GetMapping("/roomsById/{id}")
    public Result<MeetingRoomVO> getMeetingRoomById(@PathVariable Long id) {
        return adminService.getMeetingRoomById(id);
    }

    @PutMapping("/updaterooms")
    public Result updateMeetingRoom(@RequestBody UpdateRoomDTO updateRoomDTO) {
        return adminService.updateMeetingRoom(updateRoomDTO);
    }

    @DeleteMapping("/deleterooms")
    public Result deleteMeetingRoom(
            @RequestParam List<Long> ids
    ) {
        return adminService.deleteMeetingRoom(ids);
    }
}
