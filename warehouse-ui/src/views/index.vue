<template>
  <div class="app-container home">
    <h1 class="page-title">{{ title }}</h1>

    <div class="shortcut-grid">

      <div class="shortcut-card" v-has-permi="['manage:inboundOrderInfo:list']"
           @click="goShortcut('InboundOrderInfo')">
        <div class="card-icon inbound-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M224 320v384h576V320H224zm480 320H288v-64h416v64zm0-128H288v-64h416v64zm0-128H288v-64h416v64z"/>
            <path d="M96 192h832v64H96z"/>
          </svg>
        </div>
        <div class="card-label">入库单</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:inventoryRecordInfo:list']"
           @click="goShortcut('InventoryRecordInfo')">
        <div class="card-icon inventory-record-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M320 320h128v128H320V320zm192 0h128v128H512V320zm192 0h128v128H704V320zM320 512h128v128H320V512zm192 0h128v128H512V512zm192 0h128v128H704V512zM320 704h128v128H320V704zm192 0h128v128H512V704zm192 0h128v128H704V704z"/>
            <path d="M896 128H128c-17.7 0-32 14.3-32 32v640c0 17.7 14.3 32 32 32h768c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32zm-32 640H160V192h704v576z"/>
          </svg>
        </div>
        <div class="card-label">库存记录</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:inventoryTransactionInfo:list']"
           @click="goShortcut('InventoryTransactionInfo')">
        <div class="card-icon inventory-flow-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M576 320l128-128v96h192v64H704v96L576 320zm256-128v256c0 35.3-28.7 64-64 64H64c-35.3 0-64-28.7-64-64V192c0-35.3 28.7-64 64-64h448c35.3 0 64 28.7 64 64v64h64V192H64v256h768z"/>
            <path d="M704 832H320v-64h384v64z"/>
          </svg>
        </div>
        <div class="card-label">库存流水</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:locationInfo:list']"
           @click="goShortcut('LocationInfo')">
        <div class="card-icon location-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M512 128c-141.4 0-256 114.6-256 256s114.6 256 256 256 256-114.6 256-256S653.4 128 512 128zm0 448c-106 0-192-86-192-192s86-192 192-192 192 86 192 192-86 192-192 192z"/>
            <path d="M512 320c-35.3 0-64 28.7-64 64s28.7 64 64 64 64-28.7 64-64-28.7-64-64-64zm224 256H288c-17.7 0-32-14.3-32-32V352c0-17.7 14.3-32 32-32h448c17.7 0 32 14.3 32 32v192c0 17.7-14.3 32-32 32z"/>
          </svg>
        </div>
        <div class="card-label">库位</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:outboundOrderInfo:list']"
           @click="goShortcut('OutboundOrderInfo')">
        <div class="card-icon outbound-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M224 320v384h576V320H224zm480 320H288v-64h416v64zm0-128H288v-64h416v64zm0-128H288v-64h416v64z"/>
            <path d="M96 192h832v64H96z"/>
            <path d="M448 256l128 128-128 128v-96H288v-64h160v-96z"/>
          </svg>
        </div>
        <div class="card-label">出库单</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:purchaseOrderInfo:list']"
           @click="goShortcut('PurchaseOrderInfo')">
        <div class="card-icon purchase-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M896 192H640c-35.3 0-64 28.7-64 64v416c0 35.3 28.7 64 64 64h256c35.3 0 64-28.7 64-64V256c0-35.3-28.7-64-64-64zM704 640H320V320h384v320z"/>
            <path d="M416 416h192v64H416zm0-128h192v64H416zm128 192h64v64h-64z"/>
            <path d="M192 128H128c-17.7 0-32 14.3-32 32v64c0 17.7 14.3 32 32 32h64c17.7 0 32-14.3 32-32v-64c0-17.7-14.3-32-32-32zm0 96h-64v-32h64v32z"/>
          </svg>
        </div>
        <div class="card-label">采购订单</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:sparePartsInfo:list']"
           @click="goShortcut('SparePartsInfo')">
        <div class="card-icon spare-parts-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M384 320h256v256H384V320zm64-192h128c35.3 0 64 28.7 64 64v128c0 35.3-28.7 64-64 64H448c-35.3 0-64-28.7-64-64V192c0-35.3 28.7-64 64-64z"/>
            <path d="M832 192H704c-35.3 0-64 28.7-64 64v64c0 35.3 28.7 64 64 64h128c35.3 0 64-28.7 64-64v-64c0-35.3-28.7-64-64-64z"/>
            <path d="M832 448H704c-35.3 0-64 28.7-64 64v64c0 35.3 28.7 64 64 64h128c35.3 0 64-28.7 64-64v-64c0-35.3-28.7-64-64-64z"/>
            <path d="M960 896c0 35.3-28.7 64-64 64H128c-35.3 0-64-28.7-64-64V128c0-35.3 28.7-64 64-64h768c35.3 0 64 28.7 64 64v768z"/>
          </svg>
        </div>
        <div class="card-label">备品备件信息</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:stocktakingOrderInfo:list']"
           @click="goShortcut('StocktakingOrderInfo')">
        <div class="card-icon stocktaking-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M448 256l-64 64-64-64c-17.7 0-32 14.3-32 32v64h192V288c0-17.7-14.3-32-32-32z"/>
            <path d="M512 128C266 128 64 330 64 576s202 448 448 448 448-202 448-448S758 128 512 128zm0 832c-211.2 0-384-172.8-384-384S300.8 192 512 192s384 172.8 384 384-172.8 384-384 384z"/>
            <path d="M512 320c-141.4 0-256 114.6-256 256s114.6 256 256 256 256-114.6 256-256-114.6-256-256-256zm0 448c-106 0-192-86-192-192s86-192 192-192 192 86 192 192-86 192-192 192z"/>
          </svg>
        </div>
        <div class="card-label">盘点单</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:supplierInfo:list']"
           @click="goShortcut('SupplierInfo')">
        <div class="card-icon supplier-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M384 128c-35.3 0-64 28.7-64 64v32h128v-32c0-35.3-28.7-64-64-64z"/>
            <path d="M704 256H544c-35.3 0-64 28.7-64 64v320c0 35.3 28.7 64 64 64h160c35.3 0 64-28.7 64-64V320c0-35.3-28.7-64-64-64zM640 608H576v-64h64v64zm0-128H576v-64h64v64zm128 128h-64v-64h64v64zm0-128h-64v-64h64v64zm0-128h-64v-64h64v64z"/>
            <path d="M896 128H640c-35.3 0-64 28.7-64 64v64H192c-35.3 0-64 28.7-64 64v128h64v-64h640v256H128V384H64v128c0 35.3 28.7 64 64 64h64v192c0 35.3 28.7 64 64 64h512c35.3 0 64-28.7 64-64V384h64V192c0-35.3-28.7-64-64-64z"/>
          </svg>
        </div>
        <div class="card-label">供应商信息</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:warehouseInfo:list']"
           @click="goShortcut('WarehouseInfo')">
        <div class="card-icon warehouse-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M896 352H768V224c0-17.7-14.3-32-32-32H576c-17.7 0-32 14.3-32 32v128H416V192c0-17.7-14.3-32-32-32H128c-17.7 0-32 14.3-32 32v160H64c-35.3 0-64 28.7-64 64v384c0 35.3 28.7 64 64 64h832c35.3 0 64-28.7 64-64V416c0-35.3-28.7-64-64-64zM448 224h128v128H448V224zm384 448H192V448h640v224z"/>
            <path d="M352 384h64v64h-64zm128 0h64v64h-64zm128 0h64v64h-64z"/>
          </svg>
        </div>
        <div class="card-label">仓库</div>
      </div>

      <div class="shortcut-card" v-has-permi="['manage:warningInfo:list']"
           @click="goShortcut('WarningInfo')">
        <div class="card-icon warning-icon">
          <svg viewBox="0 0 1024 1024" width="48" height="48" fill="#FFFFFF">
            <path d="M512 128L128 832h768L512 128zm0 96l288 560H224l288-560zm-32 160v96h64v-96h-64zm0 160v64h64v-64h-64z"/>
          </svg>
        </div>
        <div class="card-label">库存预警</div>
      </div>
    </div>

  </div>
