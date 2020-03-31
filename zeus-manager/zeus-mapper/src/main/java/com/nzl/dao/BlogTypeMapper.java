package com.nzl.dao;

import com.nzl.model.dto.TypeDto;
import tk.mybatis.mapper.common.Mapper;

public interface BlogTypeMapper extends Mapper<TypeDto> {
    /**
     * 获取类别名称
     * @param typeId
     * @return
     */
    String getNameById(String typeId);
}