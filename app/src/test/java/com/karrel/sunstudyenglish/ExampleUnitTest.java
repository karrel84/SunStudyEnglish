package com.karrel.sunstudyenglish;

import android.support.v4.util.Pair;

import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void pairTest() {
        System.out.println("pairTest");
    }

    @Test
    public void subLists() {
        System.out.println("subLists");
        List<String> regacyList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            regacyList.add(i + " 번째 데이터입니다.");
        }

        // 큐에 넣고 작업하는건 어떨까?
        Queue<List<String>> queue = new ArrayDeque<>();
        queue.add(regacyList);

        // 2의 제곱만큼 돌린다. 그러면 큐에 최종 4개가 담겨야한다.
        for (int j = 0; j < 3; j++) {
            // 루프를 돌기전에 사이즈를 확인한다 for문에서 확인하면 사이즈가 변하기 때문
            final int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                List<String> list = queue.poll();
                Pair<List<String>, List<String>> pair = halfList(list);
                // 담은 리스트를 루프를 돌려서 다시 반으로 담아서 어딘가에 담는다.
                queue.add(pair.first);
                queue.add(pair.second);
            }
        }

        int idx = 0;
        while (!queue.isEmpty()) {
            idx++;

            List<String> list = queue.poll();
            for (String str : list) {
                System.out.println(String.format("%s번 리스트 :: %s", idx, str));
            }
        }
    }

    public Pair<List<String>, List<String>> halfList(List<String> list) {
        System.out.println("called halfList");
        int size = list.size();
        int halfCnt = size / 2;

        return Pair.create(list.subList(0, halfCnt), list.subList(halfCnt, size));
    }

    @Test
    public void testLog(){
        int x = 1200;

        System.out.println(logB(x, 2));
    }

    public static double logB(double x, double base) {
        return Math.log(x) / Math.log(base);
    }


    @Test
    public void testRx(){
        Observable observable = Observable.range(1, 10)
                .flatMap(i -> Observable.just(i))
                .subscribeOn(Schedulers.computation())
                .map(i2 -> intenseCalculation(i2));

        observable.subscribe(i -> System.out.println("Received " + i + " "
                + LocalTime.now() + " on thread "
                + Thread.currentThread().getName()));


    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}