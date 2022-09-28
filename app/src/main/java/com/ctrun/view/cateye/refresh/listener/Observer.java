package com.ctrun.view.cateye.refresh.listener;

import java.io.Serializable;

/**
 * @author ctrun on 2021/6/23.
 */
public interface Observer<T> extends Serializable {

    void onEvent(T data);

}
