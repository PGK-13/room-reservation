create table cancel_application
(
    id             bigint auto_increment comment '主键ID'
        primary key,
    reservation_id bigint                                   not null comment '关联预约ID',
    user_id        bigint                                   not null comment '申请人（客户）ID',
    reason         varchar(255)                             null comment '申请取消原因',
    refund_amount  decimal(10, 2) default 0.00              null comment '应退金额',
    review_status  varchar(20)    default '待审核'          null comment '审核状态(待审核/通过/拒绝)',
    created_at     datetime       default CURRENT_TIMESTAMP null comment '创建时间',
    updated_at     datetime       default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint cancel_application_ibfk_1
        foreign key (reservation_id) references reserve_meetingroom_system.reservation (id),
    constraint cancel_application_ibfk_2
        foreign key (user_id) references reserve_meetingroom_system.user (id)
)
    comment '取消预约申请表';

create index reservation_id
    on cancel_application (reservation_id);

create index user_id
    on cancel_application (user_id);

create table meeting_room
(
    id                   bigint auto_increment comment '主键ID'
        primary key,
    name                 varchar(100)                       not null comment '会议室名称',
    type                 varchar(20)                        not null comment '会议室类型（教室型/圆桌型）',
    area                 int                                not null comment '会议室面积（平方米）',
    seat_count           int                                not null comment '座位数',
    has_projector        tinyint  default 0                 null comment '是否有投影仪(0否/1是)',
    has_sound            tinyint  default 0                 null comment '是否有音响系统(0否/1是)',
    has_network          tinyint  default 0                 null comment '是否有网络支持(0否/1是)',
    price_per_hour       decimal(10, 2)                     not null comment '每小时租赁价格',
    is_under_maintenance tinyint  default 0                 null comment '是否处于维修状态(0正常（空闲状态），1维修)',
    created_at           datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_at           datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '会议室表';

create table reservation
(
    id              bigint auto_increment comment '主键ID'
        primary key,
    user_id         bigint                                not null comment '客户ID',
    meeting_room_id bigint                                not null comment '会议室ID',
    start_time      datetime                              not null comment '预约开始时间',
    end_time        datetime                              not null comment '预约结束时间',
    status          varchar(20)                           not null comment '预约状态(lock锁定/reserved预定/review审核/cancelled取消)',
    pay_status      varchar(20) default '未支付'          null comment '支付状态（0未支付/1已支付）',
    total_price     decimal(10, 2)                        not null comment '租赁总价格',
    created_at      datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    updated_at      datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint reservation_ibfk_1
        foreign key (user_id) references reserve_meetingroom_system.user (id),
    constraint reservation_ibfk_2
        foreign key (meeting_room_id) references reserve_meetingroom_system.meeting_room (id)
)
    comment '预约订单表';

create index meeting_room_id
    on reservation (meeting_room_id);

create index user_id
    on reservation (user_id);

create table user
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    username     varchar(50)                        not null comment '登录账号',
    password     varchar(100)                       not null comment '登录密码（加密）',
    name         varchar(50)                        not null comment '姓名',
    role         varchar(20)                        not null comment '角色(admin/employee/customer)',
    company      varchar(100)                       null comment '所属公司名称（仅客户有',
    phone        varchar(20)                        null comment '联系电话（仅客户有）',
    status       tinyint  default 1                 null comment '账号状态(0正常，1待审核，2冻结)',
    audit_reason varchar(255)                       null comment '审核/冻结备注原因',
    created_at   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_at   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    balance      double   default 0                 not null comment '余额',
    constraint username
        unique (username)
)
    comment '用户表';

