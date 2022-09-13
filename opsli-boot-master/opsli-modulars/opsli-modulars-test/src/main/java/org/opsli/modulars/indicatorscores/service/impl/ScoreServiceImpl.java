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
package org.opsli.modulars.indicatorscores.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.opsli.core.base.service.impl.CrudServiceImpl;

import org.opsli.modulars.indicatorscores.entity.Score;
import org.opsli.api.wrapper.indicatorscores.ScoreModel;
import org.opsli.modulars.indicatorscores.service.IScoreService;
import org.opsli.modulars.indicatorscores.mapper.ScoreMapper;


/**
 * 选股评分 Service Impl
 *
 * @author liugy
 * @date 2022-09-08 21:43:19
 */
@Service
public class ScoreServiceImpl extends CrudServiceImpl<ScoreMapper, Score, ScoreModel>
    implements IScoreService {

    @Autowired(required = false)
    private ScoreMapper mapper;

}