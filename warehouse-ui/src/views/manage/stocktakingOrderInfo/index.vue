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
                  <el-form-item label="盘点单号" prop="stocktakingNo">
                    <el-input
                        v-model="queryParams.stocktakingNo"
                        placeholder="请输入盘点单号"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="盘点类型" prop="stocktakingType">
                    <el-select v-model="queryParams.stocktakingType" style="width: 200px" placeholder="请选择盘点类型" clearable>
                      <el-option
                          v-for="dict in warehouse_stocktaking_type"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="仓库" prop="warehouseId">
                    <el-input
                        v-model="queryParams.warehouseId"
                        placeholder="请输入仓库"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="盘点状态" prop="stocktakingStatus">
                    <el-select v-model="queryParams.stocktakingStatus" style="width: 200px" placeholder="请选择盘点状态" clearable>
                      <el-option
                          v-for="dict in warehouse_stocktaking_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="审核状态" prop="reviewStatus">
                    <el-select v-model="queryParams.reviewStatus" style="width: 200px" placeholder="请选择审核状态" clearable>
                      <el-option
                          v-for="dict in warehouse_applicant_status"
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
            v-hasPermi="['manage:stocktakingOrderInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:stocktakingOrderInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:stocktakingOrderInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:stocktakingOrderInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stocktakingOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="盘点单号" align="center" prop="stocktakingNo" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="盘点类型" align="center" prop="stocktakingType" v-if="columns[2].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_stocktaking_type" :value="scope.row.stocktakingType"/>
                </template>
              </el-table-column>
                <el-table-column label="仓库" align="center" prop="warehouseId" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="盘点开始日期" align="center" prop="startDate" width="180" v-if="columns[4].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="盘点结束日期" align="center" prop="endDate" width="180" v-if="columns[5].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="盘点状态" align="center" prop="stocktakingStatus" v-if="columns[6].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_stocktaking_status" :value="scope.row.stocktakingStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="盘点人" align="center" prop="operatorId" v-if="columns[7].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="审核人" align="center" prop="reviewerId" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="审核状态" align="center" prop="reviewStatus" v-if="columns[9].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_applicant_status" :value="scope.row.reviewStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="审核时间" align="center" prop="reviewTime" width="180" v-if="columns[10].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="审批描述" align="center" prop="reviewRemark" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:stocktakingOrderInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:stocktakingOrderInfo:remove']">删除</el-button>
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

    <!-- 添加或修改盘点单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="stocktakingOrderInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="盘点单号" prop="stocktakingNo">
                          <el-input v-model="form.stocktakingNo" placeholder="请输入盘点单号" />
                        </el-form-item>
                        <el-form-item label="盘点类型" prop="stocktakingType">
                          <el-select v-model="form.stocktakingType" placeholder="请选择盘点类型">
                            <el-option
                                v-for="dict in warehouse_stocktaking_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="仓库" prop="warehouseId">
                          <el-input v-model="form.warehouseId" placeholder="请输入仓库" />
                        </el-form-item>
                        <el-form-item label="盘点开始日期" prop="startDate">
                          <el-date-picker clearable
                                          v-model="form.startDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择盘点开始日期">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="盘点结束日期" prop="endDate">
                          <el-date-picker clearable
                                          v-model="form.endDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择盘点结束日期">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="盘点状态" prop="stocktakingStatus">
                          <el-select v-model="form.stocktakingStatus" placeholder="请选择盘点状态">
                            <el-option
                                v-for="dict in warehouse_stocktaking_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="盘点人" prop="operatorId">
                          <el-input v-model="form.operatorId" placeholder="请输入盘点人" />
                        </el-form-item>
                        <el-form-item label="审核人" prop="reviewerId">
                          <el-input v-model="form.reviewerId" placeholder="请输入审核人" />
                        </el-form-item>
                        <el-form-item label="审核状态" prop="reviewStatus">
                          <el-radio-group v-model="form.reviewStatus">
                            <el-radio
                                v-for="dict in warehouse_applicant_status"
                                :key="dict.value"
                                :value="dict.value"
                            >{{dict.label}}</el-radio>
                          </el-radio-group>
                        </el-form-item>
                        <el-form-item label="审核时间" prop="reviewTime">
                          <el-date-picker clearable
                                          v-model="form.reviewTime"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择审核时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="审批描述" prop="reviewRemark">
                          <el-input v-model="form.reviewRemark" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="备注" prop="remark">
                          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
            <el-divider content-position="center">盘点明细信息</el-divider>
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="Plus" @click="handleAddStocktakingOrderDetailInfo">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="Delete" @click="handleDeleteStocktakingOrderDetailInfo">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="stocktakingOrderDetailInfoList" :row-class-name="rowStocktakingOrderDetailInfoIndex" @selection-change="handleStocktakingOrderDetailInfoSelectionChange" ref="stocktakingOrderDetailInfo">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
                      <el-table-column label="备件编号" prop="partsCode" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.partsCode" placeholder="请输入备件编号" />
                        </template>
                      </el-table-column>
                      <el-table-column label="仓库" prop="warehouseId" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.warehouseId" placeholder="请输入仓库" />
                        </template>
                      </el-table-column>
                      <el-table-column label="库位" prop="locationId" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.locationId" placeholder="请输入库位" />
                        </template>
                      </el-table-column>
                      <el-table-column label="批次号" prop="batchNo" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.batchNo" placeholder="请输入批次号" />
                        </template>
                      </el-table-column>
                      <el-table-column label="账面数量" prop="bookQuantity" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.bookQuantity" placeholder="请输入账面数量" />
                        </template>
                      </el-table-column>
                      <el-table-column label="实际盘点数量" prop="actualQuantity" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.actualQuantity" placeholder="请输入实际盘点数量" />
                        </template>
                      </el-table-column>
                      <el-table-column label="盈亏数量" prop="differenceQuantity" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.differenceQuantity" placeholder="请输入盈亏数量" />
                        </template>
                      </el-table-column>
                      <el-table-column label="差异原因" prop="differenceReason" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.differenceReason" placeholder="请输入差异原因" />
                        </template>
                      </el-table-column>
            </el-table>
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

