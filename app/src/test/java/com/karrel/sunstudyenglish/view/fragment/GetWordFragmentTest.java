package com.karrel.sunstudyenglish.view.fragment;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by bodyfriend_dev on 2017. 7. 25..
 */
public class GetWordFragmentTest {

    @Before
    public void setup() {

    }

    @Test
    public void getTime() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(System.currentTimeMillis()));
        System.out.println("time > " + time);
    }

}