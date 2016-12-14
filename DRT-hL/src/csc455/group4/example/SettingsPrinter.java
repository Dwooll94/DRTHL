class SettingsPrinter {
    public void print(Settings settings) throws Exception {
        System.out.println(settings.getA());
        System.out.println(settings.getB());
        System.out.println(settings.getC());
        System.out.println(settings.getCalculatedConfig());
        System.out.println(settings.getA());
    }
}