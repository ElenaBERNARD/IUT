import mongoose from "mongoose";

const Schema = mongoose.Schema;

const productSchema = new Schema({
    name: {type:String,required:"Enter a name for the product"},
    price: {type:Number,required:"Enter a price for the product"},
    description: {type:String,required:"Enter a description for the product"},
    category: {type:String,required:"Enter a category for the product"},
    created_date: {type:Date,default:Date.now}
});

export { productSchema };