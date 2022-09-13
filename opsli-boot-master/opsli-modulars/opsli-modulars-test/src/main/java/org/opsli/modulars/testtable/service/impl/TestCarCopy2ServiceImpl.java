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
package org.opsli.modulars.testtable.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.opsli.core.base.service.impl.CrudServiceImpl;

import org.opsli.modulars.testtable.entity.TestCarCopy2;
import org.opsli.api.wrapper.testtable.TestCarCopy2Model;
import org.opsli.modulars.testtable.service.ITestCarCopy2Service;
import org.opsli.modulars.testtable.mapper.TestCarCopy2Mapper;


/**
 * 测试表2 Service Impl
 *
 * @author test
 * @date 2022-09-07 21:46:01
 */
@Service
public class TestCarCopy2ServiceImpl extends CrudServiceImpl<TestCarCopy2Mapper, TestCarCopy2, TestCarCopy2Model>
    implements ITestCarCopy2Service {

    @Autowired(required = false)
    private TestCarCopy2Mapper mapper;

}