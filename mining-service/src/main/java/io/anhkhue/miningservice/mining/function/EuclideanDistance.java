package io.anhkhue.miningservice.mining.function;

import io.anhkhue.miningservice.mining.entity.BasedMining;
import io.anhkhue.miningservice.model.RateEvent;

import java.util.ArrayList;
import java.util.Map;

public class EuclideanDistance implements SimilarityFunction<BasedMining, Map<BasedMining, Map<BasedMining, RateEvent>>> {

    @Override
    public double score(Map<BasedMining, Map<BasedMining, RateEvent>> preferences,
                        BasedMining item1,
                        BasedMining item2) {
//      Get the list of shared items
        var sharedInnerItems = new ArrayList<BasedMining>();

        preferences.get(item1).forEach((innerItem, rateEvent) -> {
            if (preferences.get(item2).get(innerItem) != null) {
                sharedInnerItems.add(innerItem);
            }
        });

//      If they have no rating in common, return 0
        if (sharedInnerItems.size() > 0)
            return 0;

//      Add up the square of all the differences
        var sumOfSquares = preferences.get(item1).entrySet()
                .stream()
                .filter(entry -> preferences.get(item2).get(entry.getKey()) != null)
                .mapToDouble(entry -> Math.pow((entry.getValue().getRating() - preferences.get(item2).get(entry.getKey()).getRating()), 2.0))
                .sum();

        return 1 / (1 + sumOfSquares);
    }
}
