package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AreaTest {

    @Test
    void enlargeInCenter() {
        Area area = new Area.Builder()
                .addPosition(Position.of(5, 5))
                .addPosition(Position.of(5, 6))
                .build();
        Area enlargedArea = area.enlarge();
        assertThat(enlargedArea.count())
                .as("Checking enlarged area size")
                .isEqualTo(8);
        assertThat(enlargedArea.getPositions())
                .as("Checking enlarged area positions")
                .containsExactlyInAnyOrder(
                        Position.of(5, 4),
                        Position.of(4, 5),
                        Position.of(5, 5),
                        Position.of(6, 5),
                        Position.of(4, 6),
                        Position.of(5, 6),
                        Position.of(6, 6),
                        Position.of(5, 7)
                );
    }

    @Test
    void enlargeInCorner() {
        Area area = new Area.Builder()
                .addPosition(Position.of(1, 1))
                .build();
        Area enlargedArea = area.enlarge();
        assertThat(enlargedArea.count())
                .as("Checking enlarged area size")
                .isEqualTo(3);
        assertThat(enlargedArea.getPositions())
                .as("Checking enlarged area positions")
                .containsExactlyInAnyOrder(
                        Position.of(1, 1),
                        Position.of(2, 1),
                        Position.of(1, 2)
                );
    }

    @Test
    void enlargeMultipleTimes() {
        Area area = new Area.Builder()
                .addPosition(Position.of(1, 1))
                .build();
        Area enlargedArea = area.enlarge(2);
        assertThat(enlargedArea.count())
                .as("Checking enlarged area size")
                .isEqualTo(6);
        assertThat(enlargedArea.getPositions())
                .as("Checking enlarged area positions")
                .containsExactlyInAnyOrder(
                        Position.of(1, 1),
                        Position.of(2, 1),
                        Position.of(3, 1),
                        Position.of(1, 2),
                        Position.of(2, 2),
                        Position.of(1, 3)
                );
    }

    @Test
    void intersection() {
        Area area = new Area.Builder()
                .addPosition(Position.of(5, 5))
                .addPosition(Position.of(5, 6))
                .build();
        Area otherArea = new Area.Builder()
                .addPosition(Position.of(5, 5))
                .addPosition(Position.of(5, 4))
                .build();
        Area intersection = area.intersection(otherArea);
        assertThat(intersection.count())
                .as("Checking intersection area size")
                .isEqualTo(1);
        assertThat(intersection.getPositions())
                .as("Checking intersection area positions")
                .containsExactly(Position.of(5, 5));
    }

    @Test
    void union() {
        Area area = new Area.Builder()
                .addPosition(Position.of(5, 5))
                .addPosition(Position.of(5, 6))
                .build();
        Area otherArea = new Area.Builder()
                .addPosition(Position.of(5, 5))
                .addPosition(Position.of(5, 4))
                .build();
        Area union = area.union(otherArea);
        assertThat(union.count())
                .as("Checking union area size")
                .isEqualTo(3);
        assertThat(union.getPositions())
                .as("Checking union area positions")
                .containsExactlyInAnyOrder(
                        Position.of(5, 5),
                        Position.of(5, 6),
                        Position.of(5, 4)
                );
    }
}