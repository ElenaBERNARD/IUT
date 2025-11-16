<template>
  <div>
    <ul>
      <li v-for="(item, item_id) in data" :key="item_id">
        <input v-if="itemCheck" type="checkbox" :checked="checked[item_id]"
          @change="$emit('checked-change', item_id)" />
        <span v-for="(field, field_id) in fields" :key="field_id">
          {{ item[field] }}
        </span>
        <button v-if="itemButton[item_id].show" @click="$emit('item-button-clicked', item_id)"
          :style="'color:' + itemButton[item_id].color + ';'"> {{ itemButton[item_id].text }}</button>
      </li>
    </ul>
    <button v-if="listButton.show" @click="$emit('list-button-clicked')" :style="'color:' + listButton.color + ';'" class="info-button">{{
      listButton.text }}</button>
  </div>
</template>



<script>
export default {
  name: 'CheckedList',
  props: {
    data: Array, // les données sources
    fields: Array, // le tableau contenant le nom des champs
    itemCheck: Boolean, // s'il y a des case à cocher
    checked: Array, // le tableau des cases cochées
    itemButton: Array, // l'objet pour les boutons d'items
    listButton: Object, // l'objet pour le bouton de liste
  },
  data: () => {
    return {}
  }
}
</script>


<style scoped>
ul {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

ul li {
  display: flex;
  flex-direction: row;
  border: 1px solid black;
  color: blue;
  margin: 5px;
}

ul li span {
  margin: 5px;
  max-width: 150px;
  width: 100px;
}

button {
  display: inline-block;
  outline: none;
  cursor: pointer;
  line-height: 20px;
  font-weight: 600;
  border-radius: 8px;
  padding: 5px 12px;
  border: 1px solid #222222;
  background: #ffffff;
}

.info-button{
  font-size: 16px;
  font-weight: 600;
  padding: 13px 23px;
}
</style>
