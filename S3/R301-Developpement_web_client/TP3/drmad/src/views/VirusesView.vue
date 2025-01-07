<template>
  <div>
    <h2>Filtres :</h2>
    <table class="filter-table">
      <tr>
        <td>
          <label for="filterpriceactive">Par prix</label><input type="checkbox" v-model="filterPriceActive"
            id="filterpriceactive">
          <div v-if="filterPriceActive">
            <label for="filterprice">Prix inferieur a : </label><input v-model="priceFilter" id="filterprice">
          </div>
        </td>
        <td>
          <label for="filternameactive">Par nom</label><input type="checkbox" v-model="filterNameActive"
            id="filternameactive">
          <div v-if="filterNameActive">
            <label for="filtername">Nom contient : </label><input v-model="nameFilter" id="filtername">
          </div>
        </td>
        <td>
          <label for="filterstockactive">Par stock</label><input type="checkbox" v-model="filterStockActive"
            id="filterstockactive">
          <div v-if="filterStockActive">
            <label for="filterstock">En stock</label><input type="checkbox" v-model="stockFilter" id="filterstock">
          </div>
        </td>
      </tr>
    </table>
    <hr />
    <table>
      <tr>
        <th>Nom</th>
        <th>Prix</th>
      </tr>
      <tr v-for="(virus, index) in filterViruses" :key="index">
        <td>{{ virus.name }}</td>
        <td>{{ virus.price }}</td>
      </tr>
    </table>
    <h1>Viruses</h1>
    <CheckedList @list-button-clicked="flashItems()" @item-button-clicked="flashItem($event)"
      @checked-change="changeSelected($event)" :data=filterViruses :fields=fields :itemCheck=itemCheck :checked=checked
      :itemButton=itemButton :listButton=listButton />
  </div>
</template>

<script>

import { mapState } from 'vuex';
import CheckedList from '../components/CheckedList.vue';

export default {
  name: 'VirusesView',
  components: {
    CheckedList
  },
  data: () => ({
    priceFilter: 0,
    nameFilter: '',
    stockFilter: true,
    filterPriceActive: false,
    filterNameActive: false,
    filterStockActive: false,
    fields: [
      "name",
      "description"
    ],
    itemCheck: true,
    selected: [],
    listButton: {
      show: true,
      text: "Get info",
      color: "green"
    }
  }),
  computed: {
    ...mapState(['viruses']),
    itemButton() {
      let itemButton = []
      for (let i = 0; i < this.viruses.length; i++) {
        itemButton.push({
          show: true,
          text: "details",
          color: "blue"
        })
      }
      return itemButton
    },
    filterVirusesByPrice() {
      // no active filter => get whole list
      if (!this.filterPriceActive) return this.viruses
      let price = parseInt(this.priceFilter)
      if (isNaN(price)) return []
      if (price > 0) return this.viruses.filter(v => v.price < price)
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
    flashItems() {
      let s = ""
      for (let i = 0; i < this.viruses.length; i++) {
        if (this.checked[i]) {
          s += this.filterViruses[i].name + ": " + this.filterViruses[i].stock + ", " + (this.filterViruses[i].sold ? "en vente" : "non disponible") + "\n"
        }
      }
      alert(s)
    },
    flashItem(index) {
      let v = this.filterViruses[index]
      alert("Virus : " + v.name + "\nStock : " + v.stock + "\nEtat : " + (v.sold ? "en vente" : "indisponible"))
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
    }
  }
}
</script>

<style scoped>
table {
  margin-top: 3%;
  margin-bottom: 3%;

  border-collapse: collapse;
  width: 100%;
  border: 1px solid black;
}

th,
td {
  border: 1px solid black;
  padding: 8px;
  text-align: left;
}

.filter-table tr td {
  width: 33%;
}
</style>