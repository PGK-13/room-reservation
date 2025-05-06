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

    @GetMapping("/rooms")
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return employeeService.listMeetingRoom(pageNo, pageSize);
    }

    @PutMapping("/updaterooms")
    public Result setMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {
        return employeeService.setMaintenance(maintenanceDTO);
    }

    @GetMapping("/cancelOrder")
    public Result<PageResult<CancelOrderVO>> listCanceledOrder(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return employeeService.listCanceledOrder(pageNo, pageSize);
    }

    @PutMapping("/updateCanceledOrder")
    public Result giveResult(@RequestBody ResultDTO resultDTO) {
        return employeeService.giveResult(resultDTO);
    }
}
