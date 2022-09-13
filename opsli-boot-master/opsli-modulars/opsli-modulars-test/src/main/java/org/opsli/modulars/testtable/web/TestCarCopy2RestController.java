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
package org.opsli.modulars.testtable.web;

import java.util.Optional;
import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.opsli.api.base.result.ResultWrapper;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.core.base.controller.BaseRestController;
import org.opsli.core.persistence.Page;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.opsli.core.log.enums.*;
import org.opsli.core.log.annotation.OperateLogger;

import org.opsli.modulars.testtable.entity.TestCarCopy2;
import org.opsli.api.wrapper.testtable.TestCarCopy2Model;
import org.opsli.modulars.testtable.service.ITestCarCopy2Service;
import org.opsli.api.web.testtable.TestCarCopy2RestApi;

/**
 * 测试表2 Controller
 *
 * @author test
 * @date 2022-09-07 21:46:01
 */
@Api(tags = TestCarCopy2RestApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/testtable")
public class TestCarCopy2RestController extends BaseRestController<TestCarCopy2, TestCarCopy2Model, ITestCarCopy2Service>
    implements TestCarCopy2RestApi {


    /**
     * 测试表2代码生成 查一条
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "获得单条测试表2代码生成", notes = "获得单条测试表2代码生成 - ID")
    @PreAuthorize("hasAuthority('testtable_select')")
    @Override
    public ResultWrapper<TestCarCopy2Model> get(TestCarCopy2Model model) {
        // 如果系统内部调用 则直接查数据库
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultWrapper.getSuccessResultWrapper(model);
    }

    /**
     * 测试表2代码生成 查询分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultWrapper
     */
    @ApiOperation(value = "获得分页数据", notes = "获得分页数据 - 查询构造器")
    @PreAuthorize("hasAuthority('testtable_select')")
    @Override
    public ResultWrapper<?> findPage(Integer pageNo, Integer pageSize, HttpServletRequest request) {

        QueryBuilder<TestCarCopy2> queryBuilder = new WebQueryBuilder<>(IService.getEntityClass(), request.getParameterMap());
        Page<TestCarCopy2, TestCarCopy2Model> page = new Page<>(pageNo, pageSize);
        page.setQueryWrapper(queryBuilder.build());
        page = IService.findPage(page);

        return ResultWrapper.getSuccessResultWrapper(page.getPageData());
    }

    /**
     * 测试表2代码生成 新增
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "新增测试表2代码生成数据", notes = "新增测试表2代码生成数据")
    @PreAuthorize("hasAuthority('testtable_insert')")
    @OperateLogger(description = "新增测试表2代码生成数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> insert(TestCarCopy2Model model) {
        // 调用新增方法
        IService.insert(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("新增测试表2代码生成成功");
    }

    /**
     * 测试表2代码生成 修改
     * @param model 模型
     * @return ResultWrapper
     */
    @ApiOperation(value = "修改测试表2代码生成数据", notes = "修改测试表2代码生成数据")
    @PreAuthorize("hasAuthority('testtable_update')")
    @OperateLogger(description = "修改测试表2代码生成数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> update(TestCarCopy2Model model) {
        // 调用修改方法
        IService.update(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("修改测试表2代码生成成功");
    }


    /**
     * 测试表2代码生成 删除
     * @param id ID
     * @return ResultVo
     */
    @ApiOperation(value = "删除测试表2代码生成数据", notes = "删除测试表2代码生成数据")
    @PreAuthorize("hasAuthority('testtable_delete')")
    @OperateLogger(description = "删除测试表2代码生成数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> del(String id){
        IService.delete(id);
        return ResultWrapper.getSuccessResultWrapperByMsg("删除测试表2代码生成成功");
    }

    /**
     * 测试表2代码生成 批量删除
     * @param ids ID 数组
     * @return ResultVo
     */
    @ApiOperation(value = "批量删除测试表2代码生成数据", notes = "批量删除测试表2代码生成数据")
    @PreAuthorize("hasAuthority('testtable_delete')")
    @OperateLogger(description = "批量删除测试表2代码生成数据",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> delAll(String ids){
        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);
        return ResultWrapper.getSuccessResultWrapperByMsg("批量删除测试表2代码生成成功");
    }

    /**
     * 测试表2代码生成 Excel 导出认证
     *
     * @param type 类型
     * @param request request
     */
    @ApiOperation(value = "Excel 导出认证", notes = "Excel 导出认证")
    @PreAuthorize("hasAnyAuthority('testtable_export', 'testtable_import')")
    @Override
    public ResultWrapper<String> exportExcelAuth(String type, HttpServletRequest request) {
        Optional<String> certificateOptional =
                super.excelExportAuth(type, TestCarCopy2RestApi.SUB_TITLE, request);
        if(!certificateOptional.isPresent()){
            return ResultWrapper.getErrorResultWrapper();
        }
        return ResultWrapper.getSuccessResultWrapper(certificateOptional.get());
    }


    /**
     * 测试表2代码生成 Excel 导出
     * @param response response
     */
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @PreAuthorize("hasAuthority('testtable_export')")
    @OperateLogger(description = "测试表2代码生成 导出Excel",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.SELECT, db = true)
    @Override
    public void exportExcel(String certificate, HttpServletResponse response) {
        // 导出Excel
        super.excelExport(certificate, response);
    }

    /**
     * 测试表2代码生成 Excel 导入
     * 注：这里 RequiresPermissions 引入的是 Shiro原生鉴权注解
     * @param request 文件流 request
     * @return ResultVo
     */
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    @PreAuthorize("hasAuthority('testtable_import')")
    @OperateLogger(description = "测试表2代码生成 Excel 导入",
            module = ModuleEnum.MODULE_UNKNOWN, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }

}