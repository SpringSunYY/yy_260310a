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
                  <el-form-item label="规格型号" prop="specificationModel">
                    <el-input
                        v-model="queryParams.specificationModel"
                        placeholder="请输入规格型号"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="分类" prop="category">
                    <el-select v-model="queryParams.category" style="width: 200px" placeholder="请选择分类" clearable>
                      <el-option
                          v-for="dict in warehouse_parts_category"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="状态" prop="partsStatus">
                    <el-select v-model="queryParams.partsStatus" style="width: 200px" placeholder="请选择状态" clearable>
                      <el-option
                          v-for="dict in warehouse_common_status"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="主供应商" prop="supplierId">
                    <el-input
                        v-model="queryParams.supplierId"
                        placeholder="请输入主供应商"
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
            v-hasPermi="['manage:sparePartsInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:sparePartsInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:sparePartsInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:sparePartsInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sparePartsInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="备件编码" align="center" prop="partsCode" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="备件名称" align="center" prop="partsName" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="规格型号" align="center" prop="specificationModel" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="单位" align="center" prop="unit" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="分类" align="center" prop="category" v-if="columns[5].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_parts_category" :value="scope.row.category"/>
                </template>
              </el-table-column>
                <el-table-column label="状态" align="center" prop="partsStatus" v-if="columns[6].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_common_status" :value="scope.row.partsStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="存储条件" align="center" prop="storageCondition" v-if="columns[7].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="单价" align="center" prop="unitPrice" v-if="columns[8].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="最小库存量" align="center" prop="minStock" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="最大库存量" align="center" prop="maxStock" v-if="columns[10].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="当前库存量" align="center" prop="currentStock" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="主供应商" align="center" prop="supplierId" v-if="columns[12].visible" :show-overflow-tooltip="true"/>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:sparePartsInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:sparePartsInfo:remove']">删除</el-button>
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

    <!-- 添加或修改备品备件信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="sparePartsInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="备件编码" prop="partsCode">
                          <el-input v-model="form.partsCode" placeholder="请输入备件编码" />
                        </el-form-item>
                        <el-form-item label="备件名称" prop="partsName">
                          <el-input v-model="form.partsName" placeholder="请输入备件名称" />
                        </el-form-item>
                        <el-form-item label="规格型号" prop="specificationModel">
                          <el-input v-model="form.specificationModel" placeholder="请输入规格型号" />
                        </el-form-item>
                        <el-form-item label="单位" prop="unit">
                          <el-input v-model="form.unit" placeholder="请输入单位" />
                        </el-form-item>
                        <el-form-item label="分类" prop="category">
                          <el-select v-model="form.category" placeholder="请选择分类">
                            <el-option
                                v-for="dict in warehouse_parts_category"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="状态" prop="partsStatus">
                          <el-select v-model="form.partsStatus" placeholder="请选择状态">
                            <el-option
                                v-for="dict in warehouse_common_status"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="存储条件" prop="storageCondition">
                          <el-input v-model="form.storageCondition" placeholder="请输入存储条件" />
                        </el-form-item>
                        <el-form-item label="单价" prop="unitPrice">
                          <el-input v-model="form.unitPrice" placeholder="请输入单价" />
                        </el-form-item>
                        <el-form-item label="最小库存量" prop="minStock">
                          <el-input v-model="form.minStock" placeholder="请输入最小库存量" />
                        </el-form-item>
                        <el-form-item label="最大库存量" prop="maxStock">
                          <el-input v-model="form.maxStock" placeholder="请输入最大库存量" />
                        </el-form-item>
                        <el-form-item label="当前库存量" prop="currentStock">
                          <el-input v-model="form.currentStock" placeholder="请输入当前库存量" />
                        </el-form-item>
                        <el-form-item label="主供应商" prop="supplierId">
                          <el-input v-model="form.supplierId" placeholder="请输入主供应商" />
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

<script setup name="SparePartsInfo">
  import { listSparePartsInfo, getSparePartsInfo, delSparePartsInfo, addSparePartsInfo, updateSparePartsInfo } from "@/api/manage/sparePartsInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_parts_category, warehouse_common_status } = proxy.useDict('warehouse_parts_category', 'warehouse_common_status');

  const sparePartsInfoList = ref([]);
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
    exportUrl: 'manage/sparePartsInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    partsCode: null,
                    partsName: null,
                    specificationModel: null,
                    category: null,
                    partsStatus: null,
                    supplierId: null,
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
                    specificationModel: [
                { required: true, message: "规格型号不能为空", trigger: "blur" }
              ],
                    category: [
                { required: true, message: "分类不能为空", trigger: "change" }
              ],
                    partsStatus: [
                { required: true, message: "状态不能为空", trigger: "change" }
              ],
                    unitPrice: [
                { required: true, message: "单价不能为空", trigger: "blur" }
              ],
                    minStock: [
                { required: true, message: "最小库存量不能为空", trigger: "blur" }
              ],
                    maxStock: [
                { required: true, message: "最大库存量不能为空", trigger: "blur" }
              ],
                    currentStock: [
                { required: true, message: "当前库存量不能为空", trigger: "blur" }
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
                { key: 3, label: '规格型号', visible: true },
                { key: 4, label: '单位', visible: true },
                { key: 5, label: '分类', visible: true },
                { key: 6, label: '状态', visible: true },
                { key: 7, label: '存储条件', visible: true },
                { key: 8, label: '单价', visible: true },
                { key: 9, label: '最小库存量', visible: true },
                { key: 10, label: '最大库存量', visible: true },
                { key: 11, label: '当前库存量', visible: true },
                { key: 12, label: '主供应商', visible: true },
                { key: 13, label: '创建人', visible: true },
                { key: 14, label: '创建时间', visible: true },
                { key: 15, label: '更新人', visible: true },
                { key: 16, label: '更新时间', visible: true },
                { key: 17, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询备品备件信息列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listSparePartsInfo(queryParams.value).then(response => {
            sparePartsInfoList.value = response.rows;
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
                    specificationModel: null,
                    unit: null,
                    category: null,
                    partsStatus: null,
                    storageCondition: null,
                    unitPrice: null,
                    minStock: null,
                    maxStock: null,
                    currentStock: null,
                    supplierId: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("sparePartsInfoRef");
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
    title.value = "添加备品备件信息";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getSparePartsInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改备品备件信息";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["sparePartsInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateSparePartsInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addSparePartsInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除备品备件信息编号为"' + _ids + '"的数据项？').then(function() {
      return delSparePartsInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `sparePartsInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
