package JsonAdapters;

import Model.Product;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProductAdapter  implements JsonSerializer<Product>, JsonDeserializer<Product> {

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.get("type").getAsString().equals("smartphone")) {
            SmartphoneAdapter smartphoneAdapter = new SmartphoneAdapter();
            return smartphoneAdapter.deserialize(jsonElement,type,jsonDeserializationContext);
        }
        if(jsonObject.get("type").getAsString().equals("computer")) {
            ComputerAdapter computerAdapter = new ComputerAdapter();
            return computerAdapter.deserialize(jsonElement,type,jsonDeserializationContext);
        }
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        float price = jsonObject.get("price").getAsFloat();
        int amount = jsonObject.get("amount").getAsInt();
        return new Product(id,name,price,amount);
    }

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",product.getId());
        jsonObject.addProperty("name",product.getName());
        jsonObject.addProperty("price",product.getPrice());
        jsonObject.addProperty("amount",product.getAmount());
        jsonObject.addProperty("type","product");
        return jsonObject;
    }
}
