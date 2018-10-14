package tech.weiyi.demo.jmh.metric;

public class TestDistribution {
    public static void main(String[] args) {
        int[] array = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + "\t =\t" + array[i]);
        }
    }
}
