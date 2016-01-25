package local.oop.model.player;

public enum PlayerId {
    PLAYER_1,
    PLAYER_2,
    PLAYER_3,
    PLAYER_4;

    static PlayerId first = null;
    static PlayerId second = null;
    public static PlayerId getId(int number) {
        switch (number) {
            case 1:
                return PLAYER_1;
            case 2:
                return PLAYER_2;
            case 3:
                return PLAYER_3;
            case 4:
                return PLAYER_4;
        }
        throw new EnumConstantNotPresentException(PlayerId.class, "PlayerId cannot be larger than 4");
    }

    public static PlayerId getRealId(int number) {
        switch (number) {
            case 1:
                return first;
            case 2:
                return second;
        }
        throw new EnumConstantNotPresentException(PlayerId.class, "PlayerId cannot be larger than 4");
    }

    public static void setFirst(PlayerId first) {
        PlayerId.first = first;
    }

    public static void setSecond(PlayerId second) {
        PlayerId.second = second;
    }

    public static void reset(){
        PlayerId.first = null;
        PlayerId.second = null;
    }
}
