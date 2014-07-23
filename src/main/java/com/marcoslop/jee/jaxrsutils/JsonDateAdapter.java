/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marcoslop.jee.jaxrsutils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 *
 * Use it with this annotation: @XmlJavaTypeAdapter(JsonDateAdapter.class)
 * Whenever you want a Date to be (des)serialized using its corresponding timestamp long value.
 *
 * @author Marcos
 */
public class JsonDateAdapter extends XmlAdapter<Long, Date> {

    @Override
    public Date unmarshal(Long v) throws Exception {
        if (v==null){
            return null;
        }
        return new Date(v);
    }

    @Override
    public Long marshal(Date v) throws Exception {
        if (v==null){
            return null;
        }
        return v.getTime();
    }

}
