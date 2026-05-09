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
        <el-input
            v-model="queryParams.warehouseId"
            placeholder="请输入仓库"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位" prop="locationId">
        <el-input
            v-model="queryParams.locationId"
            placeholder="请输入库位"
            clearable
            @keyup.enter="handleQuery"
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
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="inventoryRecordInfoRef" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="备件编号" prop="partsCode">-->
<!--          <el-input v-model="form.partsCode" placeholder="请输入备件编号"/>-->
<!--        </el-form-item>-->
        <el-form-item label="仓库" prop="warehouseId">
          <el-input v-model="form.warehouseId" placeholder="请输入仓库"/>
        </el-form-item>
        <el-form-item label="库位" prop="locationId">
          <el-input v-model="form.locationId" placeholder="请输入库位"/>
        </el-form-item>
        <el-form-item label="批次号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入批次号"/>
        </el-form-item>
        <el-form-item label="库存数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入库存数量"/>
        </el-form-item>
        <el-form-item label="冻结数量" prop="frozenQuantity">
          <el-input v-model="form.frozenQuantity" placeholder="请输入冻结数量"/>
        </el-form-item>
        <el-form-item label="可用数量" prop="availableQuantity">
          <el-input v-model="form.availableQuantity" placeholder="请输入可用数量"/>
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

const {proxy} = getCurrentInstance();

const inventoryRecordInfoList = ref([]);
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
      {required: true, message: "备件编号不能为空", trigger: "blur"}
    ],
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "blur"}
    ],
    quantity: [
      {required: true, message: "库存数量不能为空", trigger: "blur"}
    ],
    frozenQuantity: [
      {required: true, message: "冻结数量不能为空", trigger: "blur"}
    ],
    availableQuantity: [
      {required: true, message: "可用数量不能为空", trigger: "blur"}
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
    quantity: null,
    frozenQuantity: null,
    availableQuantity: null,
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
  title.value = "添加库存记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getInventoryRecordInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改库存记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["inventoryRecordInfoRef"].validate(valid => {
    if (valid) {
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

getList();
</script>
