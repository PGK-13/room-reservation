package org.example.controller.customer;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.dto.MeetingRoomDTO;
import org.example.entity.customer.dto.ReservationDTO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.customer.vo.ReservationVO;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zklee
 * date 2025/4/28
 */
@RestController
@RequestMapping("/api/cus")
public class ReserveController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/rooms")
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listMeetingRoom(pageNo, pageSize);
    }

    @PostMapping("/search")
    public Result<PageResult<MeetingRoomVO>> searchAvailable(@RequestBody MeetingRoomDTO meetingRoomDTO) {
        return customerService.searchAvailable(meetingRoomDTO);
    }

    @PostMapping("/reservations")
    public Result placeOrder(@RequestBody ReservationDTO reservationDTO) {
        return customerService.placeOrder(reservationDTO);
    }

    @GetMapping("/orders")
    public Result<PageResult<ReservationVO>> listReservation(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listReservation(pageNo, pageSize);
    }

    @GetMapping("/ordersUnpay")
    public Result<PageResult<ReservationVO>> listReservationUnpay(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listReservationUnpay(pageNo, pageSize);
    }

    @PostMapping("/pay/{id}")
    public Result pay(@PathVariable Long id) {
        return customerService.pay(id);
    }

    public Result<PageResult<ReservationVO>> listFinishedOrder(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listFinishedOrder(pageNo, pageSize);
    }
}
