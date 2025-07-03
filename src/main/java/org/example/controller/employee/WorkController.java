package org.example.controller.employee;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.employee.dto.MaintenanceDTO;
import org.example.entity.employee.dto.ResultDTO;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author zklee
 * date 2025/4/29
 */
@RestController
@RequestMapping("/api/employee")
public class WorkController {

    @Autowired
    private EmployeeService employeeService;

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
        return employeeService.listMeetingRoom(pageNo, pageSize);
    }

    /**
     * 更新会议室维修状态
     * @param maintenanceDTO
     * @return
     */
    @PutMapping("/updaterooms")
    public Result setMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {
        return employeeService.setMaintenance(maintenanceDTO);
    }

    /**
     * 获取取消的订单
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/cancelOrder")
    public Result<PageResult<CancelOrderVO>> listCanceledOrder(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return employeeService.listCanceledOrder(pageNo, pageSize);
    }

    /**
     * 是否同意用户取消订单
     * @param resultDTO
     * @return
     */
    @PutMapping("/updateCanceledOrder")
    public Result giveResult(@RequestBody ResultDTO resultDTO) {
        return employeeService.giveResult(resultDTO);
    }
}
