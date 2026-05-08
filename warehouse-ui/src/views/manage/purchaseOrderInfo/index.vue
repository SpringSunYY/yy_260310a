<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="orderId">
        <el-input
            v-model="queryParams.orderId"
            placeholder="请输入编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderNo">
        <el-input
            v-model="queryParams.orderNo"
            placeholder="请输入采购订单号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select
            v-model="queryParams.supplierId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入供应商名称"
            :remote-method="remoteGetSupplierList"
            :loading="supplierLoading"
            style="width: 100%"
        >
          <el-option
              v-for="item in supplierList"
              :key="item.id"
              :label="item.supplierName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" style="width: 200px" placeholder="请选择订单状态" clearable>
          <el-option
              v-for="dict in warehouse_inbound_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="applicantStatus">
        <el-select v-model="queryParams.applicantStatus" style="width: 200px" placeholder="请选择审批状态" clearable>
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
            v-hasPermi="['manage:purchaseOrderInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:purchaseOrderInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:purchaseOrderInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:purchaseOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="orderId" v-if="columns[0].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="订单号" align="center" prop="orderNo" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="供应商" align="center" prop="supplierId" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="订单日期" align="center" prop="orderDate" width="180" v-if="columns[3].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预计到货日期" align="center" prop="expectedArrivalDate" width="180"
                       v-if="columns[4].visible" :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.expectedArrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总金额" align="center" prop="totalAmount" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="订单状态" align="center" prop="orderStatus" v-if="columns[6].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_inbound_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applicantId" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审批状态" align="center" prop="applicantStatus" v-if="columns[8].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_applicant_status" :value="scope.row.applicantStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="approverId" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审批时间" align="center" prop="approvalTime" width="180" v-if="columns[10].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.approvalTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批描述" align="center" prop="approvalDesc" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[13].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[15].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[16].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:purchaseOrderInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:purchaseOrderInfo:remove']">删除
          </el-button>
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" v-model="open" width="900px" append-to-body>
      <el-form ref="purchaseOrderInfoRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="采购订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入采购订单号"/>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select
              v-model="form.supplierId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入供应商名称"
              :remote-method="remoteGetSupplierList"
              :loading="supplierLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in supplierList"
                :key="item.id"
                :label="item.supplierName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="订单日期" prop="orderDate">
          <el-date-picker clearable
                          v-model="form.orderDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择订单日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计到货日期" prop="expectedArrivalDate">
          <el-date-picker clearable
                          v-model="form.expectedArrivalDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择预计到货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单总金额" prop="totalAmount">
          <el-input :readonly="true" v-model="form.totalAmount" placeholder="请输入订单总金额"/>
        </el-form-item>
        <!--        <el-form-item label="订单状态" prop="orderStatus">-->
        <!--          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">-->
        <!--            <el-option-->
        <!--                v-for="dict in warehouse_inbound_status"-->
        <!--                :key="dict.value"-->
        <!--                :label="dict.label"-->
        <!--                :value="dict.value"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="申请人" prop="applicantId">
          <el-select
              v-model="form.userId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetUserList"
              :loading="userLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.userName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="审批状态" prop="applicantStatus">-->
        <!--          <el-radio-group v-model="form.applicantStatus">-->
        <!--            <el-radio-->
        <!--                v-for="dict in warehouse_applicant_status"-->
        <!--                :key="dict.value"-->
        <!--                :value="dict.value"-->
        <!--            >{{ dict.label }}-->
        <!--            </el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审批人" prop="approverId">-->
        <!--          <el-input v-model="form.approverId" placeholder="请输入审批人"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审批时间" prop="approvalTime">-->
        <!--          <el-date-picker clearable-->
        <!--                          v-model="form.approvalTime"-->
        <!--                          type="date"-->
        <!--                          value-format="YYYY-MM-DD"-->
        <!--                          placeholder="请选择审批时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="审批描述" prop="approvalDesc">-->
        <!--          <el-input v-model="form.approvalDesc" type="textarea" placeholder="请输入内容"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-divider content-position="center">采购订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddPurchaseOrderDetailInfo">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeletePurchaseOrderDetailInfo">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="purchaseOrderDetailInfoList" :row-class-name="rowPurchaseOrderDetailInfoIndex"
                  @selection-change="handlePurchaseOrderDetailInfoSelectionChange" ref="purchaseOrderDetailInfo">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="备件编号" prop="partsCode" width="150">
            <template #default="scope">
              <el-select
                  v-model="scope.row.partsCode"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入备件名称"
                  :remote-method="(query) => remoteGetSparePartsList(query, scope.$index)"
                  :loading="sparePartsLoading"
                  style="width: 100%"
                  @change="handleSparePartsChange($event, scope.$index)"
              >
                <el-option
                    v-for="item in getSparePartsListForRow(scope.$index)"
                    :key="item.id"
                    :label="item.partsName"
                    :value="item.partsCode"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="采购数量" prop="purchaseQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="1" v-model="scope.row.purchaseQuantity" placeholder="请输入采购数量" @change="handleQuantityChange(scope.$index)"/>
            </template>
          </el-table-column>
          <el-table-column label="采购单价" prop="purchasePrice" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" v-model="scope.row.purchasePrice" placeholder="请输入采购单价" :min="0" :precision="2" @change="handleAmountChange(scope.$index)"/>
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" width="150">
            <template #default="scope">
              <el-input :readonly="true" v-model="scope.row.amount" placeholder="自动计算"/>
            </template>
          </el-table-column>
          <el-table-column label="已收货数量" prop="receivedQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0" v-model="scope.row.receivedQuantity" placeholder="请输入已收货数量"/>
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

