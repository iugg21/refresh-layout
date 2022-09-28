package com.ctrun.view.cateye.refresh.listener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author ctrun on 2021/6/23.
 */
public class CommonObservable {
    private CommonObservable() {

    }

    private static class SingletonHolder {
        private static final CommonObservable INSTANCE = new CommonObservable();
    }

    public static CommonObservable getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final ArrayList<WeakReference<Observer<Integer>>> mColorChangedObservers = new ArrayList<>();
    private final ArrayList<WeakReference<Observer<Boolean>>> mLightModeChangedObservers = new ArrayList<>();


    public void registerColorChangedObserver(Observer<Integer> observer, boolean register) {
        if (register) {
            for (WeakReference<Observer<Integer>> item : mColorChangedObservers) {
                if (item.get() == observer) {
                    return;
                }
            }

            mColorChangedObservers.add(new WeakReference<>(observer));
        } else {
            for (int i = 0; i < mColorChangedObservers.size(); ++i) {
                if (mColorChangedObservers.get(i).get() == observer) {
                    mColorChangedObservers.remove(i);
                    break;
                }
            }
        }
    }

    public void notifyColorChangedObservers(int color) {
        for (int i = 0; i < mColorChangedObservers.size(); ++i) {
            Observer<Integer> observer = mColorChangedObservers.get(i).get();
            if (observer != null) {
                observer.onEvent(color);
            }
        }
    }



    public void registerLightModeChangedObserver(Observer<Boolean> observer, boolean register) {
        if (register) {
            for (WeakReference<Observer<Boolean>> item : mLightModeChangedObservers) {
                if (item.get() == observer) {
                    return;
                }
            }

            mLightModeChangedObservers.add(new WeakReference<>(observer));
        } else {
            for (int i = 0; i < mLightModeChangedObservers.size(); ++i) {
                if (mLightModeChangedObservers.get(i).get() == observer) {
                    mLightModeChangedObservers.remove(i);
                    break;
                }
            }
        }
    }

    public void notifyLightModeChangedObservers(Boolean mode) {
        for (int i = 0; i < mLightModeChangedObservers.size(); ++i) {
            Observer<Boolean> observer = mLightModeChangedObservers.get(i).get();
            if (observer != null) {
                observer.onEvent(mode);
            }
        }
    }


}
