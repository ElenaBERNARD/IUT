module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  pages: {
    index: {
      // entry for the page
      entry: 'src/main.js',
      title: 'authdemo',
    },
  },
  /*
  NB: publicPath can be used so that index.html in the production
  version will use only relative path to reference .css .js etc. files
  instead of a path like /css/myfile.css.
   */
  //publicPath:'',

};
