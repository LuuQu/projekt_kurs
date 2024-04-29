package jsonAdapters;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public Color deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return new Color(
                jsonObject.get("color_red").getAsInt(),
                jsonObject.get("color_green").getAsInt(),
                jsonObject.get("color_blue").getAsInt()
        );
    }

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("color_red", color.getRed());
        jsonObject.addProperty("color_green", color.getGreen());
        jsonObject.addProperty("color_blue", color.getBlue());
        return jsonObject;
    }
}
