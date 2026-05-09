<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录ID" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入记录ID"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备件编号" prop="partsCode">
        <el-input
            v-model="queryParams.partsCode"
            placeholder="请输入备件编号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select
            v-model="queryParams.warehouseId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入仓库名称"
            :remote-method="remoteGetWarehouseList"
            :loading="warehouseLoading"
            style="width: 100%"
            clearable
            @change="handleSearchWarehouseChange"
        >
          <el-option
              v-for="item in warehouseList"
              :key="item.id"
              :label="item.warehouseName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库位" prop="locationId">
        <el-tree-select
            v-model="queryParams.locationId"
            :data="searchLocationOptions"
            :props="{ value: 'id', label: 'locationName', children: 'children' }"
            value-key="id"
            placeholder="请选择库位"
            check-strictly
            clearable
            filterable
            style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="批次号" prop="batchNo">
        <el-input
            v-model="queryParams.batchNo"
            placeholder="请输入批次号"
            clearable
            @keyup.enter="handleQuery"
        />
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
            v-hasPermi="['manage:inventoryRecordInfo:add']"
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
            v-hasPermi="['manage:inventoryRecordInfo:edit']"
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
            v-hasPermi="['manage:inventoryRecordInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:inventoryRecordInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryRecordInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="记录ID" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="备件编号" align="center" prop="partsName" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="仓库" align="center" prop="warehouseName" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="库位" align="center" prop="locationName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="批次号" align="center" prop="batchNo" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="库存数量" align="center" prop="quantity" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="冻结数量" align="center" prop="frozenQuantity" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="可用数量" align="center" prop="availableQuantity" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="有效期至" align="center" prop="expiryDate" width="180" v-if="columns[8].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.expiryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后入库日期" align="center" prop="lastInboundDate" width="180" v-if="columns[8].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.lastInboundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后出库日期" align="center" prop="lastOutboundDate" width="180"
                       v-if="columns[10].visible" :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.lastOutboundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[11].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[12].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[13].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[14].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[15].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:inventoryRecordInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:inventoryRecordInfo:remove']">删除
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

    <!-- 添加或修改库存记录对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="inventoryRecordInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="备件编号" prop="partsCode">
          <el-select
              v-model="form.partsCode"
              filterable
              remote
              reserve-keyword
              :disabled="form.id != null"
              placeholder="请输入备件名称"
              :remote-method="remoteGetSparePartsList"
              :loading="sparePartsLoading"
              style="width: 100%"
              @change="handleSparePartsChange"
          >
            <el-option
                v-for="item in sparePartsList"
                :key="item.id"
                :label="item.partsName"
                :value="item.partsCode"
            />
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
        <el-form-item label="库位" prop="locationId">
          <el-tree-select
              v-model="form.locationId"
              :data="locationInfoOptions"
              :props="{ value: 'id', label: 'locationName', children: 'children' }"
              value-key="id"
              placeholder="请选择库位"
              check-strictly
              clearable
              filterable
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="批次号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入批次号" />
        </el-form-item>
        <el-form-item label="库存数量" prop="quantity">
          <el-input-number style="width: 100%" :min="0" v-model="form.quantity" placeholder="请输入库存数量"
                           @change="calcAvailableQuantity"/>
        </el-form-item>
        <el-form-item label="冻结数量" prop="frozenQuantity">
          <el-input-number style="width: 100%" :min="0" v-model="form.frozenQuantity" placeholder="请输入冻结数量"
                           @change="calcAvailableQuantity"/>
        </el-form-item>
        <el-form-item label="可用数量" prop="availableQuantity">
          <el-input-number style="width: 100%" :min="0" v-model="computedAvailableQuantity"
                           placeholder="可用数量自动计算" disabled/>
        </el-form-item>
        <el-form-item label="有效期至" prop="expiryDate">
          <el-date-picker clearable
                          v-model="form.expiryDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择有效期至">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后入库日期" prop="lastInboundDate">
          <el-date-picker clearable
                          v-model="form.lastInboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择最后入库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后出库日期" prop="lastOutboundDate">
          <el-date-picker clearable
                          v-model="form.lastOutboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择最后出库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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

<script setup name="InventoryRecordInfo">
import {
  addInventoryRecordInfo,
  delInventoryRecordInfo,
  getInventoryRecordInfo,
  listInventoryRecordInfo,
  updateInventoryRecordInfo
} from "@/api/manage/inventoryRecordInfo";
import {listWarehouseInfo} from "@/api/manage/warehouseInfo.js";
import {listLocationInfo} from "@/api/manage/locationInfo.js";
import {listSparePartsInfo} from "@/api/manage/sparePartsInfo.js";

const {proxy} = getCurrentInstance();

