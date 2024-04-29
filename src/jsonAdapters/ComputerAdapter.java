package jsonAdapters;

import model.Computer;
import model.Product;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ComputerAdapter implements JsonSerializer<Computer>, JsonDeserializer<Computer> {

    @Override
    public Computer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        float price = jsonObject.get("price").getAsFloat();
        int amount = jsonObject.get("amount").getAsInt();

        Product computerCase = jsonDeserializationContext.deserialize(jsonObject.get("computerCase"), Product.class);
        Product motherboard = jsonDeserializationContext.deserialize(jsonObject.get("motherboard"), Product.class);
        Product processor = jsonDeserializationContext.deserialize(jsonObject.get("processor"), Product.class);
        Product ram = jsonDeserializationContext.deserialize(jsonObject.get("ram"), Product.class);
        Product hardDrive = jsonDeserializationContext.deserialize(jsonObject.get("hardDrive"), Product.class);
        Product graphicsCard = jsonDeserializationContext.deserialize(jsonObject.get("graphicsCard"), Product.class);
        Product charger = jsonDeserializationContext.deserialize(jsonObject.get("charger"), Product.class);
        return new Computer.ComputerBuilder()
                .id(id)
                .name(name)
                .price(price)
                .amount(amount)
                .computerCase(computerCase)
                .motherboard(motherboard)
                .processor(processor)
                .ram(ram)
                .hardDrive(hardDrive)
                .graphicsCard(graphicsCard)
                .charger(charger)
                .build();
    }

    @Override
    public JsonElement serialize(Computer computer, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", computer.getId());
        jsonObject.addProperty("name", computer.getName());
        jsonObject.addProperty("price", computer.getPrice());
        jsonObject.addProperty("amount", computer.getAmount());

        jsonObject.add("computerCase", jsonSerializationContext.serialize(computer.getComputerCase()));
        jsonObject.add("motherboard", jsonSerializationContext.serialize(computer.getMotherboard()));
        jsonObject.add("processor", jsonSerializationContext.serialize(computer.getProcessor()));
        jsonObject.add("ram", jsonSerializationContext.serialize(computer.getRam()));
        jsonObject.add("hardDrive", jsonSerializationContext.serialize(computer.getHardDrive()));
        jsonObject.add("graphicsCard", jsonSerializationContext.serialize(computer.getGraphicsCard()));
        jsonObject.add("charger", jsonSerializationContext.serialize(computer.getCharger()));
        jsonObject.addProperty("type", "computer");
        return jsonObject;
    }
}