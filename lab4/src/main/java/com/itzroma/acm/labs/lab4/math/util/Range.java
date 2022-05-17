package com.itzroma.acm.labs.lab4.math.util;

public record Range(int limitA, int limitB) {
    @Override
    public String toString() {
        return "[%d; %d]".formatted(limitA, limitB);
    }
}
