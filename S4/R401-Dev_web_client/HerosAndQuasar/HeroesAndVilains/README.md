# HeroesAndVilains (heroesandvilains)

A Quasar Project

## Install the dependencies
```bash
yarn
# or
npm install
```

### Start the app in development mode (hot-code reloading, error reporting, etc.)
```bash
quasar dev
```


### Lint the files
```bash
yarn lint
# or
npm run lint
```


### Format the files
```bash
yarn format
# or
npm run format
```


### Build the app for production
```bash
quasar build
```

### Customize the configuration
See [Configuring quasar.config.js](https://v2.quasar.dev/quasar-cli-webpack/quasar-config-js).

### Add capacitor
```bash
quasar mode add capacitor
quasar build --mode capacitor -T android
```

Ouvrir dans Android Studio
/src-capacitor/android

Dans les fichiers build.gradle, utiliser Java 17
(Si les erreurs persistent, fix en mettant a jour les library)