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
        >
          <el-option
              v-for="item in warehouseList"
              :key="item.id"
              :label="item.warehouseName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库位编码" prop="locationCode">
        <el-input
            v-model="queryParams.locationCode"
            placeholder="请输入库位编码"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位名称" prop="locationName">
        <el-input
            v-model="queryParams.locationName"
            placeholder="请输入库位名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父级" prop="parentId">
        <el-input
            v-model="queryParams.parentId"
            placeholder="请输入父级"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位层级" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择库位层级" clearable>
          <el-option
              v-for="dict in warehouse_location_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="locationStatus">
        <el-select v-model="queryParams.locationStatus" placeholder="请选择状态" clearable>
          <el-option
              v-for="dict in warehouse_common_status"
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
            v-hasPermi="['manage:locationInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="info"
            plain
            icon="Sort"
            @click="toggleExpandAll"
        >展开/折叠
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:locationInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table
        v-if="refreshTable"
        v-loading="loading"
        :data="locationInfoList"
        row-key="id"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="仓库" prop="warehouseName" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="库位编码" align="center" prop="locationCode" v-if="columns[2].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="库位名称" align="center" prop="locationName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="父级" align="center" prop="parentId" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="库位层级" align="center" prop="level" v-if="columns[5].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_location_level" :value="scope.row.level"/>
        </template>
      </el-table-column>
      <el-table-column label="容量" align="center" prop="capacity" v-if="columns[6].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="入库总数" align="center" prop="inboundQuantity" v-if="columns[7].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="出库总数" align="center" prop="outboundQuantity" v-if="columns[8].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="locationStatus" v-if="columns[9].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_common_status" :value="scope.row.locationStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[10].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[11].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[12].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[13].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" v-if="columns[14].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['manage:locationInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)"
                     v-hasPermi="['manage:locationInfo:add']">新增
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:locationInfo:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改库位对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="locationInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父级" prop="parentId">
          <el-tree-select
              v-model="form.parentId"
              :data="locationInfoOptions"
              :props="{ value: 'id', label: 'locationName', children: 'children' }"
              value-key="id"
              placeholder="请选择父级"
              check-strictly
          />
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
          >
            <el-option
                v-for="item in warehouseList"
                :key="item.id"
                :label="item.warehouseName"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="库位编码" prop="locationCode">
          <el-input v-model="form.locationCode" placeholder="请输入库位编码"/>
        </el-form-item>
        <el-form-item label="库位名称" prop="locationName">
          <el-input v-model="form.locationName" placeholder="请输入库位名称"/>
        </el-form-item>
        <el-form-item label="库位层级" prop="level">
          <el-select v-model="form.level" placeholder="请选择库位层级">
            <el-option
                v-for="dict in warehouse_location_level"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="容量" prop="capacity">
          <el-input-number :min="0" style="width: 100%" v-model="form.capacity" placeholder="请输入容量"/>
        </el-form-item>
        <!--        <el-form-item label="入库总数" prop="inboundQuantity">-->
        <!--          <el-input v-model="form.inboundQuantity" placeholder="请输入入库总数"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="出库总数" prop="outboundQuantity">-->
        <!--          <el-input v-model="form.outboundQuantity" placeholder="请输入出库总数"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="状态" prop="locationStatus">
          <el-select v-model="form.locationStatus" placeholder="请选择状态">
            <el-option
                v-for="dict in warehouse_common_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="LocationInfo">
import {
  listLocationInfo,
  getLocationInfo,
  delLocationInfo,
  addLocationInfo,
  updateLocationInfo
} from "@/api/manage/locationInfo";
import {listWarehouseInfo} from "@/api/manage/warehouseInfo.js";

const {proxy} = getCurrentInstance();
const {
  warehouse_location_level,
  warehouse_common_status
} = proxy.useDict('warehouse_location_level', 'warehouse_common_status');

const locationInfoList = ref([]);
const locationInfoOptions = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const title = ref("");
const isExpandAll = ref(true);
const refreshTable = ref(true);
const daterangeCreateTime = ref([]);

const data = reactive({
  form: {},
  // 导出地址
  exportUrl: 'manage/locationInfo/export',
  queryParams: {
    id: null,
    warehouseId: null,
    locationCode: null,
    locationName: null,
    parentId: null,
    level: null,
    locationStatus: null,
    createBy: null,
    createTime: null,
  },
  rules: {
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "blur"}
    ],
    locationCode: [
      {required: true, message: "库位编码不能为空", trigger: "blur"}
    ],
    level: [
      {required: true, message: "库位层级不能为空", trigger: "change"}
    ],
    inboundQuantity: [
      {required: true, message: "入库总数不能为空", trigger: "blur"}
    ],
    outboundQuantity: [
      {required: true, message: "出库总数不能为空", trigger: "blur"}
    ],
    locationStatus: [
      {required: true, message: "状态不能为空", trigger: "change"}
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
    {key: 1, label: '仓库', visible: true},
    {key: 2, label: '库位编码', visible: true},
    {key: 3, label: '库位名称', visible: true},
    {key: 4, label: '父级', visible: true},
    {key: 5, label: '库位层级', visible: true},
    {key: 6, label: '容量', visible: true},
    {key: 7, label: '入库总数', visible: true},
    {key: 8, label: '出库总数', visible: true},
    {key: 9, label: '状态', visible: true},
    {key: 10, label: '创建人', visible: true},
    {key: 11, label: '创建时间', visible: false},
    {key: 12, label: '更新人', visible: false},
    {key: 13, label: '更新时间', visible: false},
    {key: 14, label: '备注', visible: false},
  ],
});

const {queryParams, form, rules, columns, exportUrl} = toRefs(data);

/** 查询库位列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeCreateTime && '' != daterangeCreateTime) {
    queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
    queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
  }
  listLocationInfo(queryParams.value).then(response => {
    locationInfoList.value = proxy.handleTree(response.data, "id", "parentId");
    loading.value = false;
  });
}

/** 查询库位下拉树结构 */
function getTreeselect() {
  listLocationInfo().then(response => {
    locationInfoOptions.value = [];
    const data = {id: 0, locationName: '顶级节点', children: []};
    data.children = proxy.handleTree(response.data, "id", "parentId");
    locationInfoOptions.value.push(data);
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
    warehouseId: null,
    locationCode: null,
    locationName: null,
    parentId: null,
    level: null,
    capacity: null,
    inboundQuantity: null,
    outboundQuantity: null,
    locationStatus: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("locationInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeCreateTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd(row) {
  reset();
  getTreeselect();
  if (row != null && row.id) {
    form.value.parentId = row.id;
  } else {
    form.value.parentId = 0;
  }
  open.value = true;
  title.value = "添加库位";
}

/** 展开/折叠操作 */
function toggleExpandAll() {
  refreshTable.value = false;
  isExpandAll.value = !isExpandAll.value;
  nextTick(() => {
    refreshTable.value = true;
  });
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset();
  await getTreeselect();
  if (row != null) {
    form.value.parentId = row.id;
  }
  getLocationInfo(row.id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改库位";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["locationInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLocationInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLocationInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除库位编号为"' + row.id + '"的数据项？').then(function () {
    return delLocationInfo(row.id);
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
  }, `locationInfo_${new Date().getTime()}.xlsx`)
}

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

getWarehouseList()

getList();
</script>
