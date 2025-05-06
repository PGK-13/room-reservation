package org.example.service;

import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.dto.CancelReasonDTO;
import org.example.entity.customer.dto.MeetingRoomDTO;
import org.example.entity.customer.dto.ReservationDTO;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.customer.vo.ReservationVO;

/**
 * @author zklee
 * date 2025/4/28
 */
public interface CustomerService {
    Result<PageResult<MeetingRoomVO>> listMeetingRoom(Integer pageNo, Integer pageSize);
    Result<PageResult<MeetingRoomVO>> searchAvailable(MeetingRoomDTO meetingRoomDTO);
    Result placeOrder(ReservationDTO reservationDTO);

    Result<PageResult<ReservationVO>> listReservation(Integer pageNo, Integer pageSize);
    Result<PageResult<ReservationVO>> listReservationUnpay(Integer pageNo, Integer pageSize);
    Result pay(Long id);
    Result<PageResult<ReservationVO>> listFinishedOrder(Integer pageNo, Integer pageSize);

    Result<PageResult<ReservationVO>> listCanCanceled(Integer pageNo, Integer pageSize);
    Result apply(CancelReasonDTO cancelReasonDTO);
    Result<PageResult<CancelOrderVO>> listCanceledOrder(Integer pageNo, Integer pageSize);
}
