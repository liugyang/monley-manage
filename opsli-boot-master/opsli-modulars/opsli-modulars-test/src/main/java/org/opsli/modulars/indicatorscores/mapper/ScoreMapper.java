/**
 * Copyright 2020 OPSLI 快速开发平台 https://www.opsli.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opsli.modulars.indicatorscores.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.opsli.modulars.indicatorscores.entity.Indicator;
import org.opsli.modulars.indicatorscores.entity.Score;
import java.util.List;

/**
 * 选股评分 Mapper
 *
 * @author liugy
 * @date 2022-09-08 21:43:19
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    @Results({@Result(column="id",property="id"),@Result(column="sn",property="sn"),
            @Result(column="name",property="name"),@Result(column="is_calc",property="isCalc"),
            @Result(column="is_value",property="isValue"),@Result(column="weight",property="weight")})
    @Select("select id,sn,name,is_calc,is_value,weight from indicator")
    List<Indicator> getIndicators();
}