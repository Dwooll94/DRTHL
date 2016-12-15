import java.util.ArrayList;
import java.util.LinkedList;

public class MainTest extends DRTHLTestClass {
    int MAX_RESULTS = 10;

    LinkedList<Boolean> results = new LinkedList<>();

    @Override
    public void randomTest() {
        try {
            Main.main(new String[]{});
            addResult(true);
        } catch (Exception e) {
            addResult(false);
        }
    }

    @Override
    public String getClassBeingTested() {
        return "Main";
    }

    @Override
    public ArrayList<String> getTestedClassDependencies() {
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("Settings");
        dependencies.add("SettingsPrinter");
        dependencies.add("SettingsReader");
        return dependencies;
    }

    @Override
    public double getRecentErrorRate() {
        return 0;
    }

    private void addResult(boolean result){
        results.add(Boolean.valueOf(result));
        if (results.size() > MAX_RESULTS) {
            results.remove(0);
        }
    }
}
