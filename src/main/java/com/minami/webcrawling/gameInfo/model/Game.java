package com.minami.webcrawling.gameInfo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@ToString
public class Game {
    private String name;
    private String genre;
    private String company;
}
