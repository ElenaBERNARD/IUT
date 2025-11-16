import mongoose from "mongoose";
import { productSchema } from "../models/model.js";

const Product = mongoose.model('Product', productSchema);

export const addNewProduct = (req, res) => {
    try {
        let newProduct = new Product(req.body);
        const savedProduct = await newProduct.save();
        res.json(savedProduct);
    } catch (error) {
        console.error("Error saving product:", error);
        res.status(500).json({ message: error.message });
    }
}