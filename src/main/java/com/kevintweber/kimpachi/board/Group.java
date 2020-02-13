package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableSet;
import com.kevintweber.kimpachi.exception.UnconnectedException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * A Group is a set of connected points.
 * <p>
 * There is no such thing as an empty group.
 */
@EqualsAndHashCode
@ToString
public final class Group {

    private final ImmutableSet<Point> points;

    private Group(@NonNull Point point) {
        this.points = ImmutableSet.of(point);
    }

    private Group(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("Group must not be empty.");
        }

        this.points = ImmutableSet.copyOf(points);
        checkConnected();
    }

    public static Group of(@NonNull Point point) {
        return new Group(point);
    }

    public static Group of(@NonNull Set<Point> points) {
        return new Group(points);
    }

    public static Group copyOf(@NonNull Group otherGroup) {
        return new Group(otherGroup.points);
    }

    private void checkConnected() {
        if (points.size() <= 1) {
            return;
        }

        Graph<Point, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        for (Point point : points) {
            graph.addVertex(point);
            for (Point addedPoint : graph.vertexSet()) {
                if (point.isAdjacent(addedPoint)) {
                    graph.addEdge(point, addedPoint);
                }
            }
        }

        ConnectivityInspector<Point, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(graph);
        if (!connectivityInspector.isConnected()) {
            throw new UnconnectedException("Not all points are connected.");
        }
    }

    public boolean contains(@NonNull Point point) {
        return points.contains(point);
    }

    public int count() {
        return points.size();
    }

    public Group enlarge() {
        Set<Point> enlargedGroupPoints = new HashSet<>(getPoints());
        for (Point point : getPoints()) {
            enlargedGroupPoints.addAll(enlargePoint(point));
        }

        return Group.of(enlargedGroupPoints);
    }

    public Group enlarge(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Invalid enlargement count: " + count);
        }

        Group startingGroup = Group.copyOf(this);
        for (int i = 0; i < count; i++) {
            Group enlargedGroup = startingGroup.enlarge();
            if (enlargedGroup.equals(startingGroup)) {
                return enlargedGroup;
            }

            startingGroup = Group.copyOf(enlargedGroup);
        }

        return startingGroup;
    }

    private Set<Point> enlargePoint(Point point) {
        Set<Point> enlargedGroup = new HashSet<>();
        enlargedGroup.add(point);
        int x = point.getX();
        int y = point.getY();
        enlargedGroup.addAll(addPositionToArea(enlargedGroup, x - 1, y));
        enlargedGroup.addAll(addPositionToArea(enlargedGroup, x + 1, y));
        enlargedGroup.addAll(addPositionToArea(enlargedGroup, x, y - 1));
        enlargedGroup.addAll(addPositionToArea(enlargedGroup, x, y + 1));

        return enlargedGroup;
    }

    private Set<Point> addPositionToArea(Set<Point> points, int x, int y) {
        try {
            Point point = Point.of(x, y);
            points.add(point);
        } catch (Exception e) {
            // Do nothing.
        }

        return points;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public Set<Point> getNeighboringPoints() {
        Set<Point> enlargedGroupPoints = new HashSet<>(
                enlarge().getPoints()
        );

        enlargedGroupPoints.removeAll(getPoints());

        return enlargedGroupPoints;
    }

    public boolean isAdjacent(@NonNull Point point) {
        if (contains(point)) {
            return false;
        }

        for (Point innerPoint : points) {
            if (point.isAdjacent(innerPoint)) {
                return true;
            }
        }

        return false;
    }

    public boolean isDead(@NonNull Board board) {
        Set<Point> neighboringPoints = getNeighboringPoints();
        for (Point point : neighboringPoints) {
            if (!board.isOccupied(point)) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isIntersecting(@NonNull Group otherGroup) {
        if (otherGroup.equals(this)) {
            return true;
        }

        Set<Point> ownPoints = new HashSet<>(getPoints());
        Set<Point> otherPoints = otherGroup.getPoints();

        ownPoints.retainAll(otherPoints);

        return !ownPoints.isEmpty();
    }

    public Group with(@NonNull Point point) {
        if (contains(point)) {
            return this;
        }

        if (!isAdjacent(point)) {
            throw new UnconnectedException("Point is non-adjacent: " + point);
        }

        Set<Point> enlargedGroup = new HashSet<>(points);
        enlargedGroup.add(point);

        return new Group(enlargedGroup);
    }
}
