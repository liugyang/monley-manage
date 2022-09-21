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
<!--        <el-button-->
<!--            v-if="$perms('indicatorscores_insert')"-->
<!--            icon="el-icon-plus"-->
<!--            type="primary"-->
<!--            @click="handleInsert"-->
<!--        > 添加 </el-button>-->

<!--        <el-button-->
<!--            v-if="$perms('indicatorscores_import')"-->
<!--            icon="el-icon-upload2"-->
<!--            type="warning"-->
<!--            @click="handleImportExcel"-->
<!--        > 导入 </el-button>-->

        <el-button
            v-if="$perms('indicatorscores_export')"
            icon="el-icon-download"
            type="warning"
            @click="handleExportExcel"
        > 导出 </el-button>

<!--        <el-button-->
<!--            v-if="$perms('indicatorscores_delete')"-->
<!--            :disabled="!selectRows.length > 0"-->
<!--            icon="el-icon-delete"-->
<!--            type="danger"-->
<!--            @click="handleDelete"-->
<!--        > 批量删除 </el-button>-->

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="14">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>

          <el-form-item>
            <el-date-picker
                    v-model="choosenDateDatePicker"
                    type="daterange"
                    align="right"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>

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
              prop="choosenStockId"
              label="选股ID"
      ></el-table-column>
      <el-table-column
        v-for="col in extCol"
        show-overflow-tooltip
        :key="col.indicatorId"
        :label="col.note"
      >
        {{this.tableData.get(col.choosenStockId)(col.indicatorId)}}
<!--        {{tableData[col.choosenStockId][col.indicatorId]}}-->
      </el-table-column>
<!--      <template v-for="indicator in scoreList">-->
<!--        <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="indicator.value"-->
<!--                label="indicator.indicatorId"-->
<!--        ></el-table-column>-->
<!--      </template>-->

      <el-table-column
              show-overflow-tooltip
              prop="choosenDate"
              label="日期"
      ></el-table-column>

      <el-table-column
              show-overflow-tooltip
              prop="value"
              label="值"
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        label="操作"
        width="200"
        v-if="$perms('indicatorscores_update') || $perms('indicatorscores_delete')"
      >
        <template v-slot="scope">
          <el-button
            v-if="$perms('indicatorscores_update')"
            type="text"
            @click="handleUpdate(scope.row)"
          > 编辑 </el-button>

          <el-divider direction="vertical"></el-divider>

          <el-button
            v-if="$perms('indicatorscores_delete')"
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
  import { getList, doDelete, doDeleteAll, doExportExcel } from "@/api/indicatorscores/ScoreManagementApi";
  import Edit from "./components/ScoreManagementEdit";
  import Import from "./components/ScoreManagementImport";

  import { vueButtonClickBan } from "@/utils";
  import { isNotNull } from "@/utils/valiargs";
  import { formateDate } from "@/utils/format";

  export default {
    name: "ScoreManagement",
    components: { Edit, Import },
    data() {
      return {
        list: null,
        tableData: [],
        extCol:[],
        listLoading: true,
        layout: "total, prev, pager, next, sizes, jumper",
        total: 0,
        selectRows: "",
        elementLoadingText: "正在加载...",
        moreQueryFlag: false,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          choosenDate_BEGIN: formateDate(new Date(new Date().toLocaleDateString()), 'yyyy-MM-dd hh:mm:ss'),
          choosenDate_END: formateDate(new Date(new Date(new Date().toLocaleDateString()).getTime()+86400000), 'yyyy-MM-dd hh:mm:ss'),
        },
        choosenDateDatePicker: [new Date(new Date().toLocaleDateString()),new Date(new Date(new Date().toLocaleDateString()).getTime()+86400000)],
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
        if(isNotNull(this.choosenDateDatePicker) && this.choosenDateDatePicker.length === 2){
          let sDt = new Date(this.choosenDateDatePicker[0].toLocaleDateString());
          let eDt = new Date(new Date(this.choosenDateDatePicker[1].toLocaleDateString()).getTime()+86400000);
          this.queryForm.choosenDate_BEGIN =
                  this.choosenDateDatePicker.length === 0 ? "" : formateDate(sDt, 'yyyy-MM-dd hh:mm:ss');
          this.queryForm.choosenDate_END =
                  this.choosenDateDatePicker.length === 0 ? "" : formateDate(eDt, 'yyyy-MM-dd hh:mm:ss');
        }else{
          let sDt = new Date(new Date().toLocaleDateString()).getTime();
          let eDt = new Date(new Date().toLocaleDateString()).getTime()+86400000;
          this.queryForm.choosenDate_BEGIN = formateDate(sDt, 'yyyy-MM-dd hh:mm:ss');
          this.queryForm.choosenDate_END = formateDate(eDt, 'yyyy-MM-dd hh:mm:ss');
        }

        this.queryForm.pageNo = 1;
        this.fetchData();
      },
      async fetchData() {
        this.listLoading = true;
        const { data } = await getList(this.queryForm);
        if(isNotNull(data)){
          this.list = data.rows;
          this.total = data.total;

          if(this.list.length > 0) {
            this.extCol = this.list[0].scoreList;
            this.tableData = new Map();
            for (let i=0;i<this.list.length;i++) {
              this.tableData.set(this.list[i].choosenStockId, new Map());
              for(let j=0;j<this.list[i].scoreList.length;j++) {
                this.tableData.get(this.list[i].choosenStockId).set(this.list[i].scoreList[j].id,this.list[i].scoreList[j].value);
              }
            }
          }
          console.log(this.tableData)
        }
        setTimeout(() => {
            this.listLoading = false;
        }, 300);
      },
    },
  };
</script>
