<template>
  <div class="tenantManagement-container">

    <el-collapse-transition>
    <div class="more-query" v-show="this.moreQueryFlag">
      <!-- 更多查找 -->
      <vab-query-form>
        <vab-query-form-left-panel :span="24">
          <el-form :inline="true" :model="queryForm" @submit.native.prevent>


          </el-form>
        </vab-query-form-left-panel>

      </vab-query-form>
      <el-divider></el-divider>
    </div>
    </el-collapse-transition>

    <!-- 主要操作  -->
    <vab-query-form>
      <vab-query-form-left-panel :span="10">
        <el-button
            v-if="$perms('testtable_insert')"
            icon="el-icon-plus"
            type="primary"
            @click="handleInsert"
        > 添加 </el-button>

        <el-button
            v-if="$perms('testtable_import')"
            icon="el-icon-upload2"
            type="warning"
            @click="handleImportExcel"
        > 导入 </el-button>

        <el-button
            v-if="$perms('testtable_export')"
            icon="el-icon-download"
            type="warning"
            @click="handleExportExcel"
        > 导出 </el-button>

        <el-button
            v-if="$perms('testtable_delete')"
            :disabled="!selectRows.length > 0"
            icon="el-icon-delete"
            type="danger"
            @click="handleDelete"
        > 批量删除 </el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="14">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>

          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>


          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
    >
      <el-table-column show-overflow-tooltip type="selection"></el-table-column>

      <el-table-column show-overflow-tooltip label="序号" width="95">
        <template slot-scope="scope">
          {{(queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1}}
        </template>
      </el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="carName"
              label="汽车名称"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="carType"
              label="汽车类型"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="carBrand"
              label="汽车品牌"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="produceData"
              label="生产日期"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="izUsable"
              label="是否启用"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="tenantId"
              label="多租户ID"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="deleted"
              label="删除标记:0未删除，1删除"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="ts"
              label="时间戳"
      ></el-table-column>


      <el-table-column
        show-overflow-tooltip
        label="操作"
        width="200"
        v-if="$perms('testtable_update') || $perms('testtable_delete')"
      >
        <template v-slot="scope">
          <el-button
            v-if="$perms('testtable_update')"
            type="text"
            @click="handleUpdate(scope.row)"
          > 编辑 </el-button>
          
          <el-divider direction="vertical"></el-divider>
          
          <el-button
            v-if="$perms('testtable_delete')"
            type="text"
            @click="handleDelete(scope.row)"
          > 删除 </el-button>
        </template>

      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    ></el-pagination>

    <edit ref="edit" @fetchData="fetchData"></edit>
    <import ref="import" @fetchData="fetchData" ></import>

  </div>
</template>

<script>
  import { getList, doDelete, doDeleteAll, doExportExcel } from "@/api/testtable/TestCarCopy2ManagementApi";
  import Edit from "./components/TestCarCopy2ManagementEdit";
  import Import from "./components/TestCarCopy2ManagementImport";

  import { vueButtonClickBan } from "@/utils";
  import { isNotNull } from "@/utils/valiargs";
  import { formateDate } from "@/utils/format";

  export default {
    name: "TestCarCopy2Management",
    components: { Edit, Import },
    data() {
      return {
        list: null,
        listLoading: true,
        layout: "total, prev, pager, next, sizes, jumper",
        total: 0,
        selectRows: "",
        elementLoadingText: "正在加载...",
        moreQueryFlag: false,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
        },
        dict:{},
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
      };
    },
    created() {
      this.fetchData();
    },
    mounted() {
    },
    methods: {
      setSelectRows(val) {
        this.selectRows = val;
      },
      handleInsert(row) {
        this.$refs["edit"].showEdit();
      },
      handleUpdate(row) {
        if (row.id) {
          this.$refs["edit"].showEdit(row);
        }
      },
      handleDelete(row) {
        if (row.id) {
          this.$baseConfirm("你确定要删除当前项吗", null, async () => {
            const { msg } = await doDelete({ id: row.id });
            this.$baseMessage(msg, "success");
            await this.fetchData();
          });
        } else {
          if (this.selectRows.length > 0) {
            const ids = this.selectRows.map((item) => item.id).join();
            this.$baseConfirm("你确定要删除选中项吗", null, async () => {
              const { msg } = await doDeleteAll({ ids });
              this.$baseMessage(msg, "success");
              await this.fetchData();
            });
          } else {
            this.$baseMessage("未选中任何行", "error");
            return false;
          }
        }
      },
      // 导出excel
      handleExportExcel(el){
        // 导出按钮防抖处理 默认限制为10秒
        vueButtonClickBan(el, 10);

        // 执行导出
        doExportExcel(this.queryForm);
      },
      // 导入excel
      handleImportExcel(){
        this.$refs["import"].show();
      },


      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData();
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData();
      },
      moreQuery(){
        this.moreQueryFlag = !this.moreQueryFlag;
      },
      queryData() {

        this.queryForm.pageNo = 1;
        this.fetchData();
      },
      async fetchData() {
        this.listLoading = true;
        const { data } = await getList(this.queryForm);
        if(isNotNull(data)){
          this.list = data.rows;
          this.total = data.total;
        }
        setTimeout(() => {
            this.listLoading = false;
        }, 300);
      },
    },
  };
</script>
