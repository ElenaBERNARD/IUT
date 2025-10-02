<template>
  <div class="list-wrapper">
    <h2 class="filter-title">Filtres :</h2>
    <table class="filter-table">
      <tr>
        <td>
          <label for="filterpriceactive">Par prix</label>
          <input type="checkbox" v-model="filterPriceActive" id="filterpriceactive" />
          <div v-if="filterPriceActive" class="filter-group">
            <label for="filterprice">Prix inférieur à :</label>
            <input v-model="priceFilter" id="filterprice" type="number" />
          </div>
        </td>
        <td>
          <label for="filternameactive">Par nom</label>
          <input type="checkbox" v-model="filterNameActive" id="filternameactive" />
          <div v-if="filterNameActive" class="filter-group">
            <label for="filtername">Nom contient :</label>
            <input v-model="nameFilter" id="filtername" type="text" />
          </div>
        </td>
        <td>
          <label for="filterstockactive">Par stock</label>
          <input type="checkbox" v-model="filterStockActive" id="filterstockactive" />
          <div v-if="filterStockActive" class="filter-group">
            <label for="filterstock">En stock</label>
            <input type="checkbox" v-model="stockFilter" id="filterstock" />
          </div>
        </td>
      </tr>
    </table>
    <hr />
    <h1>Viruses</h1>
    <CheckedList @list-button-clicked="flashItems()" @item-button-clicked="addToCart($event)"
      @checked-change="changeSelected($event)" @checked-all-changed="changeAllSelected($event)" :data=filterViruses
      :fields=fields :itemCheck=itemCheck :checked=checked :itemButton=itemButton :listButton=listButton
      :itemAmount=itemAmount />
  </div>
</template>

<script>

import { mapActions, mapState } from 'vuex';
import CheckedList from '../components/CheckedList.vue';

export default {
  name: 'ItemList',
  components: {
    CheckedList
  },
  data: () => ({
    priceFilter: 0,
    nameFilter: '',
    globalChecked: false,
    stockFilter: true,
    filterPriceActive: false,
    filterNameActive: false,
    filterStockActive: false,
    itemAmount: true,
    fields: [
      "name",
      "price",
      ['promotion', ['discount', 'amount']],
      "stock"
    ],
    itemCheck: true,
    selected: [],
    listButton: {
      show: true,
      text: "Get info",
      color: "white"
    }
  }),
  computed: {
    ...mapState('shop', ['viruses']),
    itemButton() {
      let itemButton = []
      for (let i = 0; i < this.viruses.length; i++) {
        itemButton.push({
          show: true,
          text: "Add to cart",
          color: "blue"
        })
      }
      return itemButton
    },
    filterVirusesByPrice() {
      if (!this.filterPriceActive)
        return this.viruses
      let price = parseInt(this.priceFilter)
      if (isNaN(price))
        return []
      if (price > 0)
        return this.viruses.filter(v => v.price < price)
      return this.viruses
    },
    filterVirusesByName() {
      // no active filter => get whole list
      if (!this.filterNameActive) return this.viruses
      if (this.nameFilter) return this.viruses.filter(v => v.name.includes(this.nameFilter))
      return this.viruses
    },
    filterVirusesByStock() {
      // no active filter => get whole list
      if (!this.filterStockActive) return this.viruses
      if (this.stockFilter) return this.viruses.filter(v => v.stock > 0)
      return this.viruses
    },
    filterViruses() {
      console.log(this.viruses)
      let list = this.viruses
      if (this.filterPriceActive) {
        let price = parseInt(this.priceFilter)
        if ((!isNaN(price)) && (price > 0)) {
          list = list.filter(v => v.price < price)
        }
      }
      if (this.filterNameActive) {
        if (this.nameFilter) list = list.filter(v => v.name.includes(this.nameFilter))
      }
      if (this.filterStockActive) {
        if (this.stockFilter) list = list.filter(v => v.stock > 0)
      }
      return list
    },
    checked() {
      console.log(this.selected)
      let checked = []
      for (let i = 0; i < this.viruses.length; i++) {
        if (this.filterViruses.includes(this.viruses[i])) {
          checked.push(this.selected.includes(i))
        }
      }
      return checked
    }
  },
  methods: {
    ...mapActions('shop', ['addItemToBasket']),
    flashItems() {
      let s = ""
      for (let i = 0; i < this.viruses.length; i++) {
        if (this.checked[i]) {
          s += this.filterViruses[i].name + ": " + this.filterViruses[i].stock + ", " + (this.filterViruses[i].sold ? "en vente" : "non disponible") + "\n"
        }
      }
      alert(s)
    },
    addToCart(data) {
      let virus = this.filterViruses[data.id]
      let result = { "virus": virus, "amount": data.amount }
      this.addItemToBasket(result)
    },
    changeSelected(index) {
      let v = this.filterViruses[index]
      if (this.filterViruses.includes(v)) {
        let idInList = this.viruses.indexOf(v)
        if (!this.selected.includes(idInList)) {
          this.selected.push(idInList)
        } else {
          this.selected.splice(this.selected.indexOf(idInList), 1)
        }
      }
    },
    changeAllSelected() {
      this.globalChecked = !this.globalChecked
      if(this.globalChecked) {
        for (let i = 0; i < this.viruses.length; i++) {
          if (!this.selected.includes(i)) {
            this.selected.push(i)
          }
        }
      } else {
        this.selected = []
      }
    }
  }
}
</script>

<style scoped>
.list-wrapper {
  margin: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: ease-in-out 0.1s;
}

.list-wrapper:hover {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  transition: ease-in-out 0.1s;
}

.filter-title {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #333;
}

.filter-table {
  width: 100%;
  margin-bottom: 20px;
  border-collapse: collapse;
}

.filter-table td {
  padding: 10px;
  vertical-align: top;
}

.filter-group {
  margin-top: 5px;
}

.filter-group label {
  font-size: 0.9rem;
  color: #555;
}

input[type="text"],
input[type="number"] {
  width: 100%;
  padding: 5px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

input[type="checkbox"] {
  margin-left: 5px;
}

hr {
  margin: 20px 0;
  border: 0;
  border-top: 1px solid #ddd;
}
</style>