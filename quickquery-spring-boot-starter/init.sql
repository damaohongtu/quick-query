create database orderquery;
use orderquery;
create table if not exists orderquery.query_order_graph
(
    id          bigint auto_increment
        primary key,
    graph_code  varchar(255)                                not null comment '关联图编码',
    graph_name  varchar(256)  default ''                    not null comment '关联图名',
    create_time timestamp     default '1970-01-01 08:00:01' not null comment '创建时间',
    update_time timestamp     default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_by   varchar(256)  default ''                    not null comment '创建人',
    update_by   varchar(256)  default ''                    not null comment '更新人',
    edges       varchar(2048) default ''                    not null comment '关联图边信息',
    valid       tinyint       default 1                     not null comment '逻辑删除键，=1有效，=0失效',
    constraint graph_code
        unique (graph_code)
)
    comment '查询关联图';


create table orderquery.query_order_hash
(
    id          bigint auto_increment
        primary key,
    code        varchar(255)                                not null comment '编码',
    name        varchar(256)  default ''                    not null comment '名称',
    script      text                                        not null comment '脚本',
    create_time timestamp     default '1970-01-01 08:00:01' not null comment '创建时间',
    update_time timestamp     default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_by   varchar(256)  default ''                    not null comment '创建人',
    update_by   varchar(256)  default ''                    not null comment '更新人',
    valid       tinyint       default 1                     not null comment '逻辑删除键，=1有效，=0失效',
    constraint code
        unique (code)
)
    comment '查询关联图';

create table orderquery.query_order_node
(
    id            bigint auto_increment
        primary key,
    node_code     varchar(255)                                not null comment '节点编码',
    graph_code    varchar(255)                                not null comment '关联图编码',
    node_name     varchar(256)  default ''                    not null comment '节点名',
    node_type     varchar(64)   default ''                    not null comment '节点类型',
    data_source   varchar(1024) default ''                    not null comment '数据源',
    input_field   varchar(1024) default ''                    not null comment '条件字段',
    output_field  text                                        not null comment '结果字段',
    route_rule    varchar(1024) default ''                    not null comment '路由规则: 正则表达式',
    neighbor_node varchar(1024) default ''                    not null comment '邻接节点',
    coordinate    varchar(2048) default ''                    not null comment '关联图坐标',
    create_time   timestamp     default '1970-01-01 08:00:01' not null comment '创建时间',
    update_time   timestamp     default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_by     varchar(256)  default ''                    not null comment '创建人',
    update_by     varchar(256)  default ''                    not null comment '更新人',
    valid         tinyint       default 1                     not null comment '逻辑删除键，=1有效，=0失效',
    constraint uniq_graph_node_code
        unique (graph_code, node_code)
)
    comment '查询关联图节点';