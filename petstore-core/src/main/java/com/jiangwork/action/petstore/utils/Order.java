package com.jiangwork.action.petstore.utils;

/**
 * Created by Jiang on 2018/8/12.
 */
public abstract class Order<T extends Enum<?>> {
    private T state;


    public int toValue() {
        return state.ordinal();
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public static enum State1 {
        RUN,
        STOP
    }

    public static enum State2 {
        START,
        END
    }

    public static class NormalOrder extends Order<State1> {

    }

    public static class UnmanagedOrder extends Order<State2> {

    }

    public static void main(String[] args) {
        NormalOrder order = new NormalOrder();
        System.out.println(State1.RUN == order.getState());
    }
}
