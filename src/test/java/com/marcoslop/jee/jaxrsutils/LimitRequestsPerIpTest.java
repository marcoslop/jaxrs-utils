package com.marcoslop.jee.jaxrsutils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LimitRequestsPerIpTest {

    private LimitRequestsPerIp limitRequestsPerIp;

    private String ip1 = "ip1";
    private String ip2 = "ip2";

    @Before
    public void setUp() throws Exception {
        limitRequestsPerIp = new LimitRequestsPerIp(2, 1);
    }

    @Test
    public void testCanExecuteRequest_1Time() throws Exception {
        assertTrue(limitRequestsPerIp.canExecuteRequest(ip1));
    }

    @Test
    public void testCanExecuteRequest_2Times() throws Exception {
        limitRequestsPerIp.canExecuteRequest(ip1);
        assertTrue(limitRequestsPerIp.canExecuteRequest(ip1));
    }

    @Test
    public void testCanExecuteRequest_3Times() throws Exception {
        limitRequestsPerIp.canExecuteRequest(ip1);
        limitRequestsPerIp.canExecuteRequest(ip1);
        assertFalse(limitRequestsPerIp.canExecuteRequest(ip1));
    }

    @Test
    public void testCanExecuteRequest_3TimesWaitingOneSecond() throws Exception {
        limitRequestsPerIp.canExecuteRequest(ip1);
        limitRequestsPerIp.canExecuteRequest(ip1);
        Thread.sleep(1100);
        assertTrue(limitRequestsPerIp.canExecuteRequest(ip1));
    }

    @Test
    public void testCanExecuteRequest_3Times_ip2OneTime() throws Exception {
        limitRequestsPerIp.canExecuteRequest(ip1);
        limitRequestsPerIp.canExecuteRequest(ip1);
        assertTrue(limitRequestsPerIp.canExecuteRequest(ip2));
    }

    @Test
    public void testCanExecuteRequest_GetsEmptyAfterSecondsPassed() throws Exception {
        limitRequestsPerIp.canExecuteRequest(ip1);
        limitRequestsPerIp.canExecuteRequest(ip2);
        assertEquals(2, limitRequestsPerIp.getIpsSize());
        Thread.sleep(1100);
        assertEquals(0, limitRequestsPerIp.getIpsSize());
    }
}