package com.yaourt.common.utils.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GsonIgnoreFailureTypeAdapterFactory implements TypeAdapterFactory {

    /**
     Usage:

     Gson ref_Gson = new GsonBuilder()
     .setLenient()
     .registerTypeAdapterFactory(new IgnoreFailureTypeAdapterFactory())
     .create();

     */

    // -- CALLBACKS ----------------------------------------------------------------------------------------------------
    @Override
    public final <T> TypeAdapter<T> create(Gson ref_Gson, TypeToken<T> ref_Token_Type) {

        // -- Create
        final TypeAdapter<T> ref_TypeAdapter_Deleguate = ref_Gson.getDelegateAdapter(this, ref_Token_Type);

        // -- Commit
        return createCustomTypeAdapter(ref_TypeAdapter_Deleguate);

    }

    public <T> TypeAdapter<T> createCustomTypeAdapter(TypeAdapter<T> ref_TypeAdapter_Deleguate) {
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter ref_JsonWriter_Out, T ref_Value_Generic) throws IOException {

                // -- Bim
                ref_TypeAdapter_Deleguate.write(ref_JsonWriter_Out, ref_Value_Generic);

            }

            @Override
            public T read(JsonReader ref_JsonReader_Input) throws IOException {

                // -- Wrap
                try {

                    // -- Commit
                    return ref_TypeAdapter_Deleguate.read(ref_JsonReader_Input);

                } catch (Exception ref_Exception) {

                    // -- Skip this motherfocul
                    ref_JsonReader_Input.skipValue();

                    // -- Sheiz
                    return null;

                }
            }
        };
    }


}
