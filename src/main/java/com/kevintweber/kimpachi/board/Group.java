package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.exception.UnconnectedException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
 * <p>
 * This class exists in order to handle a group of points that lives and dies together.
 */
@EqualsAndHashCode
public final class Group implements Points, Printable {

    private final PointSet pointSet;

    private Group(@NonNull Point point) {
        this.pointSet = PointSet.of(point);
    }

    private Group(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("Group must not be empty.");
        }

        this.pointSet = PointSet.of(points);
        checkConnected();
    }

    public static Group of(@NonNull Point point) {
        return new Group(point);
    }

    public static Group of(@NonNull Set<Point> points) {
        return new Group(points);
    }

    public static Group copyOf(@NonNull Group otherGroup) {
        return new Group(otherGroup.getPoints());
    }

    private void checkConnected() {
        if (pointSet.size() <= 1) {
            return;
        }

        Graph<Point, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        for (Point point : pointSet.getPoints()) {
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

    @Override
    public boolean contains(@NonNull Point point) {
        return pointSet.contains(point);
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
        enlargedGroup.addAll(point.getNeighboringPoints());

        return enlargedGroup;
    }

    @Override
    public Set<Point> getPoints() {
        return pointSet.getPoints();
    }

    @Override
    public Set<Point> getNeighboringPoints() {
        return pointSet.getNeighboringPoints();
    }

    @Override
    public boolean isAdjacent(@NonNull Point point) {
        return pointSet.isAdjacent(point);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isIntersecting(@NonNull Points otherGroup) {
        return pointSet.isIntersecting(otherGroup);
    }

    @Override
    public int size() {
        return pointSet.size();
    }

    public Group with(@NonNull Point point) {
        if (contains(point)) {
            return this;
        }

        if (!isAdjacent(point)) {
            throw new UnconnectedException("Point is non-adjacent: " + point);
        }

        Set<Point> enlargedGroup = new HashSet<>(pointSet.getPoints());
        enlargedGroup.add(point);

        return new Group(enlargedGroup);
    }

    @Override
    public String print() {
        Komi komi = Komi.KOMI;
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < 19; i++) {
            sb.append(Board.positionCharacters.charAt(i));
            sb.append(" ");
        }

        sb.append("\n");

        for (int y = 19; y >= 1; y--) {
            sb.append(y);
            sb.append("  ");
            if (y < 10) {
                sb.append(" ");
            }

            for (int x = 1; x <= 19; x++) {
                Point point = Point.of(x, y);
                if (contains(point)) {
                    sb.append("# ");
                } else {
                    if (komi.isKomi(point)) {
                        sb.append("+ ");
                    } else {
                        sb.append(". ");
                    }
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Group(" +
                "points=" + pointSet.getPoints().toString() +
                ')';
    }
}
