package org.example.controller.customer;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.dto.CancelReasonDTO;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.ReservationVO;
import org.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zklee
 * date 2025/4/29
 */
@RestController
@RequestMapping("/api/cus")
public class CancelReserveController {

    @Autowired
    private CustomerService customerService;

    /**
     * 获取可以取消的订单，有取消按钮（已支付 并且在预定状态）
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/ordersCancanceled")
    public Result<PageResult<ReservationVO>> listCanCanceled(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listCanCanceled(pageNo, pageSize);
    }


    @PostMapping("/cancelApplications")
    public Result apply(@RequestBody CancelReasonDTO cancelReasonDTO) {
        return customerService.apply(cancelReasonDTO);
    }

    /**
     * 获取用户取消的订单，或者因为超时未支付的订单
     * TODO 要改成展示一些用户信息，或者订单具体信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/ordersCanceled")
    public Result<PageResult<CancelOrderVO>> listCanceledOrder(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return customerService.listCanceledOrder(pageNo, pageSize);
    }
}
