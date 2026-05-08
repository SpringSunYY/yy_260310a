<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                  <el-form-item label="编号" prop="id">
                    <el-input
                        v-model="queryParams.id"
                        placeholder="请输入编号"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="备件编码" prop="partsCode">
                    <el-input
                        v-model="queryParams.partsCode"
                        placeholder="请输入备件编码"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="备件名称" prop="partsName">
                    <el-input
                        v-model="queryParams.partsName"
                        placeholder="请输入备件名称"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="预警类型" prop="warnType">
                    <el-select v-model="queryParams.warnType" style="width: 200px" placeholder="请选择预警类型" clearable>
                      <el-option
                          v-for="dict in warehouse_warning_type"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="预警状态" prop="warningStatus">
                    <el-select v-model="queryParams.warningStatus" style="width: 200px" placeholder="请选择预警状态" clearable>
                      <el-option
                          v-for="dict in warehouse_warning_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="创建人" prop="createBy">
                    <el-input
                        v-model="queryParams.createBy"
                        placeholder="请输入创建人"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="创建时间" style="width: 308px">
                    <el-date-picker
                        v-model="daterangeCreateTime"
                        value-format="YYYY-MM-DD"
                        type="daterange"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                    ></el-date-picker>
                  </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['manage:warningInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:warningInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:warningInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:warningInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warningInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="备件编码" align="center" prop="partsCode" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="备件名称" align="center" prop="partsName" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="预警类型" align="center" prop="warnType" v-if="columns[3].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_warning_type" :value="scope.row.warnType"/>
                </template>
              </el-table-column>
                <el-table-column label="当前库存" align="center" prop="currentStock" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="预警阈值" align="center" prop="thresholdStock" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="差异数量" align="center" prop="differenceStock" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="预警状态" align="center" prop="warningStatus" v-if="columns[7].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_warning_status" :value="scope.row.warningStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="处理方式" align="center" prop="handlingMethod" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="处理时间" align="center" prop="handingDate" width="180" v-if="columns[9].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.handingDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="处理人" align="center" prop="handingUserId" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="处理描述" align="center" prop="handingRemark" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[13].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[14].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[15].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="备注" align="center" prop="remark" v-if="columns[16].visible" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:warningInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:warningInfo:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改库存预警对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="warningInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="备件编码" prop="partsCode">
                          <el-input v-model="form.partsCode" placeholder="请输入备件编码" />
                        </el-form-item>
                        <el-form-item label="备件名称" prop="partsName">
                          <el-input v-model="form.partsName" placeholder="请输入备件名称" />
                        </el-form-item>
                        <el-form-item label="预警类型" prop="warnType">
                          <el-select v-model="form.warnType" placeholder="请选择预警类型">
                            <el-option
                                v-for="dict in warehouse_warning_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="当前库存" prop="currentStock">
                          <el-input v-model="form.currentStock" placeholder="请输入当前库存" />
                        </el-form-item>
                        <el-form-item label="预警阈值" prop="thresholdStock">
                          <el-input v-model="form.thresholdStock" placeholder="请输入预警阈值" />
                        </el-form-item>
                        <el-form-item label="差异数量" prop="differenceStock">
                          <el-input v-model="form.differenceStock" placeholder="请输入差异数量" />
                        </el-form-item>
                        <el-form-item label="预警状态" prop="warningStatus">
                          <el-select v-model="form.warningStatus" placeholder="请选择预警状态">
                            <el-option
                                v-for="dict in warehouse_warning_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="处理方式" prop="handlingMethod">
                          <el-input v-model="form.handlingMethod" placeholder="请输入处理方式" />
                        </el-form-item>
                        <el-form-item label="处理时间" prop="handingDate">
                          <el-date-picker clearable
                                          v-model="form.handingDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择处理时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="处理人" prop="handingUserId">
                          <el-input v-model="form.handingUserId" placeholder="请输入处理人" />
                        </el-form-item>
                        <el-form-item label="处理描述" prop="handingRemark">
                          <el-input v-model="form.handingRemark" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="备注" prop="remark">
                          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="WarningInfo">
  import { listWarningInfo, getWarningInfo, delWarningInfo, addWarningInfo, updateWarningInfo } from "@/api/manage/warningInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_warning_status, warehouse_warning_type } = proxy.useDict('warehouse_warning_status', 'warehouse_warning_type');

  const warningInfoList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
          const daterangeCreateTime = ref([]);

  const data = reactive({
    form: {},
    // 导出地址
    exportUrl: 'manage/warningInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    partsCode: null,
                    partsName: null,
                    warnType: null,
                    warningStatus: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    partsCode: [
                { required: true, message: "备件编码不能为空", trigger: "blur" }
              ],
                    partsName: [
                { required: true, message: "备件名称不能为空", trigger: "blur" }
              ],
                    warnType: [
                { required: true, message: "预警类型不能为空", trigger: "change" }
              ],
                    currentStock: [
                { required: true, message: "当前库存不能为空", trigger: "blur" }
              ],
                    thresholdStock: [
                { required: true, message: "预警阈值不能为空", trigger: "blur" }
              ],
                    differenceStock: [
                { required: true, message: "差异数量不能为空", trigger: "blur" }
              ],
                    warningStatus: [
                { required: true, message: "预警状态不能为空", trigger: "change" }
              ],
                    createBy: [
                { required: true, message: "创建人不能为空", trigger: "blur" }
              ],
                    createTime: [
                { required: true, message: "创建时间不能为空", trigger: "blur" }
              ],
    },
    //表格展示列
    columns: [
              { key: 0, label: '编号', visible: true },
                { key: 1, label: '备件编码', visible: true },
                { key: 2, label: '备件名称', visible: true },
                { key: 3, label: '预警类型', visible: true },
                { key: 4, label: '当前库存', visible: true },
                { key: 5, label: '预警阈值', visible: true },
                { key: 6, label: '差异数量', visible: true },
                { key: 7, label: '预警状态', visible: true },
                { key: 8, label: '处理方式', visible: true },
                { key: 9, label: '处理时间', visible: true },
                { key: 10, label: '处理人', visible: true },
                { key: 11, label: '处理描述', visible: true },
                { key: 12, label: '创建人', visible: true },
                { key: 13, label: '创建时间', visible: true },
                { key: 14, label: '更新人', visible: true },
                { key: 15, label: '更新时间', visible: true },
                { key: 16, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询库存预警列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listWarningInfo(queryParams.value).then(response => {
            warningInfoList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    });
  }

  // 取消按钮
  function cancel() {
    open.value = false;
    reset();
  }

  // 表单重置
  function reset() {
    form.value = {
                    id: null,
                    partsCode: null,
                    partsName: null,
                    warnType: null,
                    currentStock: null,
                    thresholdStock: null,
                    differenceStock: null,
                    warningStatus: null,
                    handlingMethod: null,
                    handingDate: null,
                    handingUserId: null,
                    handingRemark: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("warningInfoRef");
  }

  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
  }

  /** 重置按钮操作 */
  function resetQuery() {
            daterangeCreateTime.value = [];
    proxy.resetForm("queryRef");
    handleQuery();
  }

  // 多选框选中数据
  function handleSelectionChange(selection) {
    ids.value = selection.map(item => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
  }

  /** 新增按钮操作 */
  function handleAdd() {
    reset();
    open.value = true;
    title.value = "添加库存预警";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getWarningInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改库存预警";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["warningInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateWarningInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addWarningInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("新增成功");
            open.value = false;
            getList();
          });
        }
      }
    });
  }

  /** 删除按钮操作 */
  function handleDelete(row) {
    const _ids = row.id || ids.value;
    proxy.$modal.confirm('是否确认删除库存预警编号为"' + _ids + '"的数据项？').then(function() {
      return delWarningInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `warningInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
