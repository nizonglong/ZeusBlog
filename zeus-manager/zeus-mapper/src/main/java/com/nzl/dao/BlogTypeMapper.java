package com.nzl.dao;

import com.nzl.model.dto.TypeDto;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface BlogTypeMapper extends Mapper<TypeDto> {
    /**
     * 获取类别名称
     * @param typeId
     * @return
     */
    String getNameById(String typeId);
}