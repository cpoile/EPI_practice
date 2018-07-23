package cpoile.thinkinglikeaprogrammer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

public class MsgDecoder {
    private enum State {UPPER, LOWER, PUNCTUATION}

    ;
    private static final Map<State, State> Transitions = Map.of(
            State.UPPER, State.LOWER,
            State.LOWER, State.PUNCTUATION,
            State.PUNCTUATION, State.UPPER);

    private final Map<State, Function<Integer, Character>> Functions = Map.of(
            State.UPPER, this::UpperFn,
            State.LOWER, this::LowerFn,
            State.PUNCTUATION, this::PunctuationFn);

    // bad idea:
    /*private static final Map<Integer, Character> PunctMap = Map.of(
            1, '!',
            2, '?',
            3, ',',
            4, '.',
            5, ' ',
            6, ';',
            7, '"',
            8, '\''
    );*/
    // better:
    private static final char[] PunctMap = {'!', '?', ',', '.', ' ', ';', '"', '\''};

    private State curState = State.UPPER;
    private Function<Integer, Character> curFunc = this::UpperFn;

    private void switchState() {
        curState = Transitions.get(curState);
        curFunc = Functions.get(curState);
    }

    private Character UpperFn(Integer i) {
        int base = i % 27;
        if (base == 0) {
            switchState();
            return null;
        } else {
            return (char) (64 + base);
        }
    }

    private Character LowerFn(Integer i) {
        int base = i % 27;
        if (base == 0) {
            switchState();
            return null;
        } else {
            return (char) (96 + base);
        }
    }

    private Character PunctuationFn(Integer i) {
        int base = i % 9;
        if (base == 0) {
            switchState();
            return null;
        } else {
            return PunctMap[base-1];
        }
    }

    public Character process(int num) {
        return curFunc.apply(num);
    }
}
