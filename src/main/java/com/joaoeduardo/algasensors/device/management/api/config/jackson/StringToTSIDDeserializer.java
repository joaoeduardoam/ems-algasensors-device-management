package com.joaoeduardo.algasensors.device.management.api.config.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import io.hypersistence.tsid.*;

import java.io.*;

public class StringToTSIDDeserializer extends JsonDeserializer<TSID> {


    @Override
    public TSID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return TSID.from(p.getText());
    }
}
