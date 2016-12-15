import java.util.ArrayList;

public class SettingsPrinterTest extends DRTHLTestClass {
    @Override
    public void randomTest() {
        //TODO implement this
    }

    @Override
    public String getClassBeingTested() {
        return "SettingsPrinter";
    }

    @Override
    public ArrayList<String> getTestedClassDependencies() {
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("Settings");
        return dependencies;
    }
}