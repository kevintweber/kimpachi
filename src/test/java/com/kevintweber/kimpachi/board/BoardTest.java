package com.kevintweber.kimpachi.board;

import com.kevintweber.kimpachi.game.Prisoners;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void empty() {
        Board board1 = Board.empty();
        Board board2 = Board.empty();

        assertThat(board1)
                .as("Checking empty board")
                .isSameAs(board2);
        assertThat(board1.isEmpty())
                .isTrue();
    }

    @Test
    void boardManipulations() {
        Board board = Board.empty();
        Point point = Point.of(1, 1);
        assertThat(board.getColor(point))
                .as("Checking empty position")
                .isEqualTo(Color.Empty);
        assertThat(board.isOccupied(point))
                .as("Checking position is not occupied")
                .isFalse();
        assertThat(board.isEmpty())
                .isTrue();

        Move blackMove = Move.normalMove(Stone.Black, point);
        Board nextBoard = board.withMove(blackMove, Prisoners.empty());
        assertThat(nextBoard.isEmpty())
                .isFalse();
        assertThat(nextBoard)
                .as("Checking boards are immutable")
                .isNotEqualTo(board);
        assertThat(nextBoard.getColor(point))
                .as("Checking modified position")
                .isEqualTo(Color.Black);
        assertThat(nextBoard.isOccupied(point))
                .as("Checking position is occupied")
                .isTrue();
        assertThat(nextBoard.toString())
                .as("Checking toString")
                .isNotEqualTo(board.toString());

        Board nextBoard2 = nextBoard.withMove(blackMove, Prisoners.empty());
        assertThat(nextBoard2)
                .as("Checking immutability of adding same color")
                .isSameAs(nextBoard);

        Board nextBoard3 = nextBoard.clear(point);
        assertThat(nextBoard3)
                .as("Checking cyclic immutablility")
                .isNotSameAs(board)
                .isEqualTo(board);
        assertThat(nextBoard3.toString())
                .as("Checking toString")
                .isEqualTo(board.toString());
    }

    @Test
    void noDeadAreas() {
        Board board = (new Board.Builder())
                .add(
                        Stone.Black,
                        Set.of(
                                Point.of(2,2),
                                Point.of(2,3),
                                Point.of(2,4),
                                Point.of(3,4),
                                Point.of(4,4),
                                Point.of(4, 3),
                                Point.of(4, 2),
                                Point.of(3, 2)
                        )
                )
                .build();

        assertThat(board.getDeadArea(Stone.White))
                .isEqualTo(Area.empty(Stone.White));
        assertThat(board.getDeadArea(Stone.Black))
                .isEqualTo(Area.empty(Stone.Black));
    }

    @Test
    void oneDeadArea() {
        Board board = (new Board.Builder())
                .add(
                        Stone.Black,
                        Set.of(
                                Point.of(2,2),
                                Point.of(2,3),
                                Point.of(2,4),
                                Point.of(3,4),
                                Point.of(4,4),
                                Point.of(4, 3),
                                Point.of(4, 2),
                                Point.of(3, 2)
                        )
                )
                .add(Position.of(Stone.White, Point.of(3,3)))
                .build();

        Group deadWhiteGroup = Group.of(Point.of(3,3));
        Area deadWhiteArea = Area.of(Stone.White, List.of(deadWhiteGroup));
        assertThat(board.getDeadArea(Stone.White))
                .isEqualTo(deadWhiteArea);
        assertThat(board.getDeadArea(Stone.Black))
                .isEqualTo(Area.empty(Stone.Black));
    }

    @Test
    void print() {
        Board board = Board.empty();
        Point point = Point.of(1, 1);
        Move blackMove = Move.normalMove(Stone.Black, point);
        Board nextBoard = board.withMove(blackMove, Prisoners.empty());
        Move whiteMove = Move.normalMove(Stone.White, Point.of(4, 4));
        nextBoard = nextBoard.withMove(whiteMove, Prisoners.empty());
        assertThat(nextBoard.print())
                .as("Checking printing the board.")
                .isEqualTo("    A B C D E F G H J K L M N O P Q R S T \n" +
                        "19  . . . . . . . . . . . . . . . . . . . \n" +
                        "18  . . . . . . . . . . . . . . . . . . . \n" +
                        "17  . . . . . . . . . . . . . . . . . . . \n" +
                        "16  . . . + . . . . . + . . . . . + . . . \n" +
                        "15  . . . . . . . . . . . . . . . . . . . \n" +
                        "14  . . . . . . . . . . . . . . . . . . . \n" +
                        "13  . . . . . . . . . . . . . . . . . . . \n" +
                        "12  . . . . . . . . . . . . . . . . . . . \n" +
                        "11  . . . . . . . . . . . . . . . . . . . \n" +
                        "10  . . . + . . . . . + . . . . . + . . . \n" +
                        "9   . . . . . . . . . . . . . . . . . . . \n" +
                        "8   . . . . . . . . . . . . . . . . . . . \n" +
                        "7   . . . . . . . . . . . . . . . . . . . \n" +
                        "6   . . . . . . . . . . . . . . . . . . . \n" +
                        "5   . . . . . . . . . . . . . . . . . . . \n" +
                        "4   . . . O . . . . . + . . . . . + . . . \n" +
                        "3   . . . . . . . . . . . . . . . . . . . \n" +
                        "2   . . . . . . . . . . . . . . . . . . . \n" +
                        "1   X . . . . . . . . . . . . . . . . . . \n");
    }
}