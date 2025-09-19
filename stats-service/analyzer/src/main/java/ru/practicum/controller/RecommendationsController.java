package ru.practicum.controller;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.stats.proto.*;
import ru.practicum.entity.RecommendedEvent;
import ru.practicum.service.RecommendationService;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class RecommendationsController
        extends RecommendationsControllerGrpc.RecommendationsControllerImplBase {

    private final RecommendationService recService;

    @Override
    public void getSimilarEvents(SimilarEventsRequestProto request,
                                 StreamObserver<RecommendedEventProto> responseObserver) {
        try {
            List<RecommendedEvent> list = recService.getSimilarEvents(request);
            for (RecommendedEvent re : list) {
                RecommendedEventProto proto = RecommendedEventProto.newBuilder()
                        .setEventId(re.eventId())
                        .setScore(re.score())
                        .build();
                responseObserver.onNext(proto);
            }
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            log.error("Недопустимый аргумент в getSimilarEvents: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e))
            );
        } catch (Exception e) {
            log.error("Непредвиденная ошибка в getSimilarEvents: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.UNKNOWN.withDescription("Произошла непредвиденная ошибка").withCause(e))
            );
        }
    }

    @Override
    public void getRecommendationsForUser(UserPredictionsRequestProto request,
                                          StreamObserver<RecommendedEventProto> responseObserver) {
        try {
            List<RecommendedEvent> list = recService.getRecommendationsForUser(request);
            for (RecommendedEvent re : list) {
                RecommendedEventProto proto = RecommendedEventProto.newBuilder()
                        .setEventId(re.eventId())
                        .setScore(re.score())
                        .build();
                responseObserver.onNext(proto);
            }
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            log.error("Недопустимый аргумент в getRecommendationsForUser: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e))
            );
        } catch (Exception e) {
            log.error("Непредвиденная ошибка в  getRecommendationsForUser: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.UNKNOWN.withDescription("Произошла непредвиденная ошибка").withCause(e))
            );
        }
    }

    @Override
    public void getInteractionsCount(InteractionsCountRequestProto request,
                                     StreamObserver<RecommendedEventProto> responseObserver) {
        try {
            List<RecommendedEvent> list = recService.getInteractionsCount(request);
            for (RecommendedEvent re : list) {
                RecommendedEventProto proto = RecommendedEventProto.newBuilder()
                        .setEventId(re.eventId())
                        .setScore(re.score())
                        .build();
                responseObserver.onNext(proto);
            }
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            log.error("Недопустимый аргумент в getInteractionsCount: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e))
            );
        } catch (Exception e) {
            log.error("Непредвиденная ошибка в getInteractionsCount: {}", e.getMessage(), e);
            responseObserver.onError(
                    new StatusRuntimeException(Status.UNKNOWN.withDescription("Произошла непредвиденная ошибка").withCause(e))
            );
        }
    }
}