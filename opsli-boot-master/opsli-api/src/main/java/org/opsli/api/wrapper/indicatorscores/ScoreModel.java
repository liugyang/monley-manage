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
package org.opsli.api.wrapper.indicatorscores;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
* 选股评分 Model
*
* @author liugy
* @date 2022-09-08 21:43:19
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreModel extends ApiWrapper {

    /** 选股ID */
    @ApiModelProperty(value = "选股ID")
    @ExcelProperty(value = "选股ID", order = 1)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(19)
    private Long choosenStockId;

    /** 指标ID */
    @ApiModelProperty(value = "指标ID")
    @ExcelProperty(value = "指标ID", order = 2)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL,
        ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(19)
    private Long indicatorId;

    /** 日期 */
    @ApiModelProperty(value = "日期")
    @ExcelProperty(value = "日期", order = 3)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date choosenDate;

    /** 时间计数 */
    @ApiModelProperty(value = "时间计数")
    @ExcelProperty(value = "时间计数", order = 4)
    @ExcelInfo
    @ValidatorLenMax(19)
    private Long dateCount;

    /** 值 */
    @ApiModelProperty(value = "值")
    @ExcelProperty(value = "值", order = 5)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(20)
    private BigDecimal value;

    /** 说明 */
    @ApiModelProperty(value = "说明")
    @ExcelProperty(value = "说明", order = 6)
    @ExcelInfo
    @ValidatorLenMax(40)
    private String note;



}