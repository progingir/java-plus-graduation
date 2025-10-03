package ru.practicum.controller;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.practicum.grpc.stats.recommendation.*;
import ru.practicum.handler.RecommendationsHandler;

@Slf4j
@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyzerController extends RecommendationsControllerGrpc.RecommendationsControllerImplBase {
    final RecommendationsHandler handler;

    @Override
    public void getRecommendationsForUser(UserPredictionsRequestProto request,
                                          StreamObserver<RecommendedEventProto> responseObserver) {
        long started = System.currentTimeMillis();
        try {
            log.info("Начало обработки запроса рекомендации для пользователя: {}", request);
            var result = handler.getRecommendationsForUser(request);
            log.debug("Найдено {} рекомендаций за {} мс",
                    result.size(), System.currentTimeMillis() - started);
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }

    @Override
    public void getSimilarEvents(SimilarEventsRequestProto request,
                                 StreamObserver<RecommendedEventProto> responseObserver) {
        long started = System.currentTimeMillis();
        try {
            log.info("Начало обработки запроса похожих событий: {}", request);
            var result = handler.getSimilarEvents(request);
            log.debug("Найдено {} похожих событий за {} мс",
                    result.size(), System.currentTimeMillis() - started);
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }

    @Override
    public void getInteractionsCount(InteractionsCountRequestProto request,
                                     StreamObserver<RecommendedEventProto> responseObserver) {
        long started = System.currentTimeMillis();
        try {
            log.info("Начало обработки запроса количества взаимодействий с мероприятием: {}", request);
            var result = handler.getInteractionsCount(request);
            log.debug("Получено {} записей об взаимодействиях за {} мс",
                    result.size(), System.currentTimeMillis() - started);
            result.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }
}