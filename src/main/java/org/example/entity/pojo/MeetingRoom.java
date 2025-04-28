package org.example.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zklee
 */
@Data
@TableName("meeting_room")
public class MeetingRoom {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name; // 会议室名称

    private String type; // 类型（教室型/圆桌型）

    private Integer area; // 面积（平方米）

    private Integer seatCount; // 座位数

    private Boolean hasProjector = false; // 是否有投影仪

    private Boolean hasSound = false; // 是否有音响系统

    private Boolean hasNetwork = false; // 是否有网络支持

    private Double pricePerHour; // 每小时租赁价格

    private Boolean isUnderMaintenance = false; // 是否处于维修状态

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
