package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.proto.*;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class AnalyzerClient {

    @GrpcClient("analyzer")
    private RecommendationsControllerGrpc.RecommendationsControllerBlockingStub analyzerStub;

    public Stream<RecommendedEventProto> getSimilarEvents(long eventId, long userId, int maxResults) {
        try {
            log.info("Fetching similar events: eventId={}, userId={}, maxResults={}", eventId, userId, maxResults);
            SimilarEventsRequestProto request = SimilarEventsRequestProto.newBuilder()
                    .setEventId(eventId)
                    .setUserId(userId)
                    .setMaxResults(maxResults)
                    .build();
            Iterator<RecommendedEventProto> iterator = analyzerStub.getSimilarEvents(request);
            return toStream(iterator);
        } catch (Exception e) {
            log.error("Error while fetching similar events: eventId={}, userId={}, maxResults={}", eventId, userId, maxResults, e);
            return Stream.empty();
        }
    }

    public Stream<RecommendedEventProto> getRecommendationsForUser(long userId, int maxResults) {
        try {
            log.info("Fetching recommendations for user: userId={}, maxResults={}", userId, maxResults);
            UserPredictionsRequestProto request = UserPredictionsRequestProto.newBuilder()
                    .setUserId(userId)
                    .setMaxResults(maxResults)
                    .build();
            Iterator<RecommendedEventProto> iterator = analyzerStub.getRecommendationsForUser(request);
            return toStream(iterator);
        } catch (Exception e) {
            log.error("Error while fetching recommendations for user: userId={}, maxResults={}", userId, maxResults, e);
            return Stream.empty();
        }
    }

    public Stream<RecommendedEventProto> getInteractionsCount(Iterable<Long> eventIds) {
        try {
            log.info("Fetching interactions count for events");
            InteractionsCountRequestProto.Builder builder = InteractionsCountRequestProto.newBuilder();
            eventIds.forEach(builder::addEventId);
            InteractionsCountRequestProto request = builder.build();
            Iterator<RecommendedEventProto> iterator = analyzerStub.getInteractionsCount(request);
            return toStream(iterator);
        } catch (Exception e) {
            log.error("Error while fetching interactions count", e);
            return Stream.empty();
        }
    }

    private Stream<RecommendedEventProto> toStream(Iterator<RecommendedEventProto> iterator) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false
        );
    }
}