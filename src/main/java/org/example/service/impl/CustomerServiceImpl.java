package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.constant.DatabaseConstant;
import org.example.entity.PageResult;
import org.example.entity.Result;
import org.example.entity.customer.dto.CancelReasonDTO;
import org.example.entity.customer.dto.MeetingRoomDTO;
import org.example.entity.customer.dto.ReservationDTO;
import org.example.entity.customer.vo.CancelOrderVO;
import org.example.entity.customer.vo.MeetingRoomVO;
import org.example.entity.customer.vo.ReservationVO;
import org.example.entity.pojo.CancelApplication;
import org.example.entity.pojo.MeetingRoom;
import org.example.entity.pojo.Reservation;
import org.example.entity.pojo.User;
import org.example.exception.*;
import org.example.mapper.CancelApplicationMapper;
import org.example.mapper.MeetingRoomMapper;
import org.example.mapper.ReservationMapper;
import org.example.mapper.UserMapper;
import org.example.service.CustomerService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zklee
 * date 2025/4/28
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    // TODO 分页逻辑有重复，可以选择提取
    @Autowired
    private CancelApplicationMapper cancelApplicationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Autowired
    private ReservationMapper reservationMapper;

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
    public Result<PageResult<MeetingRoomVO>> searchAvailable(MeetingRoomDTO meetingRoomDTO) {
        LocalDateTime startTime = meetingRoomDTO.getStartTime();
        LocalDateTime endTime = meetingRoomDTO.getEndTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusDays(60);
        if (startTime == null || endTime == null) {
            throw new TimeRequiredException();
        }
        //TODO 开始时间不晚于结束时间
        if (startTime.isAfter(deadline)) {
            throw new TimeOver60Exception();
        }
        if (startTime.isBefore(now)) {
            throw new TimeBeforeException();
        }
        int startHour = startTime.getHour();
        int endHour = endTime.getHour();
        if (startHour < 8 || endHour > 21) {
            throw new TimeRestException();
        }

        Page<MeetingRoom> page = new Page<>(meetingRoomDTO.getPageNo(), meetingRoomDTO.getPageSize()); // 可以从请求里传pageNo,pageSize
        LambdaQueryWrapper<MeetingRoom> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(MeetingRoom::getIsUnderMaintenance, 0);

        if (meetingRoomDTO.getType() != null) {
            queryWrapper.eq(MeetingRoom::getType, meetingRoomDTO.getType());
        }
        if (meetingRoomDTO.getSeatCount() != null) {
            queryWrapper.ge(MeetingRoom::getSeatCount, meetingRoomDTO.getSeatCount());
        }
        if (meetingRoomDTO.getHasProjector() != null) {
            queryWrapper.eq(MeetingRoom::getHasProjector, meetingRoomDTO.getHasProjector());
        }
        if (meetingRoomDTO.getHasSound() != null) {
            queryWrapper.eq(MeetingRoom::getHasSound, meetingRoomDTO.getHasSound());
        }
        if (meetingRoomDTO.getHasNetwork() != null) {
            queryWrapper.eq(MeetingRoom::getHasNetwork, meetingRoomDTO.getHasNetwork());
        }
        // 查询所有符合条件的会议室
        Page<MeetingRoom> meetingRoomPage = meetingRoomMapper.selectPage(page, queryWrapper);

        // 筛掉时间段冲突的会议室
        List<MeetingRoomVO> availableRooms = meetingRoomPage.getRecords().stream()
                .filter(room -> isRoomAvailable(room.getId(), meetingRoomDTO.getStartTime(), meetingRoomDTO.getEndTime()))
                .map(room -> {
                    MeetingRoomVO vo = new MeetingRoomVO();
                    BeanUtils.copyProperties(room, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        // 手动搞定的分页逻辑
        // TODO 可改成数据连接查询
        int pageNo = meetingRoomDTO.getPageNo(); // 当前第几页
        int pageSize = meetingRoomDTO.getPageSize(); // 每页多少条

        // availableRooms 是筛选后的一大堆房间
        int total = availableRooms.size();
        int fromIndex = (pageNo - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        // 防止超出范围
        if (fromIndex > total) {
            fromIndex = total;
        }
        if (toIndex > total) {
            toIndex = total;
        }

        // 截取出这一页的数据
        List<MeetingRoomVO> pageList = availableRooms.subList(fromIndex, toIndex);

        // 封装返回
        PageResult<MeetingRoomVO> result = new PageResult<>(
                (long) total,
                (long) Math.ceil((double) total / pageSize),
                (long) pageNo,
                (long) pageSize,
                pageList
        );

        return Result.success(result);
    }

    private boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Integer count = reservationMapper.countConflictReservations(roomId, startTime, endTime);
        return count == 0;
    }

    //TODO 看谁先点的下单 抢夺公共资源
    @Override
    public Result placeOrder(ReservationDTO reservationDTO) {
        Long userId = ThreadLocalUtil.getUserId();
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO, reservation);
        reservation.setUserId(userId);
        reservation.setStatus("lock");
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());

        LocalDateTime startTime = reservationDTO.getStartTime();
        LocalDateTime endTime = reservationDTO.getEndTime();
        // 计算预约时长（小时）
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
//        if (hours <= 0) {
//            throw new BusinessException("预约时间必须至少为1小时");
//        }

        // 查询会议室价格
        MeetingRoom meetingRoom = meetingRoomMapper.selectById(reservationDTO.getMeetingRoomId());
//        if (meetingRoom == null) {
//            throw new BusinessException("会议室不存在");
//        }
        double pricePerHour = meetingRoom.getPricePerHour();

        // 计算总金额
        double totalPrice = hours * pricePerHour;
        reservation.setTotalPrice(totalPrice);

        reservationMapper.insert(reservation);
        return Result.success();
    }

    @Override
    public Result<PageResult<ReservationVO>> listReservation(Integer pageNo, Integer pageSize) {
        Page<Reservation> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();

        Long userId = ThreadLocalUtil.getUserId();
        queryWrapper.eq(Reservation::getUserId, userId)
                .orderByAsc(Reservation::getCreatedAt);

        Page<Reservation> resultPage = reservationMapper.selectPage(page, queryWrapper);

        List<ReservationVO> voList = resultPage.getRecords().stream().map(reservation -> {
            ReservationVO vo = new ReservationVO();
            BeanUtils.copyProperties(reservation, vo);
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<ReservationVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result<PageResult<ReservationVO>> listReservationUnpay(Integer pageNo, Integer pageSize) {
        Page<Reservation> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();

        Long userId = ThreadLocalUtil.getUserId();
        queryWrapper.eq(Reservation::getUserId, userId)
                .eq(Reservation::getStatus, "lock")
                .eq(Reservation::getPayStatus, "未支付")
                .orderByDesc(Reservation::getCreatedAt);

        Page<Reservation> resultPage = reservationMapper.selectPage(page, queryWrapper);

        List<ReservationVO> voList = resultPage.getRecords().stream().map(reservation -> {
            ReservationVO vo = new ReservationVO();
            BeanUtils.copyProperties(reservation, vo);
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<ReservationVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result pay(Long id) {
        Long userId = ThreadLocalUtil.getUserId();
        User user = userMapper.selectById(userId);

        Reservation reservation = reservationMapper.selectById(id);
        if (user.getBalance() < reservation.getTotalPrice()) {
            throw new MoneyNotEnoughException();
        }
        //TODO 得到会议室 和 失去金钱 要同步
        user.setBalance(user.getBalance() - reservation.getTotalPrice());
        reservation.setStatus(DatabaseConstant.RESERVED);
        reservation.setPayStatus(DatabaseConstant.PAYED);

        userMapper.updateById(user);
        reservationMapper.updateById(reservation);

        return Result.success();
    }

    @Override
    public Result<PageResult<ReservationVO>> listFinishedOrder(Integer pageNo, Integer pageSize) {
        LocalDateTime now = LocalDateTime.now();
        Page<Reservation> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();

        Long userId = ThreadLocalUtil.getUserId();
        queryWrapper.eq(Reservation::getUserId, userId)
                .eq(Reservation::getStatus, DatabaseConstant.RESERVED)
                .eq(Reservation::getPayStatus, DatabaseConstant.PAYED)
                .lt(Reservation::getStartTime, now.plusHours(24))
                .orderByDesc(Reservation::getCreatedAt);

        Page<Reservation> resultPage = reservationMapper.selectPage(page, queryWrapper);

        List<ReservationVO> voList = resultPage.getRecords().stream().map(reservation -> {
            ReservationVO vo = new ReservationVO();
            BeanUtils.copyProperties(reservation, vo);
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<ReservationVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }


    //  ----------------------------------  取消模块 --------------------------------------
    @Override
    public Result<PageResult<ReservationVO>> listCanCanceled(Integer pageNo, Integer pageSize) {
        LocalDateTime now = LocalDateTime.now();

        Page<Reservation> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();

        Long userId = ThreadLocalUtil.getUserId();
        queryWrapper.eq(Reservation::getUserId, userId)
                .eq(Reservation::getStatus, DatabaseConstant.RESERVED)
                .eq(Reservation::getPayStatus, DatabaseConstant.PAYED)
                .ge(Reservation::getStartTime, now.plusHours(24))
                .orderByDesc(Reservation::getCreatedAt);

        Page<Reservation> resultPage = reservationMapper.selectPage(page, queryWrapper);

        List<ReservationVO> voList = resultPage.getRecords().stream().map(reservation -> {
            ReservationVO vo = new ReservationVO();
            BeanUtils.copyProperties(reservation, vo);
            return vo;
        }).collect(Collectors.toList());

        // 返回
        PageResult<ReservationVO> pageResult = new PageResult<>(
                resultPage.getTotal(),
                resultPage.getPages(),
                resultPage.getCurrent(),
                resultPage.getSize(),
                voList
        );

        return Result.success(pageResult);
    }

    @Override
    public Result apply(CancelReasonDTO cancelReasonDTO) {
        LocalDateTime now = LocalDateTime.now();

        CancelApplication cancelApplication = new CancelApplication();
        Long userId = ThreadLocalUtil.getUserId();
        BeanUtils.copyProperties(cancelReasonDTO, cancelApplication);
        cancelApplication.setUserId(userId);
        cancelApplication.setReviewStatus(DatabaseConstant.REVIEWED);
        cancelApplication.setCreatedAt(LocalDateTime.now());
        cancelApplication.setUpdatedAt(LocalDateTime.now());

        Reservation reservation = reservationMapper.selectById(cancelReasonDTO.getReservationId());
        Double totalPrice = reservation.getTotalPrice();
        long hoursUntilStart = Duration.between(now, reservation.getStartTime()).toHours();
        if (hoursUntilStart >= 72) {
            cancelApplication.setRefundAmount(totalPrice);
        } else if (hoursUntilStart >= 48) {
            cancelApplication.setRefundAmount(totalPrice * 0.75);
        } else {
            cancelApplication.setRefundAmount(totalPrice * 0.25);
        }

        reservation.setStatus(DatabaseConstant.ORDERREVIEW);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationMapper.updateById(reservation);
        cancelApplicationMapper.insert(cancelApplication);

        return Result.success();
    }

    @Override
    public Result<PageResult<CancelOrderVO>> listCanceledOrder(Integer pageNo, Integer pageSize) {
        Page<CancelApplication> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<CancelApplication> queryWrapper = new LambdaQueryWrapper<>();

        Long userId = ThreadLocalUtil.getUserId();
        queryWrapper.eq(CancelApplication::getUserId, userId)
                .orderByDesc(CancelApplication::getCreatedAt);

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
}
