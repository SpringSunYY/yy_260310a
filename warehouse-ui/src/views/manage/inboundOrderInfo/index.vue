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
                  <el-form-item label="入库单号" prop="inboundNo">
                    <el-input
                        v-model="queryParams.inboundNo"
                        placeholder="请输入入库单号"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="入库类型" prop="inboundType">
                    <el-select v-model="queryParams.inboundType" style="width: 200px" placeholder="请选择入库类型" clearable>
                      <el-option
                          v-for="dict in warehouse_inbound_type"
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
                  <el-form-item label="关联订单" prop="orderId">
                    <el-input
                        v-model="queryParams.orderId"
                        placeholder="请输入关联订单"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="供应商" prop="supplierId">
                    <el-input
                        v-model="queryParams.supplierId"
                        placeholder="请输入供应商"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="入库日期" prop="inboundDate">
                    <el-date-picker clearable
                                    v-model="queryParams.inboundDate"
                                    type="date"
                                    value-format="YYYY-MM-DD"
                                    placeholder="请选择入库日期">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="入库状态" prop="inboundStatus">
                    <el-select v-model="queryParams.inboundStatus" style="width: 200px" placeholder="请选择入库状态" clearable>
                      <el-option
                          v-for="dict in warehouse_inbound_status"
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
            v-hasPermi="['manage:inboundOrderInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:inboundOrderInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:inboundOrderInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:inboundOrderInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inboundOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="入库单号" align="center" prop="inboundNo" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="入库类型" align="center" prop="inboundType" v-if="columns[2].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_inbound_type" :value="scope.row.inboundType"/>
                </template>
              </el-table-column>
                <el-table-column label="仓库" align="center" prop="warehouseId" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="关联订单" align="center" prop="orderId" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="供应商" align="center" prop="supplierId" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="入库日期" align="center" prop="inboundDate" width="180" v-if="columns[6].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.inboundDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="入库状态" align="center" prop="inboundStatus" v-if="columns[7].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_inbound_status" :value="scope.row.inboundStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="经办人" align="center" prop="operatorId" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="审核人" align="center" prop="reviewerId" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="审核状态" align="center" prop="reviewStatus" v-if="columns[10].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_applicant_status" :value="scope.row.reviewStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="审核时间" align="center" prop="reviewTime" width="180" v-if="columns[11].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="审批描述" align="center" prop="reviewRemark" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[15].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[16].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="备注" align="center" prop="remark" v-if="columns[17].visible" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:inboundOrderInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:inboundOrderInfo:remove']">删除</el-button>
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

    <!-- 添加或修改入库单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="inboundOrderInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="入库单号" prop="inboundNo">
                          <el-input v-model="form.inboundNo" placeholder="请输入入库单号" />
                        </el-form-item>
                        <el-form-item label="入库类型" prop="inboundType">
                          <el-select v-model="form.inboundType" placeholder="请选择入库类型">
                            <el-option
                                v-for="dict in warehouse_inbound_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="仓库" prop="warehouseId">
                          <el-input v-model="form.warehouseId" placeholder="请输入仓库" />
                        </el-form-item>
                        <el-form-item label="关联订单" prop="orderId">
                          <el-input v-model="form.orderId" placeholder="请输入关联订单" />
                        </el-form-item>
                        <el-form-item label="供应商" prop="supplierId">
                          <el-input v-model="form.supplierId" placeholder="请输入供应商" />
                        </el-form-item>
                        <el-form-item label="入库日期" prop="inboundDate">
                          <el-date-picker clearable
                                          v-model="form.inboundDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择入库日期">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="入库状态" prop="inboundStatus">
                          <el-radio-group v-model="form.inboundStatus">
                            <el-radio
                                v-for="dict in warehouse_inbound_status"
                                :key="dict.value"
                                :value="dict.value"
                            >{{dict.label}}</el-radio>
                          </el-radio-group>
                        </el-form-item>
                        <el-form-item label="经办人" prop="operatorId">
                          <el-input v-model="form.operatorId" placeholder="请输入经办人" />
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
            <el-divider content-position="center">入库明细信息</el-divider>
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button type="primary" icon="Plus" @click="handleAddInboundOrderDetailInfo">添加</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button type="danger" icon="Delete" @click="handleDeleteInboundOrderDetailInfo">删除</el-button>
              </el-col>
            </el-row>
            <el-table :data="inboundOrderDetailInfoList" :row-class-name="rowInboundOrderDetailInfoIndex" @selection-change="handleInboundOrderDetailInfoSelectionChange" ref="inboundOrderDetailInfo">
              <el-table-column type="selection" width="50" align="center" />
              <el-table-column label="序号" align="center" prop="index" width="50"/>
                      <el-table-column label="备件编号" prop="partsCode" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.partsCode" placeholder="请输入备件编号" />
                        </template>
                      </el-table-column>
                      <el-table-column label="入库数量" prop="inboundQuantity" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.inboundQuantity" placeholder="请输入入库数量" />
                        </template>
                      </el-table-column>
                      <el-table-column label="批次号" prop="batchNo" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.batchNo" placeholder="请输入批次号" />
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
                      <el-table-column label="质量状态" prop="qualityStatus" width="150">
                        <template #default="scope">
                          <el-select v-model="scope.row.qualityStatus" placeholder="请选择质量状态">
                            <el-option label="请选择字典生成" value="" />
                          </el-select>
                        </template>
                      </el-table-column>
                      <el-table-column label="生产日期" prop="productionDate" width="240">
                        <template #default="scope">
                          <el-date-picker clearable
                                          v-model="scope.row.productionDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择生产日期">
                          </el-date-picker>
                        </template>
                      </el-table-column>
                      <el-table-column label="有效期至" prop="expiryDate" width="240">
                        <template #default="scope">
                          <el-date-picker clearable
                                          v-model="scope.row.expiryDate"
                                          type="date"
                                          value-format="YYYY-MM-DD"
                                          placeholder="请选择有效期至">
                          </el-date-picker>
                        </template>
                      </el-table-column>
                      <el-table-column label="入库单价" prop="unitPrice" width="150">
                        <template #default="scope">
                          <el-input v-model="scope.row.unitPrice" placeholder="请输入入库单价" />
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

