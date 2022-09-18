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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.opsli.modulars.indicatorscores.entity.Score;

/**
 * 选股评分 Mapper
 *
 * @author liugy
 * @date 2022-09-08 21:43:19
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
//    @Select("select count(user_id) from " +
//            "(select DISTINCT a.user_id from evaluate_result a left join user b on a.user_id = b.id where scene_id = #{sceneId} and b.department like CONCAT('%',#{departmentId},'%')) a")
//    int countTakePracticeEmployee(@Param("sceneId") long sceneId, @Param("departmentId") long departmentId);
}