<script setup name="PurchaseOrderInfo">
import {
  listPurchaseOrderInfo,
  getPurchaseOrderInfo,
  delPurchaseOrderInfo,
  addPurchaseOrderInfo,
  updatePurchaseOrderInfo
} from "@/api/manage/purchaseOrderInfo";
import {listSupplierInfo} from "@/api/manage/supplierInfo.js";
import {allocatedUserListAll} from "@/api/system/role.js";
import {listSparePartsInfo} from "@/api/manage/sparePartsInfo.js";

const {proxy} = getCurrentInstance();
const {
  warehouse_inbound_status,
  warehouse_applicant_status
} = proxy.useDict('warehouse_inbound_status', 'warehouse_applicant_status');

const purchaseOrderInfoList = ref([]);
const purchaseOrderDetailInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedPurchaseOrderDetailInfo = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/purchaseOrderInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    orderId: null,
    orderNo: null,
    supplierId: null,
    orderStatus: null,
    applicantStatus: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    orderNo: [
      {required: true, message: "采购订单号不能为空", trigger: "blur"}
    ],
    supplierId: [
      {required: true, message: "供应商不能为空", trigger: "blur"}
    ],
    orderDate: [
      {required: true, message: "订单日期不能为空", trigger: "blur"}
    ],
    totalAmount: [
      {required: true, message: "订单总金额不能为空", trigger: "blur"}
    ],
    orderStatus: [
      {required: true, message: "订单状态不能为空", trigger: "change"}
    ],
    applicantStatus: [
      {required: true, message: "审批状态不能为空", trigger: "change"}
    ],
    createBy: [
      {required: true, message: "创建人不能为空", trigger: "blur"}
    ],
    createTime: [
      {required: true, message: "创建时间不能为空", trigger: "blur"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '采购订单号', visible: true},
    {key: 2, label: '供应商', visible: true},
    {key: 3, label: '订单日期', visible: true},
    {key: 4, label: '预计到货日期', visible: true},
    {key: 5, label: '订单总金额', visible: true},
    {key: 6, label: '订单状态', visible: true},
    {key: 7, label: '申请人', visible: true},
    {key: 8, label: '审批状态', visible: true},
    {key: 9, label: '审批人', visible: true},
    {key: 10, label: '审批时间', visible: true},
    {key: 11, label: '审批描述', visible: false},
    {key: 12, label: '创建人', visible: true},
    {key: 13, label: '创建时间', visible: true},
    {key: 14, label: '更新人', visible: false},
    {key: 15, label: '更新时间', visible: false},
    {key: 16, label: '备注', visible: false},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询采购订单列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listPurchaseOrderInfo(queryParams.value).then(response => {
    purchaseOrderInfoList.value = response.rows;
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
    orderId: null,
    orderNo: null,
    supplierId: null,
    orderDate: null,
    expectedArrivalDate: null,
    totalAmount: null,
    orderStatus: null,
    applicantId: null,
    applicantStatus: null,
    approverId: null,
    approvalTime: null,
    approvalDesc: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  purchaseOrderDetailInfoList.value = [];
  proxy.resetForm("purchaseOrderInfoRef");
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
  ids.value = selection.map(item => item.orderId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加采购订单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _orderId = row.orderId || ids.value
  getPurchaseOrderInfo(_orderId).then(response => {
    form.value = response.data;
    purchaseOrderDetailInfoList.value = response.data.purchaseOrderDetailInfoList;
    open.value = true;
    title.value = "修改采购订单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["purchaseOrderInfoRef"].validate(valid => {
    if (valid) {
      form.value.purchaseOrderDetailInfoList = purchaseOrderDetailInfoList.value;
      if (form.value.orderId != null) {
        updatePurchaseOrderInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPurchaseOrderInfo(form.value).then(response => {
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
  const _orderIds = row.orderId || ids.value;
  proxy.$modal.confirm('是否确认删除采购订单编号为"' + _orderIds + '"的数据项？').then(function () {
    return delPurchaseOrderInfo(_orderIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 采购订单明细序号 */
function rowPurchaseOrderDetailInfoIndex({row, rowIndex}) {
  row.index = rowIndex + 1;
}

/** 采购订单明细添加按钮操作 */
function handleAddPurchaseOrderDetailInfo() {
  let obj = {};
  obj.partsCode = "";
  obj.purchaseQuantity = 1;
  obj.purchasePrice = "";
  obj.amount = "0.00";
  obj.receivedQuantity = "";
  obj.remark = "";
  purchaseOrderDetailInfoList.value.push(obj);
}

/** 采购订单明细删除按钮操作 */
function handleDeletePurchaseOrderDetailInfo() {
  if (checkedPurchaseOrderDetailInfo.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的采购订单明细数据");
  } else {
    const purchaseOrderDetailInfos = purchaseOrderDetailInfoList.value;
    const checkedPurchaseOrderDetailInfos = checkedPurchaseOrderDetailInfo.value;
    purchaseOrderDetailInfoList.value = purchaseOrderDetailInfos.filter(function (item) {
      return checkedPurchaseOrderDetailInfos.indexOf(item.index) == -1
    });
    // 更新订单总金额
    updateTotalAmount();
  }
}

/** 复选框选中数据 */
function handlePurchaseOrderDetailInfoSelectionChange(selection) {
  checkedPurchaseOrderDetailInfo.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(exportUrl.value, {
    ...queryParams.value
  }, `purchaseOrderInfo_${new Date().getTime()}.xlsx`)
}

const supplierList = ref([]);
const supplierLoading = ref(false);
const supplierQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  supplierName: null,
  supplierStatus: '1',
});
const getSupplierList = () => {
  supplierLoading.value = true;
  listSupplierInfo(supplierQueryParams).then(response => {
    supplierList.value = response.rows;
    supplierLoading.value = false;
  });
};
const remoteGetSupplierList = (query) => {
  supplierQueryParams.supplierName = query;
  getSupplierList();
}

getSupplierList()

const userList = ref([]);
const userLoading = ref(false);
const userQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 101,
});
const getUserList = () => {
  userLoading.value = true;
  allocatedUserListAll(userQueryParams).then(response => {
    userList.value = response.rows;
    userLoading.value = false;
  });
};
const remoteGetUserList = (query) => {
  userQueryParams.name = query;
  getUserList();
}

getUserList()

const sparePartsList = ref([]);
const sparePartsLoading = ref(false);
const sparePartsQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  partsStatus: '1',
});
// 用于存储每个行对应的备件列表
const rowSparePartsMap = ref({});

const getSparePartsList = () => {
  sparePartsLoading.value = true;
  listSparePartsInfo(sparePartsQueryParams).then(response => {
    sparePartsList.value = response.rows;
    sparePartsLoading.value = false;
  });
};
const remoteGetSparePartsList = (query, rowIndex) => {
  sparePartsQueryParams.name = query;
  listSparePartsInfo(sparePartsQueryParams).then(response => {
    rowSparePartsMap.value[rowIndex] = response.rows;
    sparePartsLoading.value = false;
  });
  sparePartsLoading.value = true;
};

// 获取指定行对应的备件列表
const getSparePartsListForRow = (rowIndex) => {
  if (rowSparePartsMap.value[rowIndex]) {
    return rowSparePartsMap.value[rowIndex];
  }
  return sparePartsList.value;
};

// 备件选择变化时触发
const handleSparePartsChange = (partsCode, rowIndex) => {
  const row = purchaseOrderDetailInfoList.value[rowIndex];
  if (partsCode) {
    // 查找选中的备件信息
    const spareParts = getSparePartsListForRow(rowIndex);
    const selectedSparePart = spareParts.find(item => item.partsCode === partsCode);
    if (selectedSparePart) {
      row.purchasePrice = selectedSparePart.unitPrice || 0;
      // 数量默认为1
      if (!row.purchaseQuantity || row.purchaseQuantity < 1) {
        row.purchaseQuantity = 1;
      }
      // 计算金额
      row.amount = (row.purchaseQuantity * row.purchasePrice).toFixed(2);
      // 更新订单总金额
      updateTotalAmount();
    }
  }
};

// 数量变化时触发
const handleQuantityChange = (rowIndex) => {
  const row = purchaseOrderDetailInfoList.value[rowIndex];
  if (row.purchasePrice && row.purchaseQuantity) {
    row.amount = (row.purchaseQuantity * row.purchasePrice).toFixed(2);
  } else {
    row.amount = '0.00';
  }
  updateTotalAmount();
};

// 单价变化时触发
const handleAmountChange = (rowIndex) => {
  const row = purchaseOrderDetailInfoList.value[rowIndex];
  if (row.purchasePrice && row.purchaseQuantity) {
    row.amount = (row.purchaseQuantity * row.purchasePrice).toFixed(2);
  } else {
    row.amount = '0.00';
  }
  updateTotalAmount();
};

// 更新订单总金额
const updateTotalAmount = () => {
  let total = 0;
  purchaseOrderDetailInfoList.value.forEach(item => {
    if (item.amount) {
      total += parseFloat(item.amount);
    }
  });
  form.value.totalAmount = total.toFixed(2);
};

getSparePartsList()

getList();
</script>
