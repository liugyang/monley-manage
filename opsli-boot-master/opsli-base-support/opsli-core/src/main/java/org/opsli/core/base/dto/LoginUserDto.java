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
package org.opsli.core.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 当前登录用户信息
 *
 * @author Parker
 * @date 2021年12月22日16:22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String uid;

    /** 用户名 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 租户ID */
    private String tenantId;

    /** 手机 */
    private String mobile;

    /** 邮箱 */
    private String email;

    /** 登录来源: 0:PC端；1:APP-安卓 2:APP-IOS 3:小程序 */
    private String loginFrom;

    /** 登录ip */
    private String loginIp;
}
