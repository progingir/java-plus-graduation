package ru.practicum.mapper;

import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.proto.ActionTypeProto;
import ru.practicum.ewm.stats.proto.UserActionProto;


public class UserActionMapper {

    public static UserActionAvro toAvro(UserActionProto proto) {
        long timestampMillis = proto.getTimestamp().getSeconds() * 1000
                + proto.getTimestamp().getNanos() / 1_000_000;

        return UserActionAvro.newBuilder()
                .setUserId(proto.getUserId())
                .setEventId(proto.getEventId())
                .setActionType(toAvroActionType(proto.getActionType()))
                .setTimestamp(timestampMillis)
                .build();
    }

    private static ActionTypeAvro toAvroActionType(ActionTypeProto protoType) {
        return switch (protoType) {
            case ACTION_REGISTER -> ActionTypeAvro.REGISTER;
            case ACTION_LIKE -> ActionTypeAvro.LIKE;
            default -> ActionTypeAvro.VIEW;
        };
    }
}