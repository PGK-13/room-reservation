package org.example.service;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.employee.dto.MaintenanceDTO;
import org.example.entity.employee.dto.ResultDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zklee
 * date 2025/4/29
 */
public interface EmployeeService {
    Result<PageResult<MeetingRoomVO>> listMeetingRoom(Integer pageNo, Integer pageSize);
    Result setMaintenance(@RequestBody MaintenanceDTO maintenanceDTO);

    Result<PageResult<CancelOrderVO>> listCanceledOrder(Integer pageNo, Integer pageSize);
    Result giveResult(@RequestBody ResultDTO resultDTO);
}
