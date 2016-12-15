import java.util.ArrayList;

public class SettingsReaderTest extends DRTHLTestClass {
    @Override
    public void randomTest() {
        //TODO implement this
    }

    @Override
    public String getClassBeingTested() {
        return "SettingsReader";
    }

    @Override
    public ArrayList<String> getTestedClassDependencies() {
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("Settings");
        return dependencies;
    }
}