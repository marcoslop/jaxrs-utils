package com.marcoslop.jee.jaxrsutils;

import com.marcoslop.jee.jaxrsutils.timer.ScheduledThreadPoolExecutorFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * If you want to limit the number of requests that a concrete IP Address can execute
 * you can use this utility class.
 *
 * Created by marcoslop on 23/09/14.
 */
public class LimitRequestsPerIp {

    private int hitMax = 3;
    private int secondsToLive = 100;

    private ConcurrentHashMap<String, Integer> ipHitCountMap = new ConcurrentHashMap();

    /**
     *
     * @param hitMax The number of requests allowed
     * @param secondsToLive The interval of time that these number of requests are allowed.
     */
    public LimitRequestsPerIp(int hitMax, int secondsToLive) {
        this.hitMax = hitMax;
        this.secondsToLive = secondsToLive;
    }

    public synchronized boolean canExecuteRequest (final String addr){
        Integer hitcount = ipHitCountMap.get(addr);
        if (hitcount==null){
            hitcount = 0;
        }
        boolean canExecute = false;
        if (hitcount < hitMax) {
            //only increase if person hasn't reached roof, prevents 'overblocking'
            hitcount++;

            ScheduledThreadPoolExecutorFactory.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    Integer hitcount = ipHitCountMap.get(addr);
                    hitcount--;
                    if (hitcount<=0){
                        ipHitCountMap.remove(addr);
                    }else{
                        ipHitCountMap.put(addr, hitcount);
                    }
                }
            }, secondsToLive, TimeUnit.SECONDS);
            canExecute = true;
        }
        ipHitCountMap.put(addr, hitcount);
        return canExecute;
    }

    public int getIpsSize (){
        return ipHitCountMap.size();
    }

}
