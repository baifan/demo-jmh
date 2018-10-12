package tech.weiyi.demo.jmh.metric;

public class TestAndOperation {
    public static void main(String[] args) {

        for (int i = 3; i < 20; i++) {
            System.out.println("==========================");
            for (int j = 0; j < i; j++) {
                System.out.println(j + "\t&\t" + i + "\t=\t" + (j & i));
            }
        }


    }
}
