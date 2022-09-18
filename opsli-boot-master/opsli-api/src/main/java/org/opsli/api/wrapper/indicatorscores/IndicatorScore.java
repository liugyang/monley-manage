package org.opsli.api.wrapper.indicatorscores;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;
import org.opsli.plugins.excel.annotation.ExcelInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class IndicatorScore {

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

    /** 指标评分 */
    @ApiModelProperty(value = "指标评分")
    @ExcelProperty(value = "指标评分", order = 1)
    @ExcelInfo
    @Validator({
            ValidatorType.IS_NOT_NULL,
            ValidatorType.IS_INTEGER
    })
    private List<ScoreModel> ScoreList;

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

    /** 总评分 */
    @ApiModelProperty(value = "总评分")
    @ExcelProperty(value = "总评分", order = 1)
    @ExcelInfo
    @Validator({
            ValidatorType.IS_NOT_NULL,
            ValidatorType.IS_INTEGER
    })
    @ValidatorLenMax(19)
    private Long totalScores;
}
