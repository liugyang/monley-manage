import request from "@/utils/request";
import { downloadFileByData } from "@/utils/download";

export function getList(data) {
  return request({
    url: "/api/v1/testtable/findPage",
    method: "get",
    params: data,
  });
}

export function doInsert(data) {
  return request({
    url: "/api/v1/testtable/insert",
    method: "post",
    data,
  });
}

export function doUpdate(data) {
  return request({
    url: "/api/v1/testtable/update",
    method: "post",
    data,
  });
}

export function doDelete(data) {
  return request({
    url: "/api/v1/testtable/del",
    method: "post",
    params: data,
  });
}

export function doDeleteAll(data) {
  return request({
    url: "/api/v1/testtable/delAll",
    method: "post",
    params: data,
  });
}

/**
 * 导出Excel 目前只支持一层参数传递
 * @param params 参数
 * @returns file
 */
 export async function doExportExcel(params) {
    let authURL = "/api/v1/testtable/excel/auth/export";

    let downloadURL = "/api/v1/testtable/excel/export/";


    // 认证
    const { data } = await request({
      url: authURL,
      method: "get",
      params: params,
    });
  
    if (data) {
      // 下载文件
      downloadFileByData(downloadURL + data, params);
    }
  }
  
  /**
   * 下载模版
   * @returns file
   */
  export async function doDownloadTemplate() {
    let authURL = "/api/v1/testtable/excel/auth/import-template-export";

    let downloadURL = "/api/v1/testtable/excel/export/";

    // 认证
    const { data } = await request({
      url: authURL,
      method: "get",
    });
  
    if (data) {
      // 下载文件
      downloadFileByData(downloadURL + data, {});
    }
  }


/**
 * 导入Excel
 * @returns file
 */
export function doImportExcel(data) {
  return request({
    url: "/api/v1/testtable/importExcel",
    method: "post",
    // 最长超时时间 3 分钟
    timeout: 180000,
    headers: {
      "Content-Type": "multipart/form-data"
    },
    data,
  });
}