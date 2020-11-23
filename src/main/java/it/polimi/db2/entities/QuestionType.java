package it.polimi.db2.entities;

public enum QuestionType {
    DYNAMIC(1), FIXED(2);

    private final int value;

    QuestionType(int value){ this.value=value; }

    public static QuestionType getTypeFromInt(int value) {
        switch (value) {
            case 1:
                return QuestionType.DYNAMIC;
            case 2:
                return QuestionType.FIXED;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
