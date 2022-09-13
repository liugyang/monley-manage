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
package org.opsli.api.wrapper.testtable;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.base.warpper.ApiWrapper;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.annotation.validator.ValidatorLenMin;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
* 测试表2 Model
*
* @author test
* @date 2022-09-07 21:46:01
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCarCopy2Model extends ApiWrapper {

    /** 汽车名称 */
    @ApiModelProperty(value = "汽车名称")
    @ExcelProperty(value = "汽车名称", order = 1)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(20)
    private String carName;

    /** 汽车类型 */
    @ApiModelProperty(value = "汽车类型")
    @ExcelProperty(value = "汽车类型", order = 2)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(20)
    private String carType;

    /** 汽车品牌 */
    @ApiModelProperty(value = "汽车品牌")
    @ExcelProperty(value = "汽车品牌", order = 3)
    @ExcelInfo
    @ValidatorLenMax(50)
    private String carBrand;

    /** 生产日期 */
    @ApiModelProperty(value = "生产日期")
    @ExcelProperty(value = "生产日期", order = 4)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    private String produceData;

    /** 是否启用 */
    @ApiModelProperty(value = "是否启用")
    @ExcelProperty(value = "是否启用", order = 5)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    @ValidatorLenMax(1)
    private String izUsable;

    /** 时间戳 */
    @ApiModelProperty(value = "时间戳")
    @ExcelProperty(value = "时间戳", order = 13)
    @ExcelInfo
    @Validator({
        ValidatorType.IS_NOT_NULL
    })
    private String ts;



}