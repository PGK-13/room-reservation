package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.constant.DatabaseConstant;
import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.employee.dto.MaintenanceDTO;
import org.example.entity.employee.dto.ResultDTO;
import org.example.entity.pojo.CancelApplication;
import org.example.entity.pojo.MeetingRoom;
import org.example.entity.pojo.Reservation;
import org.example.entity.pojo.User;
import org.example.mapper.CancelApplicationMapper;
import org.example.mapper.MeetingRoomMapper;
import org.example.mapper.ReservationMapper;
import org.example.mapper.UserMapper;
import org.example.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zklee
 * date 2025/4/29
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private CancelApplicationMapper cancelApplicationMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<PageResult<MeetingRoomVO>> listMeetingRoom(Integer pageNo, Integer pageSize) {
        Page<MeetingRoom> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<MeetingRoom> queryWrapper = new LambdaQueryWrapper<>();

        Page<MeetingRoom> resultPage = meetingRoomMapper.selectPage(page, queryWrapper);

        List<MeetingRoomVO> voList = resultPage.getRecords().stream().map(meetingRoom -> {
            MeetingRoomVO vo = new MeetingRoomVO();
            vo.setId(meetingRoom.getId());
            vo.setName(meetingRoom.getName());
            vo.setType(meetingRoom.getType());
            vo.setArea(meetingRoom.getArea());
            vo.setSeatCount(meetingRoom.getSeatCount());
            vo.setHasProjector(meetingRoom.getHasProjector());
            vo.setHasSound(meetingRoom.getHasSound());
            vo.setHasNetwork(meetingRoom.getHasNetwork());
            vo.setPricePerHour(meetingRoom.getPricePerHour());
            vo.setIsUnderMaintenance(meetingRoom.getIsUnderMaintenance());
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<MeetingRoomVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result setMaintenance(MaintenanceDTO maintenanceDTO) {
        MeetingRoom meetingRoom = meetingRoomMapper.selectById(maintenanceDTO.getId());
        meetingRoom.setIsUnderMaintenance(maintenanceDTO.getIsUnderMaintenance());
        meetingRoom.setUpdatedAt(LocalDateTime.now());

        meetingRoomMapper.updateById(meetingRoom);
        return Result.success();
    }

    @Override
    public Result<PageResult<CancelOrderVO>> listCanceledOrder(Integer pageNo, Integer pageSize) {
        Page<CancelApplication> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<CancelApplication> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(CancelApplication::getCreatedAt);

        Page<CancelApplication> resultPage = cancelApplicationMapper.selectPage(page, queryWrapper);

        List<CancelOrderVO> voList = resultPage.getRecords().stream().map(cancelApplication -> {
            CancelOrderVO vo = new CancelOrderVO();
            BeanUtils.copyProperties(cancelApplication, vo);
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<CancelOrderVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result giveResult(ResultDTO resultDTO) {
        Long cancelOrderId = resultDTO.getId();
        CancelApplication cancelApplication = cancelApplicationMapper.selectById(cancelOrderId);

        cancelApplication.setReviewStatus(resultDTO.getReviewStatus());
        cancelApplication.setUpdatedAt(LocalDateTime.now());
        Long reservationId = cancelApplication.getReservationId();
        Reservation reservation = reservationMapper.selectById(reservationId);
        Long userId = cancelApplication.getUserId();
        User user = userMapper.selectById(userId);

        if (resultDTO.getReviewStatus().equals(DatabaseConstant.PASS)) {
            reservation.setStatus(DatabaseConstant.CANCELLED);
            reservation.setUpdatedAt(LocalDateTime.now());
            user.setBalance(user.getBalance() + cancelApplication.getRefundAmount());
            user.setUpdatedAt(LocalDateTime.now());
        } else {
            reservation.setStatus(DatabaseConstant.LOCK);
            reservation.setUpdatedAt(LocalDateTime.now());
        }


        userMapper.updateById(user);
        reservationMapper.updateById(reservation);
        cancelApplicationMapper.updateById(cancelApplication);

        return Result.success();
    }
}
