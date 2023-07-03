package nttdata.com.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import reactor.core.publisher.Flux;

import java.io.IOException;

public class FluxDeserializer extends JsonDeserializer<Flux<?>> {

    @Override
    public Flux<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Flux.empty(); // Puedes ajustar la lógica de deserialización según tus necesidades
    }
}