const inventoryRecordInfoList = ref([]);
const locationInfoOptions = ref([]);
const searchLocationOptions = ref([]);
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
  exportUrl: 'manage/inventoryRecordInfo/export',
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    partsCode: null,
    warehouseId: null,
    locationId: null,
    batchNo: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    partsCode: [
      {required: true, message: "备件编号不能为空", trigger: "change"}
    ],
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "change"}
    ],
    batchNo: [
      {required: false, message: "批次号不能为空", trigger: "blur"}
    ],
    quantity: [
      {required: true, message: "库存数量不能为空", trigger: "blur"}
    ],
    frozenQuantity: [
      {required: true, message: "冻结数量不能为空", trigger: "blur"}
    ],
  },
  //表格展示列
  columns: [
    {key: 0, label: '记录ID', visible: true},
    {key: 1, label: '备件编号', visible: true},
    {key: 2, label: '仓库', visible: true},
    {key: 3, label: '库位', visible: true},
    {key: 4, label: '批次号', visible: true},
    {key: 5, label: '库存数量', visible: true},
    {key: 6, label: '冻结数量', visible: true},
    {key: 7, label: '可用数量', visible: true},
    {key: 8, label: '有效期至', visible: true},
    {key: 9, label: '最后入库日期', visible: true},
    {key: 10, label: '最后出库日期', visible: true},
    {key: 11, label: '创建人', visible: true},
    {key: 12, label: '创建时间', visible: true},
    {key: 13, label: '更新人', visible: false},
    {key: 14, label: '更新时间', visible: false},
    {key: 15, label: '备注', visible: false},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 计算可用数量 = 库存数量 - 冻结数量 */
function calcAvailableQuantity() {
  const q = form.value.quantity || 0;
  const f = form.value.frozenQuantity || 0;
  form.value.availableQuantity = q - f;
}

const computedAvailableQuantity = computed(() => {
  const q = form.value.quantity || 0;
  const f = form.value.frozenQuantity || 0;
  return q - f;
});

/** 查询库存记录列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listInventoryRecordInfo(queryParams.value).then(response => {
    inventoryRecordInfoList.value = response.rows;
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
    warehouseId: null,
    locationId: null,
    batchNo: null,
    quantity: 0,
    frozenQuantity: 0,
    availableQuantity: 0,
    expiryDate: null,
    lastInboundDate: null,
    lastOutboundDate: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("inventoryRecordInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeCreateTime.value = [];
  queryParams.value.warehouseId = null;
  queryParams.value.locationId = null;
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 查询库位下拉树结构（对话框用，按仓库过滤） */
function getTreeselect(warehouseId) {
  const params = warehouseId ? {warehouseId: warehouseId} : {};
  listLocationInfo(params).then(response => {
    locationInfoOptions.value = [];
    const data = {id: 0, locationName: '顶级节点', children: []};
    data.children = proxy.handleTree(response.data, "id", "parentId");
    locationInfoOptions.value.push(data);
  });
}

/** 查询搜索区域库位树（不带仓库过滤，全量） */
function getSearchLocationTree() {
  listLocationInfo().then(response => {
    searchLocationOptions.value = [];
    const data = {id: 0, locationName: '顶级节点', children: []};
    data.children = proxy.handleTree(response.data, "id", "parentId");
    searchLocationOptions.value.push(data);
  });
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getTreeselect(null);
  open.value = true;
  title.value = "添加库存记录";
  form.value.quantity = 0;
  form.value.frozenQuantity = 0;
  form.value.availableQuantity = 0;
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getInventoryRecordInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改库存记录";
    getTreeselect(form.value.warehouseId);
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["inventoryRecordInfoRef"].validate(valid => {
    if (valid) {
      calcAvailableQuantity();
      if (form.value.id != null) {
        updateInventoryRecordInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addInventoryRecordInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除库存记录编号为"' + _ids + '"的数据项？').then(function () {
    return delInventoryRecordInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(exportUrl.value, {
    ...queryParams.value
  }, `inventoryRecordInfo_${new Date().getTime()}.xlsx`)
}

// ===================== 仓库相关 =====================
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
};

/** 搜索区域 - 仓库变化时清空库位并刷新树 */
function handleSearchWarehouseChange(val) {
  queryParams.value.locationId = null;
  if (val) {
    getTreeselect(val).then(() => {
      searchLocationOptions.value = locationInfoOptions.value;
    });
  } else {
    searchLocationOptions.value = [];
    locationInfoOptions.value = [];
  }
}

/** 对话框 - 仓库变化时刷新库位树 */
function handleWarehouseChange(val) {
  form.value.locationId = null;
  if (val) {
    getTreeselect(val);
  } else {
    locationInfoOptions.value = [];
  }
}

// ===================== 备件相关 =====================
const sparePartsList = ref([]);
const sparePartsLoading = ref(false);
const sparePartsQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  partsName: null,
  partsStatus: '1'
});
const getSparePartsList = () => {
  sparePartsLoading.value = true;
  listSparePartsInfo(sparePartsQueryParams).then(response => {
    sparePartsList.value = response.rows;
    sparePartsLoading.value = false;
  });
};
const remoteGetSparePartsList = (query) => {
  sparePartsQueryParams.partsName = query;
  getSparePartsList();
};
const handleSparePartsChange = (val) => {
  const parts = sparePartsList.value.find(item => item.partsCode === val);
  if (parts) {
    form.value.partsName = parts.partsName;
  }
};

getWarehouseList();
getSparePartsList();
getSearchLocationTree();

getList();
</script>
