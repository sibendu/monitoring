package coms.handler;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import coms.util.ComsApiUtil;

public class AbstractEventHandlerDefAdapter extends TypeAdapter<AbstractEventHandlerDef> {

	@Override
	public AbstractEventHandlerDef read(JsonReader reader) throws IOException {
		
		String type = null;
		String fieldname = null;
		reader.beginObject();
		while (reader.hasNext()) {
			JsonToken token = reader.peek();
			System.out.println(token.name()+ " "+token.toString());
			if (token.equals(JsonToken.NAME)) {
				// get the current token
				fieldname = reader.nextName();
			}

			if ("type".equals(fieldname)) {
				// move to next token
				System.out.println("Here");
				token = reader.peek();
				type = reader.nextString();
				break;
			}
		}
		reader.endObject();
		

		AbstractEventHandlerDef def = new Gson().fromJson(reader, AbstractEventHandlerDef.class);
		AbstractEventHandlerDef thisDef = null;
		if (def.getType().equals(ComsApiUtil.HANDLER_TYPE_SERVICE)) {
			thisDef = new Gson().fromJson(reader, ServiceHandlerDef.class);
		} else if (def.getType().equals(ComsApiUtil.HANDLER_TYPE_HUMATASK)) {
			thisDef = new Gson().fromJson(reader, TaskHandlerDef.class);
		} else if (def.getType().equals(ComsApiUtil.HANDLER_TYPE_DECISION)) {
			thisDef = new Gson().fromJson(reader, DecisionHandlerDef.class);
		} else {
			throw new IOException("Unknown EventHandlerDef");
		}
		return thisDef;
	}

	@Override
	public void write(JsonWriter writer, AbstractEventHandlerDef def) throws IOException {
		System.out.println(def);
		writer.jsonValue(new Gson().toJson(def));
	}
}
