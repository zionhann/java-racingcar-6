package racingcar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Computer {

    private final List<String> raceResult = new ArrayList<>();
    private final List<RacingCar> carNames = new ArrayList<>();
    private int round = 0;

    public void readCarNames(String input) {
        List<String> carNames = extractCarNames(input);

        if (isInvalidCarNames(carNames)) {
            throw new IllegalArgumentException();
        }
        this.carNames.addAll(carNames.stream().map(RacingCar::new).toList());
    }

    private List<String> extractCarNames(String input) {
        return Arrays.stream(input.split(",")).map(String::trim).toList();
    }

    private boolean isInvalidCarNames(List<String> names) {
        return isDuplicate(names) || names.stream().anyMatch(name ->
                name.isBlank() || hasSizeGreaterThanFive(name));
    }

    private boolean isDuplicate(List<String> names) {
        return names.stream().distinct().count() != names.size();
    }

    private boolean hasSizeGreaterThanFive(String name) {
        return name.length() > 5;
    }

    public void readRound(String input) {
        final int number = Integer.parseInt(input);

        if (isInvalidRound(number)) {
            throw new IllegalArgumentException();
        }
        this.round = number;
    }

    private boolean isInvalidRound(int number) {
        return isNegative(number) || isZero(number);
    }

    private boolean isNegative(int number) {
        return number < 0;
    }

    private boolean isZero(int number) {
        return number == 0;
    }

    public String startRace() {
        if (isZero(this.round)) {
            throw new IllegalArgumentException();
        }
        while (hasNextRound()) {
            String roundResult = playRound();
            this.raceResult.add(roundResult);
        }
        return raceResult();
    }

    private boolean hasNextRound() {
        return this.round > 0;
    }

    private String playRound() {
        this.carNames.stream().filter(RacingCar::isMovable).forEach(RacingCar::move);
        endRound();
        return roundResult();
    }

    private void endRound() {
        this.round--;
    }

    private String roundResult() {
        return this.carNames.stream()
                .map(RacingCar::toString)
                .collect(Collectors.joining("\n", "", "\n"));
    }

    private String raceResult() {
        return String.join("\n", this.raceResult);
    }
}
