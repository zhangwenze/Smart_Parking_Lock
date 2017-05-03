package cn.com.nxyunzhineng.smart_parking_lock.beans;

/**
 * 作者：  wenze
 * 时间：  2017/5/2.
 * 版本：
 * 内容：
 */

public class LockItemBean {
    public String getmLockName() {
        return mLockName;
    }

    public void setmLockName(String mLockName) {
        this.mLockName = mLockName;
    }

    private String mLockName;

    public String getmLockState() {
        return mLockState;
    }

    public void setmLockState(String mLockState) {
        this.mLockState = mLockState;
    }

    private String mLockState;
}
