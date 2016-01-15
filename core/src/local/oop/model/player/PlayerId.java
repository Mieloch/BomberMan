package local.oop.model.player;

public enum PlayerId {
    PLAYER_1,
    PLAYER_2,
    PLAYER_3,
    PLAYER_4;

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
        return null;
    }
}
