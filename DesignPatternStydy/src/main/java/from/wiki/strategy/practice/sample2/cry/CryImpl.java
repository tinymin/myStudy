package from.wiki.strategy.practice.sample2.cry;

public class CryImpl implements Cry {
    private String sound;

    public CryImpl(String sound) {
        this.sound = sound;
    }

    @Override
    public String cry() {
        return sound;
    }
}
