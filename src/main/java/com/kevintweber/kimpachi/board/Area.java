package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableList;
import com.kevintweber.kimpachi.exception.InvalidStoneException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A collection of groups of the same color.
 */
@EqualsAndHashCode
@ToString
public final class Area implements Points {

    private final Stone stone;
    private final ImmutableList<Group> groups;

    private static final Area EMPTY_BLACK = new Area(Stone.Black, List.of());
    private static final Area EMPTY_WHITE = new Area(Stone.White, List.of());

    private Area(
            @NonNull Stone stone,
            @NonNull List<Group> groups) {
        this.stone = stone;
        this.groups = ImmutableList.copyOf(groups);
    }

    public static Area empty(@NonNull Stone stone) {
        if (stone.equals(Stone.Black)) {
            return EMPTY_BLACK;
        }

        return EMPTY_WHITE;
    }

    public static Area copyOf(@NonNull Area otherArea) {
        return new Area(otherArea.stone, otherArea.groups);
    }

    public static Area of(
            @NonNull Stone stone,
            @NonNull List<Group> groups) {
        if (groups.isEmpty()) {
            return empty(stone);
        }

        return new Area(stone, groups);
    }

    @Override
    public boolean contains(@NonNull Point point) {
        for (Points group : groups) {
            if (group.contains(point)) {
                return true;
            }
        }

        return false;
    }

    public Stone getStone() {
        return stone;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        Set<Point> points = new HashSet<>();
        for (Points group : groups) {
            points.addAll(group.getNeighboringPoints());
        }

        return points;
    }

    @Override
    public Set<Point> getPoints() {
        Set<Point> points = new HashSet<>();
        for (Points group : groups) {
            points.addAll(group.getPoints());
        }

        return points;
    }

    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for (Points group : groups) {
            for (Point point : group.getPoints()) {
                positions.add(Position.of(stone, point));
            }
        }

        return positions;
    }

    public Area intersection(@NonNull Area otherArea) {
        if (!stone.equals(otherArea.stone)) {
            throw new InvalidStoneException("Invalid stone: " + otherArea.getStone());
        }

        Set<Point> pointSet = getPoints();
        pointSet.retainAll(otherArea.getPoints());

        return new Area(stone, Groups.associate(pointSet));
    }

    @Override
    public boolean isAdjacent(@NonNull Point point) {
        for (Points group : groups) {
            if (group.isAdjacent(point)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return groups.isEmpty();
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherPoints) {
        for (Points group : groups) {
            if (group.isIntersecting(otherPoints)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        int count = 0;
        for (Points group : groups) {
            count += group.size();
        }

        return count;
    }

    public Area union(@NonNull Area otherArea) {
        if (!stone.equals(otherArea.getStone())) {
            throw new InvalidStoneException("Invalid stone: " + otherArea.getStone());
        }

        Set<Point> pointSet = getPoints();
        pointSet.addAll(otherArea.getPoints());

        return new Area(stone, Groups.associate(pointSet));
    }

    public Area with(@NonNull Point point) {
        if (contains(point)) {
            return this;
        }

        Set<Point> points = getPoints();
        points.add(point);

        return new Area(stone, Groups.associate(points));
    }

    public Area with(@NonNull Position position) {
        if (!stone.equals(position.getStone())) {
            throw new InvalidStoneException("Invalid stone: " + position.getStone());
        }

        return with(position.getPoint());
    }

    public Area with(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            return this;
        }

        Set<Point> currentPoints = getPoints();
        currentPoints.addAll(points);

        return new Area(stone, Groups.associate(currentPoints));
    }

    public Area without(@NonNull Area area) {
        if (!stone.equals(area.getStone())) {
            throw new InvalidStoneException("Invalid stone: " + area.getStone());
        }

        if (area.isEmpty()) {
            return this;
        }

        Set<Point> currentPoints = getPoints();
        currentPoints.removeAll(area.getPoints());

        return new Area(stone, Groups.associate(currentPoints));
    }

    public Area without(@NonNull Point point) {
        if (!contains(point)) {
            return this;
        }

        Set<Point> points = getPoints();
        points.remove(point);

        return new Area(stone, Groups.associate(points));
    }

    public Area without(@NonNull Position position) {
        if (!stone.equals(position.getStone())) {
            throw new InvalidStoneException("Invalid stone: " + position.getStone());
        }

        return without(position.getPoint());
    }

    public Area without(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            return this;
        }

        Set<Point> currentPoints = getPoints();
        currentPoints.removeAll(points);

        return new Area(stone, Groups.associate(currentPoints));
    }

    public static class Builder {

        private final Stone builderStone;
        private final List<Group> builderGroups;

        public Builder(@NonNull Stone stone) {
            this.builderStone = stone;
            this.builderGroups = new ArrayList<>();
        }

        public Builder addGroup(@NonNull Group group) {
            this.builderGroups.add(group);

            return this;
        }

        public Area build() {
            return new Area(builderStone, builderGroups);
        }
    }
}
