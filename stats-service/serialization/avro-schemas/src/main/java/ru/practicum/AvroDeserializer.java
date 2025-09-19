package ru.practicum;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private final Class<T> targetType;

    public AvroDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    public AvroDeserializer() {
        this.targetType = null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        if (targetType == null) {
            throw new IllegalStateException("targetType не задан в AvroDeserializer!");
        }
        try {
            SpecificDatumReader<T> datumReader = new SpecificDatumReader<>(targetType);
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(in, null);
            return datumReader.read(null, decoder);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось десериализовать сообщение Avro для topic " + topic, e);
        }
    }

    @Override
    public void close() {
    }
}