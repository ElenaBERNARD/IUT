<template>
  <q-dialog v-model="dialog" persistent>
    <q-card>
      <q-card-section>
        <div class="text-h6">Update hero</div>
      </q-card-section>

      <q-card-section>
        <q-input label="Display Name" v-model="newHero.publicName" />
        <q-input label="Real Name" v-model="newHero.realName" />

        <q-form>
          <div v-for="(power, index) in newHero.powers" :key="index" class="row q-mb-sm">
            <q-input class="col" label="Power Name" v-model="power.name" />

            <q-select class="col" label="Type" v-model="power.type"
              :options="powerTypes.map((type, index) => ({ label: type, value: index + 1 }))" option-value="value"
              option-label="label" emit-value map-options />

            <q-input class="col" label="Power level" :min="0" :max="100" v-model="power.level"
              @update:model-value="power.level = Math.min(100, Math.max(0, power.level))" />

            <div class="flex items-center q-ml-sm">
              <q-btn color="negative" @click="removePower(index)" dense flat
                style="width: 32px; height: 32px; min-width: 32px;">
                <q-icon name="close" />
              </q-btn>
            </div>
          </div>
        </q-form>

        <q-btn label="+ Add Power" color="primary" @click="addPower" />
      </q-card-section>

      <q-card-actions>
        <q-btn flat label="Cancel" @click="closeDialog" />
        <q-btn flat label="Update Hero" color="primary" :disabled="areIdentical" @click="startConfirmation" />
      </q-card-actions>
    </q-card>
    <ConfirmDialog ref="confirmUpdateRef" />
  </q-dialog>
</template>

<script setup>
import { defineExpose, ref, computed } from 'vue';
import { useHeroStore } from '../stores/hero';
import ConfirmDialog from '../components/ConfirmDialog.vue';

const heroStore = useHeroStore();

const dialog = ref(false);
const newHero = ref({
  publicName: '',
  realName: '',
  powers: [],
});
const confirmUpdateRef = ref(null);
let onConfirm = () => { };

const powerTypes = ["Force", "Vitesse", "Endurance", "Magie", "Effrayant", "Furtivité", "Stupidité"];
const areIdentical = computed(() => {
  return JSON.stringify(heroStore.currentHero) === JSON.stringify(newHero.value);
});

const openDialog = (onConfirmFunc) => {
  onConfirm = onConfirmFunc;
  newHero.value = {
    _id: heroStore.currentHero._id,
    publicName: heroStore.currentHero.publicName,
    realName: heroStore.currentHero.realName,
    powers: heroStore.currentHero.powers.map(power => ({ ...power })),
  };
  dialog.value = true;
};

const closeDialog = () => {
  dialog.value = false;
};

const startConfirmation = () => {
  if (confirmUpdateRef.value) {
    confirmUpdateRef.value.openDialog(() => {
      confirm();
    }, "Confirm Hero Update", "Are you sure you want to update this hero?");
  } else {
    console.error("confirmUpdateRef is not defined");
  }
};

const confirm = () => {
  console.log(newHero.value)
  onConfirm(newHero.value);
  closeDialog();
};

const addPower = () => {
  newHero.value.powers.push({ name: 'newPower', type: 1, level: 0 });
};

const removePower = (index) => {
  newHero.value.powers.splice(index, 1);
};

defineExpose({ openDialog })
</script>
