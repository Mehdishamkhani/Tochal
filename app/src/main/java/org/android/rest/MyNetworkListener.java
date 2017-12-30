package org.android.rest;

/**
 * Created by USER on 11/13/2015.
 */
public interface MyNetworkListener<T> {
   public void getResult(T result);
   public void getException(NetworkExceptionHandler error);
}
