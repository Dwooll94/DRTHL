package csc455.group4.example;
public class example {
    public static void main(String[] args) throws Exception {
        Settings settings = new SettingsReader().read();
        SettingsPrinter printer = new SettingsPrinter();
        printer.print(settings);
    }
}