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
      <el-form-item label="出库单号" prop="outboundNo">
        <el-input
            v-model="queryParams.outboundNo"
            placeholder="请输入出库单号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库类型" prop="outboundType">
        <el-select v-model="queryParams.outboundType" style="width: 200px" placeholder="请选择出库类型" clearable>
          <el-option
              v-for="dict in warehouse_outbound_type"
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
      <el-form-item label="出库日期" style="width: 308px">
        <el-date-picker
            v-model="daterangeOutboundDate"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="出库状态" prop="outboundStatus">
        <el-select v-model="queryParams.outboundStatus" style="width: 200px" placeholder="请选择出库状态" clearable>
          <el-option
              v-for="dict in warehouse_outbound_status"
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
            v-hasPermi="['manage:outboundOrderInfo:add']"
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
            v-hasPermi="['manage:outboundOrderInfo:edit']"
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
            v-hasPermi="['manage:outboundOrderInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:outboundOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outboundOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="出库单号" align="center" prop="outboundNo" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="出库类型" align="center" prop="outboundType" v-if="columns[2].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_outbound_type" :value="scope.row.outboundType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="领用人" align="center" prop="recipientName" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="领用部门" align="center" prop="recipientDeptName" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="出库日期" align="center" prop="outboundDate" width="180" v-if="columns[6].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.outboundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center" prop="outboundStatus" v-if="columns[7].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_outbound_status" :value="scope.row.outboundStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" prop="operatorName" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审核人" align="center" prop="reviewerName" v-if="columns[9].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="审核状态" align="center" prop="reviewStatus" v-if="columns[10].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_applicant_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="reviewTime" width="180" v-if="columns[11].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批描述" align="center" prop="reviewRemark" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[16].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[17].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column fixed="right" width="200" label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:outboundOrderInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:outboundOrderInfo:remove']">删除
          </el-button>
          <el-button link type="primary" icon="Check" @click="handleAudit(scope.row)"
                     v-if="scope.row.outboundStatus === '0' && scope.row.reviewStatus === '0'"
                     v-hasPermi="['manage:outboundOrderInfo:audit']">审核
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

    <!-- 添加或修改出库单对话框 -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form ref="outboundOrderInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="出库单号" prop="outboundNo">
          <el-input v-model="form.outboundNo" placeholder="请输入出库单号"/>
        </el-form-item>
        <el-form-item label="出库类型" prop="outboundType">
          <el-select v-model="form.outboundType" placeholder="请选择出库类型">
            <el-option
                v-for="dict in warehouse_outbound_type"
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
        <el-form-item label="领用人" prop="recipientId">
          <el-select
              v-model="form.recipientId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetUserList"
              :loading="userLoading"
              style="width: 100%"
              @change="handleRecipientChange"
          >
            <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="领用部门" prop="recipientDeptId">-->
        <!--          <el-input :readonly="true" v-model="form.recipientDeptName" placeholder="领用部门自动获取"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="出库日期" prop="outboundDate">
          <el-date-picker clearable
                          v-model="form.outboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择出库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否可归还" prop="isReturnable">
          <el-select v-model="form.isReturnable" placeholder="请选择是否可归还">
            <el-option
                v-for="dict in warehouse_is_returnable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经办人" prop="operatorId">
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
        <el-divider content-position="center">出库明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddOutboundOrderDetailInfo">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteOutboundOrderDetailInfo">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="outboundOrderDetailInfoList" :row-class-name="rowOutboundOrderDetailInfoIndex"
                  @selection-change="handleOutboundOrderDetailInfoSelectionChange" ref="outboundOrderDetailInfo">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="备件" prop="partsCode" width="250">
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
          <el-table-column label="可用数量" prop="availableQuantity" width="100">
            <template #default="scope">
              <span>{{ scope.row.availableQuantity || 0 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="出库数量" prop="outboundQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0"
                               v-model="scope.row.outboundQuantity"
                               placeholder="请输入出库数量"/>
            </template>
          </el-table-column>
          <el-table-column label="出库单价" prop="unitPrice" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.unitPrice"
                               placeholder="请输入出库单价"/>
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

    <!-- 审核出库单对话框 -->
    <el-dialog :title="auditTitle" v-model="openAudit" width="1000px" append-to-body>
      <el-form ref="outboundOrderInfoAuditRef" :model="form" :rules="auditRules" label-width="80px">
        <el-form-item label="出库单号" prop="outboundNo">
          <el-input :readonly="true" v-model="form.outboundNo" placeholder="请输入出库单号"/>
        </el-form-item>
        <el-form-item label="出库类型" prop="outboundType">
          <el-select :disabled="true" v-model="form.outboundType" placeholder="请选择出库类型">
            <el-option
                v-for="dict in warehouse_outbound_type"
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
        <el-form-item label="领用人" prop="recipientId">
          <el-select
              :disabled="true"
              v-model="form.recipientId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入用户名称"
              :remote-method="remoteGetUserList"
              :loading="userLoading"
              style="width: 100%"
              @change="handleRecipientChange"
          >
            <el-option
                v-for="item in userList"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="出库日期" prop="outboundDate">
          <el-date-picker clearable
                          v-model="form.outboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择出库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否可归还" prop="isReturnable">
          <el-select v-model="form.isReturnable" placeholder="请选择是否可归还">
            <el-option
                v-for="dict in warehouse_is_returnable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经办人" prop="operatorId">
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
        <el-divider content-position="center">出库明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddOutboundOrderDetailInfo">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteOutboundOrderDetailInfo">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="outboundOrderDetailInfoList" :row-class-name="rowOutboundOrderDetailInfoIndex"
                  @selection-change="handleOutboundOrderDetailInfoSelectionChange" ref="outboundOrderDetailInfo">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="备件" prop="partsCode" width="250">
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
          <el-table-column label="可用数量" prop="availableQuantity" width="100">
            <template #default="scope">
              <span>{{ scope.row.availableQuantity || 0 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="出库数量" prop="outboundQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0"
                               v-model="scope.row.outboundQuantity"
                               placeholder="请输入出库数量"/>
            </template>
          </el-table-column>
          <el-table-column label="出库单价" prop="unitPrice" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.unitPrice"
                               placeholder="请输入出库单价"/>
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

<script setup name="OutboundOrderInfo">
import {
  listOutboundOrderInfo,
  getOutboundOrderInfo,
  delOutboundOrderInfo,
  addOutboundOrderInfo,
  updateOutboundOrderInfo,
  auditOutboundOrderInfo
} from "@/api/manage/outboundOrderInfo";
import {listWarehouseInfo} from "@/api/manage/warehouseInfo.js";
import {allocatedUserListAll} from "@/api/system/role.js";
import {listInventoryRecordInfo} from "@/api/manage/inventoryRecordInfo.js";

const {proxy} = getCurrentInstance();
const {
  warehouse_outbound_status,
  warehouse_outbound_type,
  warehouse_is_returnable,
  warehouse_applicant_status
} = proxy.useDict('warehouse_outbound_status', 'warehouse_outbound_type', 'warehouse_is_returnable', 'warehouse_applicant_status');

const outboundOrderInfoList = ref([]);
const outboundOrderDetailInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedOutboundOrderDetailInfo = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeOutboundDate = ref([]);
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/outboundOrderInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    outboundNo: null,
    outboundType: null,
    warehouseId: null,
    outboundDate: null,
    outboundStatus: null,
    reviewStatus: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    outboundNo: [
      {required: true, message: "出库单号不能为空", trigger: "blur"}
    ],
    outboundType: [
      {required: true, message: "出库类型不能为空", trigger: "change"}
    ],
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "change"}
    ],
    outboundDate: [
      {required: true, message: "出库日期不能为空", trigger: "change"}
    ],
    isReturnable: [
      {required: true, message: "是否可归还不能为空", trigger: "change"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '编号', visible: true},
    {key: 1, label: '出库单号', visible: true},
    {key: 2, label: '出库类型', visible: true},
    {key: 3, label: '仓库', visible: true},
    {key: 4, label: '领用人', visible: true},
    {key: 5, label: '领用部门', visible: true},
    {key: 6, label: '出库日期', visible: true},
    {key: 7, label: '出库状态', visible: true},
    {key: 8, label: '经办人', visible: true},
    {key: 9, label: '审核人', visible: true},
    {key: 10, label: '审核状态', visible: true},
    {key: 11, label: '审核时间', visible: true},
    {key: 12, label: '审批描述', visible: true},
    {key: 13, label: '创建人', visible: true},
    {key: 14, label: '创建时间', visible: true},
    {key: 15, label: '更新人', visible: false},
    {key: 16, label: '更新时间', visible: false},
    {key: 17, label: '备注', visible: false},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询出库单列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeOutboundDate && '' != daterangeOutboundDate) {
    queryParams.value.params["beginOutboundDate"] = daterangeOutboundDate.value[0];
    queryParams.value.params["endOutboundDate"] = daterangeOutboundDate.value[1];
  }
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listOutboundOrderInfo(queryParams.value).then(response => {
    outboundOrderInfoList.value = response.rows;
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
    outboundNo: null,
    outboundType: null,
    warehouseId: null,
    warehouseName: null,
    recipientId: null,
    recipientName: null,
    recipientDeptId: null,
    recipientDeptName: null,
    outboundDate: null,
    outboundStatus: null,
    isReturnable: null,
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
  outboundOrderDetailInfoList.value = [];
  proxy.resetForm("outboundOrderInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeOutboundDate.value = [];
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
  title.value = "添加出库单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getOutboundOrderInfo(_id).then(response => {
    form.value = response.data;
    outboundOrderDetailInfoList.value = response.data.outboundOrderDetailInfoList;
    // 转换出库数量为数字
    outboundOrderDetailInfoList.value.forEach(detail => {
      detail.outboundQuantity = Number(detail.outboundQuantity) || 0;
    });
    open.value = true;
    title.value = "修改出库单";
    // 为每个明细行设置 inventoryId
    nextTick(() => {
      outboundOrderDetailInfoList.value.forEach((detail, index) => {
        if (detail.partsCode && detail.locationId) {
          remoteGetInventoryRecordList(detail.partsCode, index);
        }
      });
    });
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["outboundOrderInfoRef"].validate(valid => {
    if (valid) {
      // 校验出库数量不能超过可用数量
      for (let i = 0; i < outboundOrderDetailInfoList.value.length; i++) {
        const detail = outboundOrderDetailInfoList.value[i];
        if (detail.inventoryId && detail.outboundQuantity > (detail.availableQuantity || 0)) {
          proxy.$modal.msgError(`第${i + 1}行出库数量不能超过可用数量${detail.availableQuantity || 0}`);
          return;
        }
      }
      form.value.outboundOrderDetailInfoList = outboundOrderDetailInfoList.value;
      if (form.value.id != null) {
        updateOutboundOrderInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOutboundOrderInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除出库单编号为"' + _ids + '"的数据项？').then(function () {
    return delOutboundOrderInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 出库明细序号 */
function rowOutboundOrderDetailInfoIndex({row, rowIndex}) {
  row.index = rowIndex + 1;
}

/** 出库明细添加按钮操作 */
function handleAddOutboundOrderDetailInfo() {
  let obj = {};
  obj.inventoryId = null;
  obj.partsCode = "";
  obj.partsName = "";
  obj.outboundQuantity = 0;
  obj.availableQuantity = 0;
  obj.locationId = null;
  obj.locationName = "";
  obj.batchNo = "";
  obj.warehouseId = form.value.warehouseId || null;
  obj.unitPrice = 0;
  obj.remark = "";
  outboundOrderDetailInfoList.value.push(obj);
}

/** 出库明细删除按钮操作 */
function handleDeleteOutboundOrderDetailInfo() {
  if (checkedOutboundOrderDetailInfo.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的出库明细数据");
  } else {
    const outboundOrderDetailInfos = outboundOrderDetailInfoList.value;
    const checkedOutboundOrderDetailInfos = checkedOutboundOrderDetailInfo.value;
    outboundOrderDetailInfoList.value = outboundOrderDetailInfos.filter(function (item) {
      return checkedOutboundOrderDetailInfos.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleOutboundOrderDetailInfoSelectionChange(selection) {
  checkedOutboundOrderDetailInfo.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(exportUrl.value, {
    ...queryParams.value
  }, `outboundOrderInfo_${new Date().getTime()}.xlsx`)
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

// ========== 用户列表（领用人） ==========
const userList = ref([]);
const userLoading = ref(false);
const userQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 2,
});
const getUserList = () => {
  userLoading.value = true;
  allocatedUserListAll(userQueryParams).then(response => {
    userList.value = response.rows;
    userLoading.value = false;
  });
};
const remoteGetUserList = (query) => {
  userQueryParams.userName = query;
  getUserList();
}

getUserList();

// ========== 用户列表（经办人） ==========
const operatorList = ref([]);
const operatorLoading = ref(false);
const operatorQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  userName: null,
  roleId: 100,
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
    // 编辑模式：根据 partsCode + locationId + batchNo 匹配并设置 inventoryId 和 availableQuantity
    const detail = outboundOrderDetailInfoList.value[rowIndex];
    if (detail && detail.partsCode && detail.locationId) {
      const matched = list.find(item =>
          item.partsCode === detail.partsCode &&
          item.locationId === detail.locationId &&
          (item.batchNo || '') === (detail.batchNo || '')
      );
      if (matched) {
        detail.inventoryId = matched.id;
        detail.availableQuantity = matched.availableQuantity;
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
  form.value.locationId = null;
  nextTick(() => {
    outboundOrderDetailInfoList.value.forEach((item, index) => {
      item.inventoryId = null;
      item.partsCode = "";
      item.partsName = "";
      item.availableQuantity = 0;
      item.locationId = null;
      item.locationName = "";
      item.batchNo = "";
    });
    rowInventoryRecordMap.value = {};
  });
};

// ========== 领用人变化 ==========
const handleRecipientChange = (userId) => {
  if (userId) {
    const selectedUser = userList.value.find(item => item.userId === userId);
    if (selectedUser) {
      form.value.recipientName = selectedUser.nickName;
      form.value.recipientDeptId = selectedUser.deptId;
      form.value.recipientDeptName = selectedUser.deptName;
    }
  } else {
    form.value.recipientName = null;
    form.value.recipientDeptId = null;
    form.value.recipientDeptName = null;
  }
};

// ========== 备件变化 ==========
const handleInventoryRecordChange = (inventoryId, rowIndex) => {
  const row = outboundOrderDetailInfoList.value[rowIndex];
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
      row.availableQuantity = selectedInventory.availableQuantity;
      // 不重置 outboundQuantity 和 unitPrice，保留已有值
    }
  } else {
    row.inventoryId = null;
    row.partsCode = "";
    row.partsName = "";
    row.locationId = null;
    row.locationName = "";
    row.batchNo = "";
    row.availableQuantity = 0;
  }
};

// ========== 审核相关 ==========
const openAudit = ref(false);
const auditTitle = ref("");
const auditRules = {
  reviewStatus: [
    {required: true, message: "审核状态不能为空", trigger: "change"}
  ],
};

function handleAudit(row) {
  getOutboundOrderInfo(row.id).then(response => {
    form.value = response.data;
    outboundOrderDetailInfoList.value = response.data.outboundOrderDetailInfoList;
    // 转换出库数量为数字
    outboundOrderDetailInfoList.value.forEach(detail => {
      detail.outboundQuantity = Number(detail.outboundQuantity) || 0;
    });
    openAudit.value = true;
    title.value = "审核出库单";
    // 为每个明细行设置 inventoryId
    nextTick(() => {
      outboundOrderDetailInfoList.value.forEach((detail, index) => {
        if (detail.partsCode && detail.locationId) {
          remoteGetInventoryRecordList(detail.partsCode, index);
        }
      });
    });
  });
}

function submitAuditForm() {
  proxy.$refs["outboundOrderInfoAuditRef"].validate(valid => {
    // 校验出库数量不能超过可用数量
    for (let i = 0; i < outboundOrderDetailInfoList.value.length; i++) {
      const detail = outboundOrderDetailInfoList.value[i];
      if (detail.inventoryId && detail.outboundQuantity > (detail.availableQuantity || 0)) {
        proxy.$modal.msgError(`第${i + 1}行出库数量不能超过可用数量${detail.availableQuantity || 0}`);
        return;
      }
    }
    if (valid) {
      form.value.outboundOrderDetailInfoList = outboundOrderDetailInfoList.value;
      auditOutboundOrderInfo(form.value).then(response => {
        proxy.$modal.msgSuccess("审核成功");
        openAudit.value = false;
        getList();
      });
    }
  });
}

getList();
</script>
