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
package org.opsli.modulars.indicatorscores.web;

import java.util.*;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.opsli.api.base.result.ResultWrapper;
import org.opsli.api.wrapper.indicatorscores.IndicatorScore;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.core.base.controller.BaseRestController;
import org.opsli.core.persistence.Page;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.opsli.modulars.indicatorscores.entity.Indicator;
import org.opsli.modulars.indicatorscores.mapper.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.opsli.core.log.enums.*;
import org.opsli.core.log.annotation.OperateLogger;

import org.opsli.modulars.indicatorscores.entity.Score;
import org.opsli.api.wrapper.indicatorscores.ScoreModel;
import org.opsli.modulars.indicatorscores.service.IScoreService;
import org.opsli.api.web.indicatorscores.ScoreRestApi;

/**
 * 选股评分 Controller
 *
 * @author liugy
 * @date 2022-09-08 21:43:19
 */
@Api(tags = ScoreRestApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/indicatorscores")
public class ScoreRestController extends BaseRestController<Score, ScoreModel, IScoreService>
    implements ScoreRestApi {

    List<IndicatorScore> indicatorScoreList;
    int PAGE_SIZE = 100000;
    String choosenDateBegin = "";
    String choosenDateEnd = "";
    Map<Long, Indicator> indicatorsMap;

    @Autowired
    private ScoreMapper scoreMapper;
    /**
     * 选股评分 查一条
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "获得单条选股评分", notes = "获得单条选股评分 - ID")
    @PreAuthorize("hasAuthority('indicatorscores_select')")
    @Override
    public ResultWrapper<ScoreModel> get(ScoreModel model) {
        // 如果系统内部调用 则直接查数据库
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultWrapper.getSuccessResultWrapper(model);
    }

    /**
     * 选股评分 查询分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultWrapper
     */
    @ApiOperation(value = "获得分页数据", notes = "获得分页数据 - 查询构造器")
    @PreAuthorize("hasAuthority('indicatorscores_select')")
    @Override
    public ResultWrapper<?> findPage(Integer pageNo, Integer pageSize, HttpServletRequest request) {
        if(indicatorsMap == null){
            indicatorsMap = new HashMap<>();
            List<Indicator> indicators = scoreMapper.getIndicators();
            for(Indicator in: indicators){
                indicatorsMap.put(in.getId(),in);
            }
        }
        if(request.getParameterMap().get("choosenDate_BEGIN") != null && request.getParameterMap().get("choosenDate_END") != null){
            String[] _choosenDateBegin = request.getParameterMap().get("choosenDate_BEGIN");
            String[] _choosenDateEnd = request.getParameterMap().get("choosenDate_END");

            if(_choosenDateBegin.length>0&&choosenDateBegin.equals(_choosenDateBegin[0])&& _choosenDateEnd.length>0&&choosenDateEnd.equals(_choosenDateEnd[0])){
                Page.PageData pageData = new Page.PageData();
                pageData.setTotal((long)indicatorScoreList.size());
                if(indicatorScoreList.size()>0) {
                    if((pageNo - 1) * pageSize >indicatorScoreList.size()){
                        pageData.setRows(new ArrayList<>());
                    }else if(pageNo * pageSize > indicatorScoreList.size()){
                        pageData.setRows(indicatorScoreList.subList((pageNo - 1) * pageSize,indicatorScoreList.size()));
                    }else {
                        pageData.setRows(indicatorScoreList.subList((pageNo - 1) * pageSize, pageNo * pageSize));
                    }
                }else{
                    pageData.setRows(indicatorScoreList);
                }

                return ResultWrapper.getSuccessResultWrapper(pageData);
            }
        }

        if (request.getParameterMap().get("choosenDate_BEGIN") != null) {
            choosenDateBegin = request.getParameterMap().get("choosenDate_BEGIN")[0];
        }
        if (request.getParameterMap().get("choosenDate_END") != null) {
            choosenDateEnd = request.getParameterMap().get("choosenDate_END")[0];
        }

        QueryBuilder<Score> queryBuilder = new WebQueryBuilder<>(IService.getEntityClass(), request.getParameterMap());
        Page<Score, ScoreModel> page = new Page<>(1, PAGE_SIZE);
        page.setQueryWrapper(queryBuilder.build());
        page = IService.findPage(page);

        Map<Long, List<ScoreModel>> map = new LinkedHashMap<>();
        for (ScoreModel sm : page.getList()) {
            if (map.get(sm.getChoosenStockId()) == null) {
                map.put(sm.getChoosenStockId(), new ArrayList<>());
            } else {
                map.get(sm.getChoosenStockId()).add(sm);
            }
        }

        indicatorScoreList = new ArrayList<>();

        for (Long datecount:map.keySet()){
            IndicatorScore is = new IndicatorScore();
            is.setScoreList(new ArrayList<>());
            long totalScores = 0L;
            for(ScoreModel sm:map.get(datecount)) {
                sm.setNote(sm.getNote()==null?indicatorsMap.get(sm.getIndicatorId()).getName():sm.getNote());

                is.setDateCount(sm.getDateCount());
                is.setIndicatorId(sm.getIndicatorId());
                is.setValue(sm.getValue());
                is.setNote(sm.getNote());
                is.setChoosenDate(sm.getChoosenDate());
                is.setChoosenStockId(sm.getChoosenStockId());
                is.getScoreList().add(sm);

                totalScores += sm.getValue().longValue();
            }
            is.setTotalScores(totalScores);
            indicatorScoreList.add(is);
        }

        Page.PageData pageData = new Page.PageData();
        pageData.setTotal((long)indicatorScoreList.size());
        if(indicatorScoreList.size()>0) {
            if((pageNo - 1) * pageSize >indicatorScoreList.size()){
                pageData.setRows(new ArrayList<>());
            }else if(pageNo * pageSize > indicatorScoreList.size()){
                pageData.setRows(indicatorScoreList.subList((pageNo - 1) * pageSize,indicatorScoreList.size()));
            }else {
                pageData.setRows(indicatorScoreList.subList((pageNo - 1) * pageSize, pageNo * pageSize));
            }
        }else{
            pageData.setRows(indicatorScoreList);
        }


        return ResultWrapper.getSuccessResultWrapper(pageData);
    }

    /**
     * 选股评分 新增
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "新增选股评分数据", notes = "新增选股评分数据")
    @PreAuthorize("hasAuthority('indicatorscores_insert')")
    @OperateLogger(description = "新增选股评分数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> insert(ScoreModel model) {
        // 调用新增方法
        IService.insert(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("新增选股评分成功");
    }

    /**
     * 选股评分 修改
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "修改选股评分数据", notes = "修改选股评分数据")
    @PreAuthorize("hasAuthority('indicatorscores_update')")
    @OperateLogger(description = "修改选股评分数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> update(ScoreModel model) {
        // 调用修改方法
        IService.update(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("修改选股评分成功");
    }


    /**
     * 选股评分 删除
     * @param id ID
     * @return ResultVo
     */
    @ApiOperation(value = "删除选股评分数据", notes = "删除选股评分数据")
    @PreAuthorize("hasAuthority('indicatorscores_delete')")
    @OperateLogger(description = "删除选股评分数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> del(String id){
        IService.delete(id);
        return ResultWrapper.getSuccessResultWrapperByMsg("删除选股评分成功");
    }

    /**
     * 选股评分 批量删除
     * @param ids ID 数组
     * @return ResultVo
     */
    @ApiOperation(value = "批量删除选股评分数据", notes = "批量删除选股评分数据")
    @PreAuthorize("hasAuthority('indicatorscores_delete')")
    @OperateLogger(description = "批量删除选股评分数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> delAll(String ids){
        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);
        return ResultWrapper.getSuccessResultWrapperByMsg("批量删除选股评分成功");
    }

    /**
     * 选股评分 Excel 导出认证
     *
     * @param type 类型
     * @param request request
     */
    @ApiOperation(value = "Excel 导出认证", notes = "Excel 导出认证")
    @PreAuthorize("hasAnyAuthority('indicatorscores_export', 'indicatorscores_import')")
    @Override
    public ResultWrapper<String> exportExcelAuth(String type, HttpServletRequest request) {
        Optional<String> certificateOptional =
                super.excelExportAuth(type, ScoreRestApi.SUB_TITLE, request);
        if(!certificateOptional.isPresent()){
            return ResultWrapper.getErrorResultWrapper();
        }
        return ResultWrapper.getSuccessResultWrapper(certificateOptional.get());
    }


    /**
     * 选股评分 Excel 导出
     * @param response response
     */
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @PreAuthorize("hasAuthority('indicatorscores_export')")
    @OperateLogger(description = "选股评分 导出Excel",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.SELECT, db = true)
    @Override
    public void exportExcel(String certificate, HttpServletResponse response) {
        // 导出Excel
        super.excelExport(certificate, response);
    }

    /**
     * 选股评分 Excel 导入
     * 注：这里 RequiresPermissions 引入的是 Shiro原生鉴权注解
     * @param request 文件流 request
     * @return ResultVo
     */
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    @PreAuthorize("hasAuthority('indicatorscores_import')")
    @OperateLogger(description = "选股评分 Excel 导入",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }

}