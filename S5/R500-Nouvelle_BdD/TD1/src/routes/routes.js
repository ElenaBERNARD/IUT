import { addNewProduct } from "../controllers/controllers.js";

const routes = (app) => {
    app.route('/products')
        .post(addNewProduct);
}

export default routes;