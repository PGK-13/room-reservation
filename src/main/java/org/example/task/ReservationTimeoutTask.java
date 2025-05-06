package org.example.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.DatabaseConstant;
import org.example.entity.pojo.Reservation;
import org.example.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zklee
 * date 2025/4/29
 */
@Component
@Slf4j
public class ReservationTimeoutTask {

    @Autowired
    private ReservationMapper reservationMapper;

    /**
     * 每5分钟执行一次，扫描并关闭超时未支付订单
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cancelTimeoutReservations() {
        log.info("开始执行超时未支付订单扫描任务...");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thresholdTime = now.minusMinutes(30);

        // 查询超时未支付的预约
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reservation::getStatus, DatabaseConstant.LOCK)
                .eq(Reservation::getPayStatus, DatabaseConstant.UNPAY)
                .lt(Reservation::getCreatedAt, thresholdTime);

        List<Reservation> timeoutReservations = reservationMapper.selectList(queryWrapper);

        for (Reservation reservation : timeoutReservations) {
            reservation.setStatus(DatabaseConstant.CANCELLED);
            reservationMapper.updateById(reservation);
            log.info("取消超时未支付订单，预约ID: {}", reservation.getId());
        }

        log.info("超时订单扫描任务完成，处理了 {} 条记录", timeoutReservations.size());
    }
}
