package com.kevintweber.kimpachi.board;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Functionality for creating and manipulating the Group class.
 */
public final class Groups {

    /**
     * Will group points into a list of Group objects.
     *
     * @param points The points to group.
     * @return The list of groups
     */
    public static List<Group> associate(@NonNull Set<Point> points) {
        if (points.isEmpty()) {
            return ImmutableList.of();
        }

        SimpleGraph<Point, DefaultEdge> allStonesGraph = new SimpleGraph<>(DefaultEdge.class);
        for (Point point : points) {
            allStonesGraph.addVertex(point);
            for (Point addedPoint : allStonesGraph.vertexSet()) {
                if (point.isAdjacent(addedPoint)) {
                    allStonesGraph.addEdge(point, addedPoint);
                }
            }
        }

        ConnectivityInspector<Point, DefaultEdge> connectivityInspector = new ConnectivityInspector<>(allStonesGraph);
        List<Set<Point>> connectedSets = connectivityInspector.connectedSets();

        List<Group> resultList = new ArrayList<>(connectedSets.size());
        for (Set<Point> pointSet : connectedSets) {
            resultList.add(Group.of(pointSet));
        }

        return ImmutableList.copyOf(resultList);
    }

    /**
     * Will find intersection points between two groups
     */
    public static List<Group> intersection(@NonNull Points group, @NonNull Points otherGroup) {
        Set<Point> points = new HashSet<>(group.getPoints());
        Set<Point> otherPoints = new HashSet<>(otherGroup.getPoints());

        points.retainAll(otherPoints);

        return associate(points);
    }

    /**
     * Will combine two groups into one or two groups.
     */
    public static List<Group> union(@NonNull Points group, @NonNull Points otherGroup) {
        Set<Point> points = new HashSet<>(group.getPoints());
        Set<Point> otherPoints = new HashSet<>(otherGroup.getPoints());

        points.addAll(otherPoints);

        return associate(points);
    }
}
