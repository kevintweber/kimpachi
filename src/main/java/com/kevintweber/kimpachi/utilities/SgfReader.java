package com.kevintweber.kimpachi.utilities;

import com.kevintweber.kimpachi.board.Move;
import com.kevintweber.kimpachi.game.Configuration;
import com.kevintweber.kimpachi.game.Game;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class SgfReader {

    private static final Logger logger = LoggerFactory.getLogger(SgfReader.class);

    private final Pattern pattern;

    public SgfReader() {
        this.pattern = Pattern.compile("([A-Z]{1,2}\\[.*?\\])");
    }

    public Game read(@NonNull Path sgfPath) {

        Configuration.Builder configurationBuilder = new Configuration.Builder();
        Deque<Move> moves = new LinkedList<>();

        logger.debug("Reading file={}", sgfPath.toAbsolutePath().toString());
        try (BufferedReader br = new BufferedReader(new FileReader(sgfPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                readLine(line, configurationBuilder, moves);
            }
        } catch (IOException e) {
            logger.error("Error reading SGF file: " + e.getMessage(), e);

            throw new RuntimeException(e);
        }

        Game game = Game.newGame(configurationBuilder.build());
        for (Move move : moves) {
            game.addMove(move);
        }

        return game;
    }

    private void readLine(
            String line,
            Configuration.Builder configurationBuilder,
            Deque<Move> moves) {
        List<SgfToken> tokens = tokenizeLine(line);
        for (SgfToken token : tokens) {
            applyToken(token, configurationBuilder, moves);
        }
    }

    private void applyToken(
            SgfToken token,
            Configuration.Builder configurationBuilder,
            Deque<Move> moves) {
        logger.debug("Applying token={}", token);
        switch (token.getKey()) {
            case "B":
            case "W":
                moves.addLast(Move.sgf(token));
                break;

            case "DT":
                configurationBuilder.withDate(LocalDate.parse(token.getValue(), DateTimeFormatter.ISO_LOCAL_DATE));
                break;

            case "HA":
                configurationBuilder.withHandicap(Integer.parseInt(token.getValue()));
                break;

            case "KM":
                configurationBuilder.withKomi(new BigDecimal(token.getValue()));
                break;

            case "PB":
                configurationBuilder.withBlackName(token.getValue());
                break;

            case "PW":
                configurationBuilder.withWhiteName(token.getValue());
                break;
        }
    }

    private List<SgfToken> tokenizeLine(String line) {
        List<SgfToken> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            tokens.add(tokenizeMatch(matcher.group()));
        }

        return tokens;
    }

    private SgfToken tokenizeMatch(String match) {
        String[] matchParts = match.substring(0, match.length() - 1).split("\\[");
        if (matchParts.length == 1) {
            return new SgfToken(matchParts[0], "");
        }

        return new SgfToken(matchParts[0], matchParts[1]);
    }
}
