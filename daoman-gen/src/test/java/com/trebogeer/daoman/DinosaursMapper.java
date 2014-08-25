package com.trebogeer.daoman;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Collection;

/**
 * @author dimav
 *         Date: 8/25/14
 *         Time: 4:13 PM
 */
public interface DinosaursMapper {
   // @Select("call get_dinosaurs({})")
    @Select({ "{call get_dinosaurs(", "#{integer,jdbcType=INTEGER,mode=OUT})}"})
    @Options(statementType = StatementType.CALLABLE)
    Collection<Long> getDinosaurs(MBParam p);
}