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
            v-hasPermi="['manage:stocktakingOrderInfo:edit']"
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
            v-hasPermi="['manage:stocktakingOrderInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:stocktakingOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stocktakingOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="盘点单号" align="center" prop="stocktakingNo" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="盘点类型" align="center" prop="stocktakingType" v-if="columns[2].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_stocktaking_type" :value="scope.row.stocktakingType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="盘点开始日期" align="center" prop="startDate" width="180" v-if="columns[4].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点结束日期" align="center" prop="endDate" width="180" v-if="columns[5].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点状态" align="center" prop="stocktakingStatus" v-if="columns[6].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_stocktaking_status" :value="scope.row.stocktakingStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="盘点人" align="center" prop="operatorName" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审核人" align="center" prop="reviewerName" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审核状态" align="center" prop="reviewStatus" v-if="columns[9].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_applicant_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="reviewTime" width="180" v-if="columns[10].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批描述" align="center" prop="reviewRemark" v-if="columns[11].visible"
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
      <el-table-column fixed="right" width="200" label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:stocktakingOrderInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:stocktakingOrderInfo:remove']">删除
          </el-button>
          <el-button link type="primary" icon="Check" @click="handleAudit(scope.row)"
                     v-if="scope.row.stocktakingStatus === '0' && scope.row.reviewStatus === '0'"
                     v-hasPermi="['manage:stocktakingOrderInfo:audit']">审核
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

    <!-- 添加或修改盘点单对话框 -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form ref="stocktakingOrderInfoRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="盘点单号" prop="stocktakingNo">
          <el-input v-model="form.stocktakingNo" placeholder="请输入盘点单号"/>
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
          <el-select
              v-model="form.warehouseId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入仓库名称"
              :remote-method="remoteGetWarehouseList"
              :loading="warehouseLoading"
              style="width: 100%"
              @change="handleWarehouseChange"
          >
            <el-option
                v-for="item in warehouseList"
                :key="item.id"
                :label="item.warehouseName"
                :value="item.id"
            />
          </el-select>
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
        <el-form-item label="盘点人" prop="operatorId">
          <el-select
              v-model="form.operatorId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetOperatorList"
              :loading="operatorLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in operatorList"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
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
        <el-table :data="stocktakingOrderDetailInfoList" :row-class-name="rowStocktakingOrderDetailInfoIndex"
                  @selection-change="handleStocktakingOrderDetailInfoSelectionChange" ref="stocktakingOrderDetailInfo">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="备件" prop="partsCode" width="280">
            <template #default="scope">
              <el-select
                  v-model="scope.row.inventoryId"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入备件名称"
                  :remote-method="(query) => remoteGetInventoryRecordList(query, scope.$index)"
                  :loading="inventoryLoading"
                  style="width: 100%"
                  @change="(val) => handleInventoryRecordChange(val, scope.$index)"
              >
                <el-option
                    v-for="item in getInventoryRecordListForRow(scope.$index)"
                    :key="item.id"
                    :label="item.partsName + '-' + item.warehouseName + '-' + item.locationName + (item.batchNo ? '-' + item.batchNo : '')"
                    :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="账面数量" prop="bookQuantity" width="100">
            <template #default="scope">
              <span>{{ scope.row.bookQuantity || 0 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际盘点数量" prop="actualQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0"
                               v-model="scope.row.actualQuantity"
                               placeholder="请输入实际盘点数量"/>
            </template>
          </el-table-column>
          <el-table-column label="差异数量" prop="differenceQuantity" width="100">
            <template #default="scope">
              <span :style="{ color: getDifferenceColor(scope.row) }">
                {{ getDifferenceQuantity(scope.row) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="差异原因" prop="differenceReason" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.differenceReason" placeholder="请输入差异原因"/>
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

    <!-- 审核盘点单对话框 -->
    <el-dialog :title="auditTitle" v-model="openAudit" width="1000px" append-to-body>
      <el-form ref="stocktakingOrderInfoAuditRef" :model="form" :rules="auditRules" label-width="100px">
        <el-form-item label="盘点单号" prop="stocktakingNo">
          <el-input :readonly="true" v-model="form.stocktakingNo" placeholder="请输入盘点单号"/>
        </el-form-item>
        <el-form-item label="盘点类型" prop="stocktakingType">
          <el-select :disabled="true" v-model="form.stocktakingType" placeholder="请选择盘点类型">
            <el-option
                v-for="dict in warehouse_stocktaking_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select
              :disabled="true"
              v-model="form.warehouseId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入仓库名称"
              :remote-method="remoteGetWarehouseList"
              :loading="warehouseLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in warehouseList"
                :key="item.id"
                :label="item.warehouseName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="盘点开始日期" prop="startDate">
          <el-date-picker clearable
                          :readonly="true"
                          v-model="form.startDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择盘点开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="盘点结束日期" prop="endDate">
          <el-date-picker clearable
                          :readonly="true"
                          v-model="form.endDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择盘点结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="盘点人" prop="operatorId">
          <el-select
              :disabled="true"
              v-model="form.operatorId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetOperatorList"
              :loading="operatorLoading"
              style="width: 100%"
          >
            <el-option
                v-for="item in operatorList"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" prop="reviewStatus">
          <el-radio-group v-model="form.reviewStatus">
            <el-radio
                v-for="dict in warehouse_applicant_status"
                :key="dict.value"
                :value="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审批描述" prop="reviewRemark">
          <el-input v-model="form.reviewRemark" type="textarea" placeholder="请输入审批描述"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
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
        <el-table :data="stocktakingOrderDetailInfoList" :row-class-name="rowStocktakingOrderDetailInfoIndex"
                  @selection-change="handleStocktakingOrderDetailInfoSelectionChange" ref="stocktakingOrderDetailInfo">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="备件" prop="partsCode" width="280">
            <template #default="scope">
              <el-select
                  v-model="scope.row.inventoryId"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入备件名称"
                  :remote-method="(query) => remoteGetInventoryRecordList(query, scope.$index)"
                  :loading="inventoryLoading"
                  style="width: 100%"
                  @change="(val) => handleInventoryRecordChange(val, scope.$index)"
              >
                <el-option
                    v-for="item in getInventoryRecordListForRow(scope.$index)"
                    :key="item.id"
                    :label="item.partsName + '-' + item.warehouseName + '-' + item.locationName + (item.batchNo ? '-' + item.batchNo : '')"
                    :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="账面数量" prop="bookQuantity" width="100">
            <template #default="scope">
              <span>{{ scope.row.bookQuantity || 0 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="实际盘点数量" prop="actualQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0"
                               v-model="scope.row.actualQuantity"
                               placeholder="请输入实际盘点数量"/>
            </template>
          </el-table-column>
          <el-table-column label="差异数量" prop="differenceQuantity" width="100">
            <template #default="scope">
              <span :style="{ color: getDifferenceColor(scope.row) }">
                {{ getDifferenceQuantity(scope.row) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="差异原因" prop="differenceReason" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.differenceReason" placeholder="请输入差异原因"/>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitAuditForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="StocktakingOrderInfo">
import {
  listStocktakingOrderInfo,
  getStocktakingOrderInfo,
  delStocktakingOrderInfo,
  addStocktakingOrderInfo,
  updateStocktakingOrderInfo,
  auditStocktakingOrderInfo
} from "@/api/manage/stocktakingOrderInfo";
import {listWarehouseInfo} from "@/api/manage/warehouseInfo.js";
import {allocatedUserListAll} from "@/api/system/role.js";
import {listInventoryRecordInfo} from "@/api/manage/inventoryRecordInfo.js";

const {proxy} = getCurrentInstance();
const {
  warehouse_stocktaking_status,
  warehouse_stocktaking_type,
  warehouse_applicant_status
} = proxy.useDict('warehouse_stocktaking_status', 'warehouse_stocktaking_type', 'warehouse_applicant_status');

const stocktakingOrderInfoList = ref([]);
const stocktakingOrderDetailInfoList = ref([]);
const open = ref(false);
const openAudit = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedStocktakingOrderDetailInfo = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const auditTitle = ref("");
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
      {required: true, message: "盘点单号不能为空", trigger: "blur"}
    ],
    stocktakingType: [
      {required: true, message: "盘点类型不能为空", trigger: "change"}
    ],
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "change"}
    ],
    startDate: [
      {required: true, message: "盘点开始日期不能为空", trigger: "blur"}
    ],
  },
  auditRules: {
    reviewStatus: [
      {required: true, message: "审核状态不能为空", trigger: "change"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '盘点单号', visible: true},
    {key: 2, label: '盘点类型', visible: true},
    {key: 3, label: '仓库', visible: true},
    {key: 4, label: '盘点开始日期', visible: true},
    {key: 5, label: '盘点结束日期', visible: true},
    {key: 6, label: '盘点状态', visible: true},
    {key: 7, label: '盘点人', visible: true},
    {key: 8, label: '审核人', visible: true},
    {key: 9, label: '审核状态', visible: true},
    {key: 10, label: '审核时间', visible: true},
    {key: 11, label: '审批描述', visible: true},
    {key: 12, label: '创建人', visible: true},
    {key: 13, label: '创建时间', visible: true},
    {key: 14, label: '更新人', visible: false},
    {key: 15, label: '更新时间', visible: false},
    {key: 16, label: '备注', visible: false},
  ],
});

const {queryParams, form, rules, auditRules, columns, exportUrl} = toRefs(data);

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
  openAudit.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    stocktakingNo: null,
    stocktakingType: null,
    warehouseId: null,
    warehouseName: null,
    startDate: null,
    endDate: null,
    stocktakingStatus: null,
    operatorId: null,
    operatorName: null,
    reviewerId: null,
    reviewerName: null,
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
    // 转换数量为数字
    stocktakingOrderDetailInfoList.value.forEach(detail => {
      detail.bookQuantity = Number(detail.bookQuantity) || 0;
      detail.actualQuantity = Number(detail.actualQuantity) || 0;
      detail.differenceQuantity = Number(detail.differenceQuantity) || 0;
    });
    open.value = true;
    title.value = "修改盘点单";
    // 为每个明细行设置 inventoryId
    nextTick(() => {
      stocktakingOrderDetailInfoList.value.forEach((detail, index) => {
        if (detail.partsCode && detail.locationId) {
          remoteGetInventoryRecordList(detail.partsCode, index);
        }
      });
    });
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["stocktakingOrderInfoRef"].validate(valid => {
    if (valid) {
      // 计算差异数量
      stocktakingOrderDetailInfoList.value.forEach(detail => {
        detail.differenceQuantity = (detail.actualQuantity || 0) - (detail.bookQuantity || 0);
      });
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
  proxy.$modal.confirm('是否确认删除盘点单编号为"' + _ids + '"的数据项？').then(function () {
    return delStocktakingOrderInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 盘点明细序号 */
function rowStocktakingOrderDetailInfoIndex({row, rowIndex}) {
  row.index = rowIndex + 1;
}

/** 盘点明细添加按钮操作 */
function handleAddStocktakingOrderDetailInfo() {
  let obj = {};
  obj.inventoryId = null;
  obj.partsCode = "";
  obj.partsName = "";
  obj.warehouseId = form.value.warehouseId || null;
  obj.locationId = null;
  obj.locationName = "";
  obj.batchNo = "";
  obj.bookQuantity = 0;
  obj.actualQuantity = 0;
  obj.differenceQuantity = 0;
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
    stocktakingOrderDetailInfoList.value = stocktakingOrderDetailInfos.filter(function (item) {
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

// ========== 仓库列表 ==========
const warehouseList = ref([]);
const warehouseLoading = ref(false);
const warehouseQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  warehouseName: null,
});
const getWarehouseList = () => {
  warehouseLoading.value = true;
  listWarehouseInfo(warehouseQueryParams).then(response => {
    warehouseList.value = response.rows;
    warehouseLoading.value = false;
  });
};
const remoteGetWarehouseList = (query) => {
  warehouseQueryParams.warehouseName = query;
  getWarehouseList();
}

getWarehouseList();

// ========== 用户列表（盘点人） ==========
const operatorList = ref([]);
const operatorLoading = ref(false);
const operatorQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 101,
});
const getOperatorList = () => {
  operatorLoading.value = true;
  allocatedUserListAll(operatorQueryParams).then(response => {
    operatorList.value = response.rows;
    operatorLoading.value = false;
  });
};
const remoteGetOperatorList = (query) => {
  operatorQueryParams.userName = query;
  getOperatorList();
}

// ========== 库存记录列表（备件选择） ==========
const inventoryRecordList = ref([]);
const inventoryLoading = ref(false);
const inventoryQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  partsCode: null,
});
const rowInventoryRecordMap = ref({});

const getInventoryRecordList = () => {
  inventoryLoading.value = true;
  listInventoryRecordInfo(inventoryQueryParams).then(response => {
    inventoryRecordList.value = response.data || response.rows || [];
    inventoryLoading.value = false;
  });
};

const remoteGetInventoryRecordList = (query, rowIndex) => {
  const params = {partsCode: query};
  if (form.value.warehouseId) {
    params.warehouseId = form.value.warehouseId;
  }
  inventoryLoading.value = true;
  listInventoryRecordInfo(params).then(response => {
    const list = response.data || response.rows || [];
    rowInventoryRecordMap.value[rowIndex] = list;
    inventoryLoading.value = false;
    // 编辑模式：根据 partsCode + locationId + batchNo 匹配并设置 inventoryId
    const detail = stocktakingOrderDetailInfoList.value[rowIndex];
    if (detail && detail.partsCode && detail.locationId) {
      const matched = list.find(item =>
          item.partsCode === detail.partsCode &&
          item.locationId === detail.locationId &&
          (item.batchNo || '') === (detail.batchNo || '')
      );
      if (matched) {
        detail.inventoryId = matched.id;
        detail.bookQuantity = matched.quantity || 0;
      }
    }
  });
};

const getInventoryRecordListForRow = (rowIndex) => {
  if (rowInventoryRecordMap.value[rowIndex]) {
    return rowInventoryRecordMap.value[rowIndex];
  }
  return inventoryRecordList.value;
};

// ========== 仓库变化 ==========
const handleWarehouseChange = (value) => {
  nextTick(() => {
    stocktakingOrderDetailInfoList.value.forEach((item, index) => {
      item.inventoryId = null;
      item.partsCode = "";
      item.partsName = "";
      item.locationId = null;
      item.locationName = "";
      item.batchNo = "";
      item.bookQuantity = 0;
      item.actualQuantity = 0;
      item.differenceQuantity = 0;
    });
    rowInventoryRecordMap.value = {};
  });
};

// ========== 备件变化 ==========
const handleInventoryRecordChange = (inventoryId, rowIndex) => {
  const row = stocktakingOrderDetailInfoList.value[rowIndex];
  if (inventoryId) {
    const inventoryList = getInventoryRecordListForRow(rowIndex);
    const selectedInventory = inventoryList.find(item => item.id === inventoryId);
    if (selectedInventory) {
      row.inventoryId = inventoryId;
      row.partsCode = selectedInventory.partsCode;
      row.partsName = selectedInventory.partsName;
      row.locationId = selectedInventory.locationId;
      row.locationName = selectedInventory.locationName;
      row.batchNo = selectedInventory.batchNo || '';
      // 账面数量 = 库存数量
      row.bookQuantity = selectedInventory.quantity || 0;
      // 实际盘点数量默认 = 账面数量
      row.actualQuantity = selectedInventory.quantity || 0;
      // 差异数量 = 0
      row.differenceQuantity = 0;
    }
  } else {
    row.inventoryId = null;
    row.partsCode = "";
    row.partsName = "";
    row.locationId = null;
    row.locationName = "";
    row.batchNo = "";
    row.bookQuantity = 0;
    row.actualQuantity = 0;
    row.differenceQuantity = 0;
  }
};

// ========== 计算差异数量 ==========
const getDifferenceQuantity = (row) => {
  const diff = (row.actualQuantity || 0) - (row.bookQuantity || 0);
  return diff > 0 ? `+${diff}` : diff.toString();
};

const getDifferenceColor = (row) => {
  const diff = (row.actualQuantity || 0) - (row.bookQuantity || 0);
  if (diff > 0) return '#67C23A'; // 绿色，盘盈
  if (diff < 0) return '#F56C6C'; // 红色，盘亏
  return '#909399'; // 灰色，无差异
};

// ========== 审核相关 ==========
function handleAudit(row) {
  getStocktakingOrderInfo(row.id).then(response => {
    form.value = response.data;
    stocktakingOrderDetailInfoList.value = response.data.stocktakingOrderDetailInfoList;
    // 转换数量为数字
    stocktakingOrderDetailInfoList.value.forEach(detail => {
      detail.bookQuantity = Number(detail.bookQuantity) || 0;
      detail.actualQuantity = Number(detail.actualQuantity) || 0;
      detail.differenceQuantity = Number(detail.differenceQuantity) || 0;
    });
    openAudit.value = true;
    title.value = "审核盘点单";
    // 为每个明细行设置 inventoryId
    nextTick(() => {
      stocktakingOrderDetailInfoList.value.forEach((detail, index) => {
        if (detail.partsCode && detail.locationId) {
          remoteGetInventoryRecordList(detail.partsCode, index);
        }
      });
    });
  });
}

function submitAuditForm() {
  proxy.$refs["stocktakingOrderInfoAuditRef"].validate(valid => {
    if (valid) {
      // 计算差异数量
      stocktakingOrderDetailInfoList.value.forEach(detail => {
        detail.differenceQuantity = (detail.actualQuantity || 0) - (detail.bookQuantity || 0);
      });
      form.value.stocktakingOrderDetailInfoList = stocktakingOrderDetailInfoList.value;
      auditStocktakingOrderInfo(form.value).then(response => {
        proxy.$modal.msgSuccess("审核成功");
        openAudit.value = false;
        getList();
      });
    }
  });
}

getList();
</script>
