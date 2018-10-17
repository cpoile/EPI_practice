package cpoile.HackerRank;

public class StringSplitting {
    public void main(String arghs) {
        String[] ss = arghs.split("/^(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[01]?\\d\\d|2[0-4]\n" +
                "\\d|25[0-5])\\.\n" +
                "(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[01]?\\d\\d|2[0-4]\n" +
                "\\d|25[0-5])$/");
    }
}
