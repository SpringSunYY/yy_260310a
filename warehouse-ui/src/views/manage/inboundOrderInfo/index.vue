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
            v-hasPermi="['manage:inboundOrderInfo:edit']"
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
            v-hasPermi="['manage:inboundOrderInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['manage:inboundOrderInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inboundOrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id" v-if="columns[0].visible" :show-overflow-tooltip="true"/>
      <el-table-column label="入库单号" align="center" prop="inboundNo" v-if="columns[1].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="入库类型" align="center" prop="inboundType" v-if="columns[2].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_inbound_type" :value="scope.row.inboundType"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库" align="center" prop="warehouseName" v-if="columns[3].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="关联订单" align="center" prop="orderNo" v-if="columns[4].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="供应商" align="center" prop="supplierName" v-if="columns[5].visible"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="入库日期" align="center" prop="inboundDate" width="180" v-if="columns[6].visible"
                       :show-overflow-tooltip="true">
        <template #default="scope">
          <span>{{ parseTime(scope.row.inboundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库状态" align="center" prop="inboundStatus" v-if="columns[7].visible">
        <template #default="scope">
          <dict-tag :options="warehouse_inbound_status" :value="scope.row.inboundStatus"/>
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
                     v-hasPermi="['manage:inboundOrderInfo:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['manage:inboundOrderInfo:remove']">删除
          </el-button>
          <el-button link type="primary" icon="Check" @click="handleAudit(scope.row)"
                     v-if="scope.row.inboundStatus === '0' && scope.row.reviewStatus === '0'"
                     v-hasPermi="['manage:inboundOrderInfo:audit']">审核
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

    <!-- 添加或修改入库单对话框 -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form ref="inboundOrderInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="入库单号" prop="inboundNo">
          <el-input v-model="form.inboundNo" placeholder="请输入入库单号"/>
        </el-form-item>
        <el-form-item label="入库类型" prop="inboundType">
          <el-select v-model="form.inboundType" placeholder="请选择入库类型" @change="handleInboundTypeChange">
            <el-option
                v-for="dict in warehouse_inbound_type"
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
        <el-form-item label="关联订单" prop="orderId" v-if="form.inboundType === '0'">
          <el-select
              v-model="form.orderId"
              filterable
              remote
              reserve-keyword
              placeholder="请选择订单"
              :remote-method="remoteGetPurchaseOrderList"
              :loading="purchaseOrderLoading"
              style="width: 100%"
              @change="handleOrderChange"
          >
            <el-option
                v-for="item in purchaseOrderList"
                :key="item.orderId"
                :label="item.orderNo"
                :value="item.orderId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select
              v-model="form.supplierId"
              filterable
              remote
              reserve-keyword
              :disabled="form.inboundType === '0'"
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
        <el-form-item label="入库日期" prop="inboundDate">
          <el-date-picker clearable
                          v-model="form.inboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择入库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经办人" prop="operatorId">
          <el-select
              v-model="form.operatorId"
              filterable
              remote
              reserve-keyword
              :disabled="form.inboundType === '0'"
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
        <el-table :data="inboundOrderDetailInfoList" :row-class-name="rowInboundOrderDetailInfoIndex"
                  @selection-change="handleInboundOrderDetailInfoSelectionChange" ref="inboundOrderDetailInfo">
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
                  @change="(val) => handleSparePartsChange(val, scope.$index)"
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
          <el-table-column label="入库数量" prop="inboundQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="1" v-model="scope.row.inboundQuantity"
                               placeholder="请输入入库数量"/>
            </template>
          </el-table-column>
          <el-table-column label="批次号" prop="batchNo" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.batchNo" placeholder="请输入批次号"/>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="locationId" width="200">
            <template #default="scope">
              <el-tree-select
                  v-model="scope.row.locationId"
                  :data="locationInfoOptions"
                  :props="{ value: 'id', label: 'locationName', children: 'children' }"
                  value-key="id"
                  placeholder="请选择库位"
                  check-strictly
                  style="width: 100%"
              />
            </template>
          </el-table-column>
          <el-table-column label="质量状态" prop="qualityStatus" width="150">
            <template #default="scope">
              <el-select v-model="scope.row.qualityStatus" placeholder="请选择质量状态">
                <el-option
                    v-for="dict in warehouse_quality_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productionDate" width="180">
            <template #default="scope">
              <el-date-picker clearable
                              v-model="scope.row.productionDate"
                              type="date"
                              value-format="YYYY-MM-DD"
                              placeholder="请选择生产日期">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="有效期至" prop="expiryDate" width="180">
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
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.unitPrice"
                               placeholder="请输入入库单价"/>
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

    <!-- 审核入库单对话框 -->
    <el-dialog :title="auditTitle" v-model="openAudit" width="900px" append-to-body>
      <el-form ref="inboundOrderInfoAuditRef" :model="form" :rules="auditRules" label-width="80px">
        <el-form-item label="入库单号" prop="inboundNo">
          <el-input :readonly="true" v-model="form.inboundNo" placeholder="请输入入库单号"/>
        </el-form-item>
        <el-form-item label="入库类型" prop="inboundType">
          <el-select v-model="form.inboundType" placeholder="请选择入库类型" @change="handleInboundTypeChange">
            <el-option
                :disabled="true"
                v-for="dict in warehouse_inbound_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关联订单" prop="orderId" v-if="form.inboundType === '0'">
          <el-select
              :disabled="true"
              v-model="form.orderId"
              filterable
              remote
              reserve-keyword
              placeholder="请选择订单"
              :remote-method="remoteGetPurchaseOrderList"
              :loading="purchaseOrderLoading"
              style="width: 100%"
              @change="handleOrderChange"
          >
            <el-option
                v-for="item in purchaseOrderList"
                :key="item.orderId"
                :label="item.orderNo"
                :value="item.orderId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select
              v-model="form.supplierId"
              filterable
              remote
              reserve-keyword
              :disabled="true"
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
        <el-form-item label="入库日期" prop="inboundDate">
          <el-date-picker clearable
                          v-model="form.inboundDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择入库日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经办人" prop="operatorId">
          <el-select
              v-model="form.operatorId"
              filterable
              remote
              reserve-keyword
              :disabled="true"
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
        <el-divider content-position="center">入库明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddInboundOrderDetailInfo">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteInboundOrderDetailInfo">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="inboundOrderDetailInfoList" :row-class-name="rowInboundOrderDetailInfoIndex"
                  @selection-change="handleInboundOrderDetailInfoSelectionChange" ref="inboundOrderDetailInfoAudit">
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
                  @change="(val) => handleSparePartsChange(val, scope.$index)"
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
          <el-table-column label="入库数量" prop="inboundQuantity" width="150">
            <template #default="scope">
              <el-input-number style="width: 100%" :min="0" v-model="scope.row.inboundQuantity"
                               placeholder="请输入入库数量"/>
            </template>
          </el-table-column>
          <el-table-column label="批次号" prop="batchNo" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.batchNo" placeholder="请输入批次号"/>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="locationId" width="200">
            <template #default="scope">
              <el-tree-select
                  v-model="scope.row.locationId"
                  :data="locationInfoOptions"
                  :props="{ value: 'id', label: 'locationName', children: 'children' }"
                  value-key="id"
                  placeholder="请选择库位"
                  check-strictly
                  style="width: 100%"
              />
            </template>
          </el-table-column>
          <el-table-column label="质量状态" prop="qualityStatus" width="150">
            <template #default="scope">
              <el-select v-model="scope.row.qualityStatus" placeholder="请选择质量状态">
                <el-option
                    v-for="dict in warehouse_quality_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productionDate" width="180">
            <template #default="scope">
              <el-date-picker clearable
                              v-model="scope.row.productionDate"
                              type="date"
                              value-format="YYYY-MM-DD"
                              placeholder="请选择生产日期">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="有效期至" prop="expiryDate" width="180">
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
              <el-input-number style="width: 100%" :min="0" :precision="2" v-model="scope.row.unitPrice"
                               placeholder="请输入入库单价"/>
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

<script setup name="InboundOrderInfo">
import {
  listInboundOrderInfo,
  getInboundOrderInfo,
  delInboundOrderInfo,
  addInboundOrderInfo,
  updateInboundOrderInfo,
  auditInboundOrderInfo
} from "@/api/manage/inboundOrderInfo";
import {listWarehouseInfo} from "@/api/manage/warehouseInfo.js";
import {listSupplierInfo} from "@/api/manage/supplierInfo.js";
import {listSparePartsInfo} from "@/api/manage/sparePartsInfo.js";
import {listLocationInfo} from "@/api/manage/locationInfo.js";
import {listPurchaseOrderInfo, getPurchaseOrderInfo} from "@/api/manage/purchaseOrderInfo.js";
import {allocatedUserListAll} from "@/api/system/role.js";

const {proxy} = getCurrentInstance();
const {
  warehouse_inbound_type,
  warehouse_inbound_status,
  warehouse_applicant_status,
  warehouse_quality_status
} = proxy.useDict('warehouse_inbound_type', 'warehouse_quality_status', 'warehouse_inbound_status', 'warehouse_applicant_status');

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
      {required: true, message: "入库单号不能为空", trigger: "blur"}
    ],
    inboundType: [
      {required: true, message: "入库类型不能为空", trigger: "change"}
    ],
    warehouseId: [
      {required: true, message: "仓库不能为空", trigger: "blur"}
    ],
    inboundDate: [
      {required: true, message: "入库日期不能为空", trigger: "blur"}
    ],
    inboundStatus: [
      {required: true, message: "入库状态不能为空", trigger: "change"}
    ],
    reviewStatus: [
      {required: true, message: "审核状态不能为空", trigger: "change"}
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
    {key: 1, label: '入库单号', visible: true},
    {key: 2, label: '入库类型', visible: true},
    {key: 3, label: '仓库', visible: true},
    {key: 4, label: '关联订单', visible: true},
    {key: 5, label: '供应商', visible: true},
    {key: 6, label: '入库日期', visible: true},
    {key: 7, label: '入库状态', visible: true},
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
  openAudit.value = false;
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
  proxy.$modal.confirm('是否确认删除入库单编号为"' + _ids + '"的数据项？').then(function () {
    return delInboundOrderInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 入库明细序号 */
function rowInboundOrderDetailInfoIndex({row, rowIndex}) {
  row.index = rowIndex + 1;
}

/** 入库明细添加按钮操作 */
function handleAddInboundOrderDetailInfo() {
  let obj = {};
  obj.partsCode = "";
  obj.inboundQuantity = 0;
  obj.batchNo = "";
  obj.warehouseId = form.value.warehouseId || null;
  obj.locationId = null;
  obj.qualityStatus = "";
  obj.productionDate = "";
  obj.expiryDate = "";
  obj.unitPrice = 0;
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
    inboundOrderDetailInfoList.value = inboundOrderDetailInfos.filter(function (item) {
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

// ========== 供应商列表 ==========
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

getSupplierList();

// ========== 用户列表 ==========
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

getUserList();

// ========== 备件列表 ==========
const sparePartsList = ref([]);
const sparePartsLoading = ref(false);
const sparePartsQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  partsStatus: '1',
});
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

const getSparePartsListForRow = (rowIndex) => {
  if (rowSparePartsMap.value[rowIndex]) {
    return rowSparePartsMap.value[rowIndex];
  }
  return sparePartsList.value;
};

const handleSparePartsChange = (partsCode, rowIndex) => {
  const row = inboundOrderDetailInfoList.value[rowIndex];
  if (partsCode) {
    const spareParts = getSparePartsListForRow(rowIndex);
    const selectedSparePart = spareParts.find(item => item.partsCode === partsCode);
    if (selectedSparePart) {
      row.unitPrice = selectedSparePart.unitPrice || 0;
    }
  }
};

getSparePartsList();

// ========== 库位列表 ==========
const locationInfoOptions = ref([]);

const getLocationList = () => {
  const params = {pageNum: 1, pageSize: 1000};
  if (form.value.warehouseId) {
    params.warehouseId = form.value.warehouseId;
  }
  listLocationInfo(params).then(response => {
    locationInfoOptions.value = proxy.handleTree(response.data, "id", "parentId") || [];
  });
};

// ========== 订单列表 ==========
const purchaseOrderList = ref([]);
const purchaseOrderLoading = ref(false);
const purchaseOrderQueryParams = reactive({
  pageNum: 1,
  pageSize: 100,
  orderNo: null,
  orderStatus: '0',
  applicantStatus: '1',
});
const remoteGetPurchaseOrderList = (query) => {
  purchaseOrderQueryParams.orderNo = query;
  purchaseOrderLoading.value = true;
  listPurchaseOrderInfo(purchaseOrderQueryParams).then(response => {
    purchaseOrderList.value = response.rows;
    purchaseOrderLoading.value = false;
  });
}

// ========== 入库类型变化 ==========
const handleInboundTypeChange = (value) => {
  form.value.orderId = null;
  form.value.supplierId = null;
  form.value.operatorId = null;
  inboundOrderDetailInfoList.value = [];
};

// ========== 仓库变化 ==========
const handleWarehouseChange = (value) => {
  form.value.locationId = null;
  getLocationList();
  // 更新明细的库位选项
  nextTick(() => {
    inboundOrderDetailInfoList.value.forEach((item, index) => {
      item.locationId = null;
    });
  });
};

// ========== 订单变化 ==========
const handleOrderChange = (orderId) => {
  if (orderId) {
    // 清空明细
    inboundOrderDetailInfoList.value = [];
    getPurchaseOrderInfo(orderId).then(response => {
      const orderData = response.data;
      // 设置供应商
      form.value.supplierId = orderData.supplierId;
      // 设置经办人为订单申请人
      form.value.operatorId = orderData.applicantId;
      // 设置仓库（如果有的话）
      if (orderData.warehouseId) {
        form.value.warehouseId = orderData.warehouseId;
        getLocationList();
      }
      // 转换订单明细为入库明细
      if (orderData.purchaseOrderDetailInfoList && orderData.purchaseOrderDetailInfoList.length > 0) {
        inboundOrderDetailInfoList.value = orderData.purchaseOrderDetailInfoList.map(item => ({
          partsCode: item.partsCode,
          inboundQuantity: item.receivedQuantity || 0,
          batchNo: '',
          warehouseId: form.value.warehouseId,
          locationId: null,
          qualityStatus: '0',
          productionDate: '',
          expiryDate: '',
          unitPrice: item.purchasePrice || 0,
          remark: ''
        }));
      }
    });
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
  getInboundOrderInfo(row.id).then(response => {
    Object.assign(form.value, response.data);
    inboundOrderDetailInfoList.value = response.data.inboundOrderDetailInfoList || [];
    auditTitle.value = "审核入库单";
    // 获取库位列表
    getLocationList();
    openAudit.value = true;
  });
}

function submitAuditForm() {
  proxy.$refs["inboundOrderInfoAuditRef"].validate(valid => {
    if (valid) {
      form.value.inboundOrderDetailInfoList = inboundOrderDetailInfoList.value;
      auditInboundOrderInfo(form.value).then(response => {
        proxy.$modal.msgSuccess("审核成功");
        openAudit.value = false;
        getList();
      });
    }
  });
}

getList();
</script>
