import java.util.ArrayList;
import java.util.LinkedList;

public class MainTest extends DRTHLTestClass {

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
}
