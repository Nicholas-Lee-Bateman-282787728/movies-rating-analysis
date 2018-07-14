package io.anhkhue.akphim.crawlers.sources;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Checkpoints {

    private String link;
    private String start;
    private String end;
}
