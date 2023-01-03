package com.damaohongtu.quickquery.dto.sharding;

import com.damaohongtu.quickquery.dao.entity.Sharding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShardingDto {

    private Long id;

    private String code;

    private String name;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    private Byte valid;

    private String script;

    public static ShardingDto fromPo(Sharding sharding){
        ShardingDto shardingDto = ShardingDto.builder()
                .id(sharding.getId())
                .code(sharding.getCode())
                .name(sharding.getName())
                .createTime(sharding.getCreateTime())
                .updateTime(sharding.getUpdateTime())
                .createBy(sharding.getCreateBy())
                .updateBy(sharding.getUpdateBy())
                .valid(sharding.getValid())
                .script(sharding.getScript())
                .build();
        return shardingDto;
    }

    public Sharding toPo(){
        Sharding sharding = new Sharding();
        sharding.setId(this.getId());
        sharding.setCode(this.getCode());
        sharding.setName(this.getName());
        sharding.setCreateTime(this.getCreateTime());
        sharding.setUpdateTime(this.getUpdateTime());
        sharding.setCreateBy(this.getCreateBy());
        sharding.setUpdateBy(this.getUpdateBy());
        sharding.setValid(this.getValid());
        sharding.setScript(this.getScript());
        return sharding;
    }

}
