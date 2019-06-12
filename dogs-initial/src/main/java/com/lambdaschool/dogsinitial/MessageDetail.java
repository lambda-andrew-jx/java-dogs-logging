package com.lambdaschool.dogsinitial;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@ToString
public class MessageDetail implements Serializable {
    @Getter private String text;
    @Getter private int priority;
    @Getter private boolean secret;
}
