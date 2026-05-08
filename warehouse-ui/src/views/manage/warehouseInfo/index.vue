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
                  <el-form-item label="仓库编码" prop="warehouseCode">
                    <el-input
                        v-model="queryParams.warehouseCode"
                        placeholder="请输入仓库编码"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="仓库名称" prop="warehouseName">
                    <el-input
                        v-model="queryParams.warehouseName"
                        placeholder="请输入仓库名称"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="仓库类型" prop="warehouseType">
                    <el-select v-model="queryParams.warehouseType" style="width: 200px" placeholder="请选择仓库类型" clearable>
                      <el-option
                          v-for="dict in warehouse_warehouse_type"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="联系电话" prop="contactPhone">
                    <el-input
                        v-model="queryParams.contactPhone"
                        placeholder="请输入联系电话"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="状态" prop="warehouseStatus">
                    <el-select v-model="queryParams.warehouseStatus" style="width: 200px" placeholder="请选择状态" clearable>
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
            v-hasPermi="['manage:warehouseInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:warehouseInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:warehouseInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:warehouseInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="仓库编码" align="center" prop="warehouseCode" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="仓库名称" align="center" prop="warehouseName" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="仓库类型" align="center" prop="warehouseType" v-if="columns[3].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_warehouse_type" :value="scope.row.warehouseType"/>
                </template>
              </el-table-column>
                <el-table-column label="仓库位置" align="center" prop="location" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="仓库面积" align="center" prop="area" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="仓库管理员" align="center" prop="userId" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="总容量" align="center" prop="totalCapacity" v-if="columns[7].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="入库总数" align="center" prop="inboundQuantity" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="出库总数" align="center" prop="outbountQuantiy" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="联系电话" align="center" prop="contactPhone" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="状态" align="center" prop="warehouseStatus" v-if="columns[11].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_common_status" :value="scope.row.warehouseStatus"/>
                </template>
              </el-table-column>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:warehouseInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:warehouseInfo:remove']">删除</el-button>
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

    <!-- 添加或修改仓库对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="warehouseInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="仓库编码" prop="warehouseCode">
                          <el-input v-model="form.warehouseCode" placeholder="请输入仓库编码" />
                        </el-form-item>
                        <el-form-item label="仓库名称" prop="warehouseName">
                          <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
                        </el-form-item>
                        <el-form-item label="仓库类型" prop="warehouseType">
                          <el-select v-model="form.warehouseType" placeholder="请选择仓库类型">
                            <el-option
                                v-for="dict in warehouse_warehouse_type"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="仓库位置" prop="location">
                          <el-input v-model="form.location" placeholder="请输入仓库位置" />
                        </el-form-item>
                        <el-form-item label="仓库面积" prop="area">
                          <el-input v-model="form.area" placeholder="请输入仓库面积" />
                        </el-form-item>
                        <el-form-item label="仓库管理员" prop="userId">
                          <el-input v-model="form.userId" placeholder="请输入仓库管理员" />
                        </el-form-item>
                        <el-form-item label="总容量" prop="totalCapacity">
                          <el-input v-model="form.totalCapacity" placeholder="请输入总容量" />
                        </el-form-item>
                        <el-form-item label="入库总数" prop="inboundQuantity">
                          <el-input v-model="form.inboundQuantity" placeholder="请输入入库总数" />
                        </el-form-item>
                        <el-form-item label="出库总数" prop="outbountQuantiy">
                          <el-input v-model="form.outbountQuantiy" placeholder="请输入出库总数" />
                        </el-form-item>
                        <el-form-item label="联系电话" prop="contactPhone">
                          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
                        </el-form-item>
                        <el-form-item label="状态" prop="warehouseStatus">
                          <el-select v-model="form.warehouseStatus" placeholder="请选择状态">
                            <el-option
                                v-for="dict in warehouse_common_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
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

<script setup name="WarehouseInfo">
  import { listWarehouseInfo, getWarehouseInfo, delWarehouseInfo, addWarehouseInfo, updateWarehouseInfo } from "@/api/manage/warehouseInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_warehouse_type, warehouse_common_status } = proxy.useDict('warehouse_warehouse_type', 'warehouse_common_status');

  const warehouseInfoList = ref([]);
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
    exportUrl: 'manage/warehouseInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    warehouseCode: null,
                    warehouseName: null,
                    warehouseType: null,
                    contactPhone: null,
                    warehouseStatus: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    warehouseCode: [
                { required: true, message: "仓库编码不能为空", trigger: "blur" }
              ],
                    warehouseName: [
                { required: true, message: "仓库名称不能为空", trigger: "blur" }
              ],
                    inboundQuantity: [
                { required: true, message: "入库总数不能为空", trigger: "blur" }
              ],
                    outbountQuantiy: [
                { required: true, message: "出库总数不能为空", trigger: "blur" }
              ],
                    warehouseStatus: [
                { required: true, message: "状态不能为空", trigger: "change" }
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
                { key: 1, label: '仓库编码', visible: true },
                { key: 2, label: '仓库名称', visible: true },
                { key: 3, label: '仓库类型', visible: true },
                { key: 4, label: '仓库位置', visible: true },
                { key: 5, label: '仓库面积', visible: true },
                { key: 6, label: '仓库管理员', visible: true },
                { key: 7, label: '总容量', visible: true },
                { key: 8, label: '入库总数', visible: true },
                { key: 9, label: '出库总数', visible: true },
                { key: 10, label: '联系电话', visible: true },
                { key: 11, label: '状态', visible: true },
                { key: 12, label: '创建人', visible: true },
                { key: 13, label: '创建时间', visible: true },
                { key: 14, label: '更新人', visible: true },
                { key: 15, label: '更新时间', visible: true },
                { key: 16, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询仓库列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listWarehouseInfo(queryParams.value).then(response => {
            warehouseInfoList.value = response.rows;
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
                    warehouseCode: null,
                    warehouseName: null,
                    warehouseType: null,
                    location: null,
                    area: null,
                    userId: null,
                    totalCapacity: null,
                    inboundQuantity: null,
                    outbountQuantiy: null,
                    contactPhone: null,
                    warehouseStatus: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("warehouseInfoRef");
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
    title.value = "添加仓库";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getWarehouseInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改仓库";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["warehouseInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateWarehouseInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addWarehouseInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除仓库编号为"' + _ids + '"的数据项？').then(function() {
      return delWarehouseInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `warehouseInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
