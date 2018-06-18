package io.anhkhue.miningservice.mining.function;

import io.anhkhue.miningservice.mining.entity.BasedMining;
import io.anhkhue.miningservice.model.RateEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsRecommendation implements RecommendationFunction<BasedMining, BasedMining, RateEvent> {

    @Override
    @SuppressWarnings("unchecked")
    public List getRecommendation(Map<BasedMining, Map<BasedMining, RateEvent>> preferences,
                                  BasedMining item,
                                  SimilarityFunction similarityFunction) {
        var totals = new HashMap<BasedMining, Double>();
        var similaritySums = new HashMap<BasedMining, Double>();

        for (Map.Entry<BasedMining, Map<BasedMining, RateEvent>> other : preferences.entrySet()) {
            if (other.getKey() == item) continue;

            var similarity = similarityFunction.score(preferences, item, other);

            // Ignore scores of zero or lower
            if (similarity <= 0) continue;

            for (Map.Entry<BasedMining, RateEvent> innerItem : other.getValue().entrySet()) {
                // Only score inner items that does not exist for current outer item
                if (!preferences.get(item).containsKey(innerItem.getKey())) {
                    if (!totals.containsKey(innerItem.getKey())) {
                        totals.put(innerItem.getKey(), 0.0);
                    } else {
                        totals.put(innerItem.getKey(),
                                   totals.get(innerItem.getKey()) + innerItem.getValue().getRating() * similarity);
                    }
                    // Sum of similarities
                    if (!similaritySums.containsKey(innerItem.getKey())) {
                        similaritySums.put(innerItem.getKey(), 0.0);
                    } else {
                        similaritySums.put(innerItem.getKey(), similaritySums.get(innerItem.getKey()) + similarity);
                    }
                }
            }
        }

        // Create the normalized list


        ArrayList arrayList = new ArrayList();
        return arrayList;
    }

    List normalizeList(Map<BasedMining, Double> totals) {
        return new ArrayList<>(totals.entrySet());
    }
}
