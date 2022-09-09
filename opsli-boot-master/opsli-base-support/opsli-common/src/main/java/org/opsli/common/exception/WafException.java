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
package org.opsli.common.exception;

import org.opsli.common.base.msg.BaseMsg;

/**
 * Java 防火墙 异常类
 *
 * @author Parker
 * @date 2020-10-09
 */
public class WafException extends ServiceException {

	public WafException(Integer code, String errorMessage) {
		super(code, errorMessage);
	}

	public WafException(BaseMsg msg) {
		super(msg);
	}
}
