package com.kevintweber.kimpachi.board;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AreaTest {

    @Test
    void constructor() {
        Area area = Area.of(Stone.Black, buildListOfGroups());
        assertThat(area.contains(Point.of(1,1)))
                .isTrue();
        assertThat(area.contains(Point.of(12,12)))
                .isFalse();
        assertThat(area.count())
                .isEqualTo(4);

        Area area2 = Area.of(Stone.Black, List.of());
        assertThat(area2)
                .isSameAs(Area.empty(Stone.Black));

    }

    private List<Group> buildListOfGroups() {
        return List.of(
                Group.of(Point.of(1,1)),
                Group.of(Set.of(
                        Point.of(4, 4),
                        Point.of(4, 5),
                        Point.of(4, 6)
                ))
        );
    }
}