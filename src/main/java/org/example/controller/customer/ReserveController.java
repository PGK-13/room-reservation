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
 * 一个用户预定会议室的控件
 *
 * @author zklee
 * date 2025/4/28
 */
@RestController
@RequestMapping("/api/cus")
public class ReserveController {

    @Autowired
    private CustomerService customerService;


    /**
     * 获取会议室的list
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/rooms")
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listMeetingRoom(pageNo, pageSize);
    }

    /**
     * 查询有哪些可以使用的会议室（条件查询）
     * @param meetingRoomDTO
     * @return
     */
    @PostMapping("/search")
    public Result<PageResult<MeetingRoomVO>> searchAvailable(@RequestBody MeetingRoomDTO meetingRoomDTO) {
        return customerService.searchAvailable(meetingRoomDTO);
    }

    /**
     * 一个预定的按钮
     * @param reservationDTO
     * @return
     */
    @PostMapping("/reservations")
    public Result placeOrder(@RequestBody ReservationDTO reservationDTO) {
        return customerService.placeOrder(reservationDTO);
    }

    /**
     * 查询预订的订单列表（全部）
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/orders")
    public Result<PageResult<ReservationVO>> listReservation(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listReservation(pageNo, pageSize);
    }

    /**
     * 查询哪些订单未支付
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/ordersUnpay")
    public Result<PageResult<ReservationVO>> listReservationUnpay(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listReservationUnpay(pageNo, pageSize);
    }

    /**
     * 支付按钮
     * @param id
     * @return
     */
    @PostMapping("/pay/{id}")
    public Result pay(@PathVariable Long id) {
        return customerService.pay(id);
    }

    /**
     * 查询已经完成的订单列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/ordersFinished")
    public Result<PageResult<ReservationVO>> listFinishedOrder(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listFinishedOrder(pageNo, pageSize);
    }
}
