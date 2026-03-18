package api._bit.catalog.domain;

public enum GameCategory {
    ACTION("Action"),
    ADULT("Adult"),
    ADVENTURE("Adventure"),
    EDUCATIONAL("Educational"),
    FIGHTING("Fighting"),
    PUZZLE("Puzzle"),
    RACING("Racing"),
    ROLE_PLAYING("Role_playing"),
    SHOOTER("Shooter"),
    SIMULATION("Simulation"),
    SPORTS("Sports"),
    STRATEGY("Strategy"),
    TRADITIONAL("Traditional"),
    VARIOUS("Various");

    public final String label;

    GameCategory(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
