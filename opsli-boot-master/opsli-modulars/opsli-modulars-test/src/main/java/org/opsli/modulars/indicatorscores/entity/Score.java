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
package org.opsli.modulars.indicatorscores.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.core.base.entity.BaseEntity;

/**
 * 选股评分 Entity
 *
 * @author liugy
 * @date 2022-09-08 21:43:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Score extends BaseEntity {


    /** 选股ID */
    private Long choosenStockId;

    /** 指标ID */
    private Long indicatorId;

    /** 日期 */
    private Date choosenDate;

    /** 时间计数 */
    private Long dateCount;

    /** 值 */
    private BigDecimal value;

    /** 说明 */
    private String note;


    // ========================================








}