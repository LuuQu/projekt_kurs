package jsonAdapters;

import model.Product;
import model.Smartphone;
import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class SmartphoneAdapter implements JsonSerializer<Smartphone>, JsonDeserializer<Smartphone> {

    @Override
    public Smartphone deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
       JsonObject jsonObject = jsonElement.getAsJsonObject();
       Color color = jsonDeserializationContext.deserialize(jsonObject.get("color"),Color.class);
       int batteryCapacity = jsonObject.get("batteryCapacity").getAsInt();
       Product smartphoneCase = jsonDeserializationContext.deserialize(jsonObject.get("phoneCase"), Product.class);
       int id = jsonObject.get("id").getAsInt();
       String name = jsonObject.get("name").getAsString();
       float price = jsonObject.get("price").getAsFloat();
       int amount = jsonObject.get("amount").getAsInt();
       return new Smartphone(id,name,price,amount,color,batteryCapacity,smartphoneCase);
    }

    @Override
    public JsonElement serialize(Smartphone smartphone, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("color",jsonSerializationContext.serialize(smartphone.getColor()));
        jsonObject.addProperty("batteryCapacity",smartphone.getBatteryCapacity());
        jsonObject.add("phoneCase",jsonSerializationContext.serialize(smartphone.getPhoneCase()));
        jsonObject.addProperty("id",smartphone.getId());
        jsonObject.addProperty("name",smartphone.getName());
        jsonObject.addProperty("price",smartphone.getPrice());
        jsonObject.addProperty("amount",smartphone.getAmount());
        jsonObject.addProperty("type","smartphone");
        return jsonObject;
    }
}
