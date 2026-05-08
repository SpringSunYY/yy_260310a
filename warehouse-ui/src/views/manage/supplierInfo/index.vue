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
                  <el-form-item label="供应商编码" prop="supplierCode">
                    <el-input
                        v-model="queryParams.supplierCode"
                        placeholder="请输入供应商编码"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="供应商名称" prop="supplierName">
                    <el-input
                        v-model="queryParams.supplierName"
                        placeholder="请输入供应商名称"
                        clearable
                        @keyup.enter="handleQuery"
                    />
                  </el-form-item>
                  <el-form-item label="信用等级" prop="creditLevel">
                    <el-select v-model="queryParams.creditLevel" style="width: 200px" placeholder="请选择信用等级" clearable>
                      <el-option
                          v-for="dict in warehouse_suppliercredit_level"
                          :key="dict.value"
                          :label="dict.label"
                          :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="状态" prop="supplierStatus">
                    <el-select v-model="queryParams.supplierStatus" style="width: 200px" placeholder="请选择状态" clearable>
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
            v-hasPermi="['manage:supplierInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['manage:supplierInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:supplierInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:supplierInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="供应商编码" align="center" prop="supplierCode" v-if="columns[1].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="供应商名称" align="center" prop="supplierName" v-if="columns[2].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="联系人" align="center" prop="contactPerson" v-if="columns[3].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="联系电话" align="center" prop="contactPhone" v-if="columns[4].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="联系邮箱" align="center" prop="contactEmail" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="地址" align="center" prop="address" v-if="columns[6].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="信用等级" align="center" prop="creditLevel" v-if="columns[7].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_suppliercredit_level" :value="scope.row.creditLevel"/>
                </template>
              </el-table-column>
                <el-table-column label="状态" align="center" prop="supplierStatus" v-if="columns[8].visible">
                <template #default="scope">
                      <dict-tag :options="warehouse_common_status" :value="scope.row.supplierStatus"/>
                </template>
              </el-table-column>
                <el-table-column label="创建人" align="center" prop="createBy" v-if="columns[9].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="创建时间" align="center" prop="createTime" width="180" v-if="columns[10].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="更新人" align="center" prop="updateBy" v-if="columns[11].visible" :show-overflow-tooltip="true"/>
                <el-table-column label="更新时间" align="center" prop="updateTime" width="180" v-if="columns[12].visible" :show-overflow-tooltip="true">
                <template #default="scope">
                  <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
                <el-table-column label="备注" align="center" prop="remark" v-if="columns[13].visible" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:supplierInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:supplierInfo:remove']">删除</el-button>
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

    <!-- 添加或修改供应商信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="supplierInfoRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="供应商编码" prop="supplierCode">
                          <el-input v-model="form.supplierCode" placeholder="请输入供应商编码" />
                        </el-form-item>
                        <el-form-item label="供应商名称" prop="supplierName">
                          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
                        </el-form-item>
                        <el-form-item label="联系人" prop="contactPerson">
                          <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
                        </el-form-item>
                        <el-form-item label="联系电话" prop="contactPhone">
                          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
                        </el-form-item>
                        <el-form-item label="联系邮箱" prop="contactEmail">
                          <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" />
                        </el-form-item>
                        <el-form-item label="地址" prop="address">
                          <el-input v-model="form.address" placeholder="请输入地址" />
                        </el-form-item>
                        <el-form-item label="信用等级" prop="creditLevel">
                          <el-select v-model="form.creditLevel" placeholder="请选择信用等级">
                            <el-option
                                v-for="dict in warehouse_suppliercredit_level"
                                :key="dict.value"
                                :label="dict.label"
                                :value="dict.value"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item label="状态" prop="supplierStatus">
                          <el-select v-model="form.supplierStatus" placeholder="请选择状态">
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

<script setup name="SupplierInfo">
  import { listSupplierInfo, getSupplierInfo, delSupplierInfo, addSupplierInfo, updateSupplierInfo } from "@/api/manage/supplierInfo";

  const { proxy } = getCurrentInstance();
      const { warehouse_common_status, warehouse_suppliercredit_level } = proxy.useDict('warehouse_common_status', 'warehouse_suppliercredit_level');

  const supplierInfoList = ref([]);
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
    exportUrl: 'manage/supplierInfo/export',
    queryParams: {
      pageNum: 1,
      pageSize: 10,
                    id: null,
                    supplierCode: null,
                    supplierName: null,
                    creditLevel: null,
                    supplierStatus: null,
                    createBy: null,
                    createTime: null,
    },
    rules: {
                    supplierCode: [
                { required: true, message: "供应商编码不能为空", trigger: "blur" }
              ],
                    supplierName: [
                { required: true, message: "供应商名称不能为空", trigger: "blur" }
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
                { key: 1, label: '供应商编码', visible: true },
                { key: 2, label: '供应商名称', visible: true },
                { key: 3, label: '联系人', visible: true },
                { key: 4, label: '联系电话', visible: true },
                { key: 5, label: '联系邮箱', visible: true },
                { key: 6, label: '地址', visible: true },
                { key: 7, label: '信用等级', visible: true },
                { key: 8, label: '状态', visible: true },
                { key: 9, label: '创建人', visible: true },
                { key: 10, label: '创建时间', visible: true },
                { key: 11, label: '更新人', visible: true },
                { key: 12, label: '更新时间', visible: true },
                { key: 13, label: '备注', visible: true },
      ],
  });

  const { queryParams, form, rules, columns, exportUrl } = toRefs(data);

  /** 查询供应商信息列表 */
  function getList() {
    loading.value = true;
            queryParams.value.params = {};
            if (null != daterangeCreateTime && '' != daterangeCreateTime) {
              queryParams.value.params["beginCreateTime"] = daterangeCreateTime.value[0];
              queryParams.value.params["endCreateTime"] = daterangeCreateTime.value[1];
            }
    listSupplierInfo(queryParams.value).then(response => {
            supplierInfoList.value = response.rows;
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
                    supplierCode: null,
                    supplierName: null,
                    contactPerson: null,
                    contactPhone: null,
                    contactEmail: null,
                    address: null,
                    creditLevel: null,
                    supplierStatus: null,
                    createBy: null,
                    createTime: null,
                    updateBy: null,
                    updateTime: null,
                    remark: null
    };
    proxy.resetForm("supplierInfoRef");
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
    title.value = "添加供应商信息";
  }

  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getSupplierInfo(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改供应商信息";
    });
  }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["supplierInfoRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateSupplierInfo(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addSupplierInfo(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除供应商信息编号为"' + _ids + '"的数据项？').then(function() {
      return delSupplierInfo(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }

  /** 导出按钮操作 */
  function handleExport() {
    proxy.download(exportUrl.value, {
      ...queryParams.value
    }, `supplierInfo_${new Date().getTime()}.xlsx`)
  }

  getList();
</script>