<script setup name="InboundOrderInfo">
  import { listInboundOrderInfo, getInboundOrderInfo, delInboundOrderInfo, addInboundOrderInfo, updateInboundOrderInfo } from "@/api/manage/inboundOrderInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_inbound_type, warehouse_inbound_status, warehouse_applicant_status } = proxy.useDict('warehouse_inbound_type', 'warehouse_inbound_status', 'warehouse_applicant_status');

  const inboundOrderInfoList = ref([]);
      const inboundOrderDetailInfoList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
      const checkedInboundOrderDetailInfo = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
          const daterangeCreateTime = ref([]);

  const data = reactive({
    form: {},
    // 导出地址
    exportUrl: 'manage/inboundOrderInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    inboundNo: null,
                    inboundType: null,
                    warehouseId: null,
                    orderId: null,
                    supplierId: null,
                    inboundDate: null,
                    inboundStatus: null,
                    reviewStatus: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    inboundNo: [
                { required: true, message: "入库单号不能为空", trigger: "blur" }
              ],
                    inboundType: [
                { required: true, message: "入库类型不能为空", trigger: "change" }
              ],
                    warehouseId: [
                { required: true, message: "仓库不能为空", trigger: "blur" }
              ],
                    inboundDate: [
                { required: true, message: "入库日期不能为空", trigger: "blur" }
              ],
                    inboundStatus: [
                { required: true, message: "入库状态不能为空", trigger: "change" }
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
                { key: 1, label: '入库单号', visible: true },
                { key: 2, label: '入库类型', visible: true },
                { key: 3, label: '仓库', visible: true },
                { key: 4, label: '关联订单', visible: true },
                { key: 5, label: '供应商', visible: true },
                { key: 6, label: '入库日期', visible: true },
                { key: 7, label: '入库状态', visible: true },
                { key: 8, label: '经办人', visible: true },
                { key: 9, label: '审核人', visible: true },
                { key: 10, label: '审核状态', visible: true },
                { key: 11, label: '审核时间', visible: true },
                { key: 12, label: '审批描述', visible: true },
                { key: 13, label: '创建人', visible: true },
                { key: 14, label: '创建时间', visible: true },
                { key: 15, label: '更新人', visible: true },
                { key: 16, label: '更新时间', visible: true },
                { key: 17, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询入库单列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listInboundOrderInfo(queryParams.value).then(response => {
            inboundOrderInfoList.value = response.rows;
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
                    inboundNo: null,
                    inboundType: null,
                    warehouseId: null,
                    orderId: null,
                    supplierId: null,
                    inboundDate: null,
                    inboundStatus: null,
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
              inboundOrderDetailInfoList.value = [];
    proxy.resetForm("inboundOrderInfoRef");
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
    title.value = "添加入库单";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getInboundOrderInfo(_id).then(response => {
      form.value = response.data;
                inboundOrderDetailInfoList.value = response.data.inboundOrderDetailInfoList;
      open.value = true;
      title.value = "修改入库单";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["inboundOrderInfoRef"].validate(valid => {
      if (valid) {
        form.value.inboundOrderDetailInfoList = inboundOrderDetailInfoList.value;
        if (form.value.id != null) {
          updateInboundOrderInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addInboundOrderInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除入库单编号为"' + _ids + '"的数据项？').then(function() {
      return delInboundOrderInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 入库明细序号 */
  function rowInboundOrderDetailInfoIndex({ row, rowIndex }) {
    row.index = rowIndex + 1;
  }

  /** 入库明细添加按钮操作 */
  function handleAddInboundOrderDetailInfo() {
    let obj = {};
    obj.partsCode = "";
    obj.inboundQuantity = "";
    obj.batchNo = "";
    obj.warehouseId = "";
    obj.locationId = "";
    obj.qualityStatus = "";
    obj.productionDate = "";
    obj.expiryDate = "";
    obj.unitPrice = "";
    obj.remark = "";
    inboundOrderDetailInfoList.value.push(obj);
  }

  /** 入库明细删除按钮操作 */
  function handleDeleteInboundOrderDetailInfo() {
    if (checkedInboundOrderDetailInfo.value.length == 0) {
      proxy.$modal.msgError("请先选择要删除的入库明细数据");
    } else {
      const inboundOrderDetailInfos = inboundOrderDetailInfoList.value;
      const checkedInboundOrderDetailInfos = checkedInboundOrderDetailInfo.value;
      inboundOrderDetailInfoList.value = inboundOrderDetailInfos.filter(function(item) {
        return checkedInboundOrderDetailInfos.indexOf(item.index) == -1
      });
    }
  }

  /** 复选框选中数据 */
  function handleInboundOrderDetailInfoSelectionChange(selection) {
    checkedInboundOrderDetailInfo.value = selection.map(item => item.index)
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `inboundOrderInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
