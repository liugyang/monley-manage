<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    :close-on-click-modal="false"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="105px">
      <el-row :gutter="10" >
        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="选股ID" prop="choosenStockId">
            <el-input v-model="form.choosenStockId" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="指标ID" prop="indicatorId">
            <el-input v-model="form.indicatorId" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="日期" prop="choosenDate">
            <el-date-picker
                    v-model="form.choosenDate"
                    type="date"
                    placeholder="选择日期"
                    style="width: 100%"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="时间计数" prop="dateCount">
            <el-input v-model="form.dateCount" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="值" prop="value">
            <el-input v-model="form.value" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
          <el-form-item label="说明" prop="note">
            <el-input v-model="form.note" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>

      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { doInsert, doUpdate } from "@/api/indicatorscores/ScoreManagementApi";
  import { isNull } from "@/utils/validate";
  import { formateDate } from "@/utils/format";
  import { validatorRule } from "@/utils/validateRlue";

  export default {
    name: "ScoreManagementEdit",
    data() {

      return {
        form: {
          // 设置默认值
          version: 0
        },
        dict: {},
        rules: {
              choosenStockId: [
                { required: true, trigger: "blur", message: "选股ID非空" },
                { required: false, trigger: "blur", validator: validatorRule.IS_INTEGER },
              ],
              indicatorId: [
                { required: true, trigger: "blur", message: "指标ID非空" },
                { required: false, trigger: "blur", validator: validatorRule.IS_INTEGER },
              ],
              choosenDate: [
                { required: true, trigger: "blur", message: "日期非空" },
              ],
              value: [
                { required: true, trigger: "blur", message: "值非空" },
              ],
        },
        title: "",
        dialogFormVisible: false,
      };
    },
    created() {

    },
    mounted() {
      // 加载字典值
    },
    methods: {
      showEdit(row) {
        if (!row) {
          this.title = "添加";
        } else {
          this.title = "编辑";
          this.form = Object.assign({}, row);
        }
        this.dialogFormVisible = true;
      },
      close() {
        this.dialogFormVisible = false;
        this.$refs["form"].resetFields();
        this.form = this.$options.data().form;
      },
      save() {
        this.$refs["form"].validate(async (valid) => {
          if (valid) {
            // 处理数据
            this.handlerFormData(this.form);

            // 修改
            if (!isNull(this.form.id)) {
              const { msg } = await doUpdate(this.form);
  			  this.$baseMessage(msg, "success");
            } else {
              const { msg } = await doInsert(this.form);
              this.$baseMessage(msg, "success");
            }

            await this.$emit("fetchData");
            this.close();
          } else {
            return false;
          }
        });
      },
      // 处理 form数据
      handlerFormData(formData){
        if(!isNull(formData)){
          for(let key in formData){
            // 对于时间类进行处理
            if("[object Date]" === Object.prototype.toString.call(formData[key])){
              formData[key] = formateDate(formData[key], 'yyyy-MM-dd hh:mm:ss');
            }
          }
        }
      },
    },
  };
</script>