<script setup name="StocktakingOrderInfo">
  import { listStocktakingOrderInfo, getStocktakingOrderInfo, delStocktakingOrderInfo, addStocktakingOrderInfo, updateStocktakingOrderInfo } from "@/api/manage/stocktakingOrderInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_stocktaking_status, warehouse_stocktaking_type, warehouse_applicant_status } = proxy.useDict('warehouse_stocktaking_status', 'warehouse_stocktaking_type', 'warehouse_applicant_status');

  const stocktakingOrderInfoList = ref([]);
      const stocktakingOrderDetailInfoList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
      const checkedStocktakingOrderDetailInfo = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
          const daterangeCreateTime = ref([]);

  const data = reactive({
    form: {},
    // 导出地址
    exportUrl: 'manage/stocktakingOrderInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    stocktakingNo: null,
                    stocktakingType: null,
                    warehouseId: null,
                    stocktakingStatus: null,
                    reviewStatus: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    stocktakingNo: [
                { required: true, message: "盘点单号不能为空", trigger: "blur" }
              ],
                    stocktakingType: [
                { required: true, message: "盘点类型不能为空", trigger: "change" }
              ],
                    startDate: [
                { required: true, message: "盘点开始日期不能为空", trigger: "blur" }
              ],
                    stocktakingStatus: [
                { required: true, message: "盘点状态不能为空", trigger: "change" }
              ],
                    reviewStatus: [
                { required: true, message: "审核状态不能为空", trigger: "change" }
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
                { key: 1, label: '盘点单号', visible: true },
                { key: 2, label: '盘点类型', visible: true },
                { key: 3, label: '仓库', visible: true },
                { key: 4, label: '盘点开始日期', visible: true },
                { key: 5, label: '盘点结束日期', visible: true },
                { key: 6, label: '盘点状态', visible: true },
                { key: 7, label: '盘点人', visible: true },
                { key: 8, label: '审核人', visible: true },
                { key: 9, label: '审核状态', visible: true },
                { key: 10, label: '审核时间', visible: true },
                { key: 11, label: '审批描述', visible: true },
                { key: 12, label: '创建人', visible: true },
                { key: 13, label: '创建时间', visible: true },
                { key: 14, label: '更新人', visible: true },
                { key: 15, label: '更新时间', visible: true },
                { key: 16, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询盘点单列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listStocktakingOrderInfo(queryParams.value).then(response => {
            stocktakingOrderInfoList.value = response.rows;
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
                    stocktakingNo: null,
                    stocktakingType: null,
                    warehouseId: null,
                    startDate: null,
                    endDate: null,
                    stocktakingStatus: null,
                    operatorId: null,
                    reviewerId: null,
                    reviewStatus: null,
                    reviewTime: null,
                    reviewRemark: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
              stocktakingOrderDetailInfoList.value = [];
    proxy.resetForm("stocktakingOrderInfoRef");
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
    title.value = "添加盘点单";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getStocktakingOrderInfo(_id).then(response => {
      form.value = response.data;
                stocktakingOrderDetailInfoList.value = response.data.stocktakingOrderDetailInfoList;
      open.value = true;
      title.value = "修改盘点单";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["stocktakingOrderInfoRef"].validate(valid => {
      if (valid) {
        form.value.stocktakingOrderDetailInfoList = stocktakingOrderDetailInfoList.value;
        if (form.value.id != null) {
          updateStocktakingOrderInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addStocktakingOrderInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除盘点单编号为"' + _ids + '"的数据项？').then(function() {
      return delStocktakingOrderInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 盘点明细序号 */
  function rowStocktakingOrderDetailInfoIndex({ row, rowIndex }) {
    row.index = rowIndex + 1;
  }

  /** 盘点明细添加按钮操作 */
  function handleAddStocktakingOrderDetailInfo() {
    let obj = {};
    obj.partsCode = "";
    obj.warehouseId = "";
    obj.locationId = "";
    obj.batchNo = "";
    obj.bookQuantity = "";
    obj.actualQuantity = "";
    obj.differenceQuantity = "";
    obj.differenceReason = "";
    obj.remark = "";
    stocktakingOrderDetailInfoList.value.push(obj);
  }

  /** 盘点明细删除按钮操作 */
  function handleDeleteStocktakingOrderDetailInfo() {
    if (checkedStocktakingOrderDetailInfo.value.length == 0) {
      proxy.$modal.msgError("请先选择要删除的盘点明细数据");
    } else {
      const stocktakingOrderDetailInfos = stocktakingOrderDetailInfoList.value;
      const checkedStocktakingOrderDetailInfos = checkedStocktakingOrderDetailInfo.value;
      stocktakingOrderDetailInfoList.value = stocktakingOrderDetailInfos.filter(function(item) {
        return checkedStocktakingOrderDetailInfos.indexOf(item.index) == -1
      });
    }
  }

  /** 复选框选中数据 */
  function handleStocktakingOrderDetailInfoSelectionChange(selection) {
    checkedStocktakingOrderDetailInfo.value = selection.map(item => item.index)
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `stocktakingOrderInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
