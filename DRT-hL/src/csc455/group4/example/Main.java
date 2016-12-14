public class Main {
    public static void main(String[] args) throws Exception {
        Settings settings = new SettingsReader().read();
        SettingsPrinter printer = new SettingsPrinter();
        printer.print(settings);
    }
}