</template>

<script setup>
const title = import.meta.env.VITE_APP_TITLE
import {useRouter} from "vue-router"

const router = useRouter()
const goShortcut = (name) => {
  router.push({name})
}
</script>

<style scoped lang="scss">
.home {
  padding: 32px;
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
  background: #f5f7fa;
  min-height: 100vh;
  box-sizing: border-box;

  .page-title {
    margin: 0 auto;
    text-align: center;
    font-size: 48px;
    color: #1ab394;
    font-weight: bold;
  }

  .shortcut-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 24px;
    max-width: 1200px;
    margin: 100px auto 100px;

    .shortcut-card {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: #fff;
      border-radius: 12px;
      padding: 32px 16px;
      cursor: pointer;
      transition: transform 0.25s ease, box-shadow 0.25s ease;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
      border: 1px solid rgba(0, 0, 0, 0.06);

      &:hover {
        transform: translateY(-6px);
        box-shadow: 0 12px 32px rgba(0, 0, 0, 0.14);

        .card-icon {
          transform: scale(1.1);
        }
      }

      &:active {
        transform: translateY(-2px);
      }

      .card-icon {
        width: 72px;
        height: 72px;
        border-radius: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 16px;
        transition: transform 0.25s ease;

        &.health-record-icon {
          background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        }

        &.health-history-icon {
          background: linear-gradient(135deg, #00c6ff 0%, #0072ff 100%);
        }

        &.resident-icon {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        &.hospital-icon {
          background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        &.appointment-icon {
          background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        &.treatment-icon {
          background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        &.inbound-icon {
          background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
        }

        &.inventory-record-icon {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        &.inventory-flow-icon {
          background: linear-gradient(135deg, #00c6ff 0%, #0072ff 100%);
        }

        &.location-icon {
          background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        &.outbound-icon {
          background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        &.purchase-icon {
          background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }

        &.spare-parts-icon {
          background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        &.stocktaking-icon {
          background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
        }

        &.supplier-icon {
          background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%);
        }

        &.warehouse-icon {
          background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%);
        }

        &.warning-icon {
          background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
        }

        .icon {
          color: #fff;
        }
      }

      .card-label {
        font-size: 15px;
        font-weight: 600;
        color: #2c3e50;
        text-align: center;
      }
    }
  }

  .info-section {
    max-width: 1100px;
    margin: 0 auto;

    blockquote {
      padding: 16px 24px;
      margin: 0;
      font-size: 14px;
      border-left: 4px solid #667eea;
      background: #fff;
      border-radius: 0 8px 8px 0;
      box-shadow: 0 1px 8px rgba(0, 0, 0, 0.06);
      color: #444;

      ol {
        display: block;
        list-style-type: decimal;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0;
        margin-inline-end: 0;
        padding-inline-start: 24px;

        li {
          padding: 4px 0;
          color: #555;
          line-height: 1.6;
        }
      }
    }
  }

  @media (max-width: 1200px) {
    .shortcut-grid {
      grid-template-columns: repeat(3, 1fr);
    }
  }

  @media (max-width: 900px) {
    .shortcut-grid {
      grid-template-columns: repeat(2, 1fr);
    }
  }

  @media (max-width: 600px) {
    .shortcut-grid {
      grid-template-columns: repeat(1, 1fr);
    }
  }
}
</style>

